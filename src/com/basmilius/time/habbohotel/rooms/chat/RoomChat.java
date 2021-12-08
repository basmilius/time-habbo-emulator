package com.basmilius.time.habbohotel.rooms.chat;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomChatFloodingComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.users.RoomUserTypingComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.users.TalkComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.users.TalkShoutComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.users.TalkWhisperComposer;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomBot;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.event.RoomUnitChatEventArgs;
import com.basmilius.time.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomChat implements Runnable
{

	private final Room room;

	private final List<RoomChatMessage> messages;

	public RoomChat(final Room room)
	{
		this.room = room;
		this.messages = new ArrayList<>();

		(new Thread(this)).start();
	}

	public void addMessage(final RoomChatMessage roomChatMessage, boolean ignoreFlooding)
	{
		if (roomChatMessage.getUnit().getUnitType() == RoomUnitType.USER && ((RoomUser) roomChatMessage.getUnit()).isSpectator())
			return;

		roomChatMessage.getUnit().setIdle(false, true);

		if (!ignoreFlooding)
		{
			this.messages.add(roomChatMessage);

			if (!this.canChat(roomChatMessage.getUnit()) && roomChatMessage.getUnit().getUnitType() == RoomUnitType.USER)
			{
				/**
				 * TODO: Soft code the seconds the user can't chat.
				 * TODO: Staff members have an different waiting time than normal users so to speak.
				 */
				((RoomUser) roomChatMessage.getUnit()).getConnection().send(new RoomChatFloodingComposer(10));
				return;
			}
		}

		final boolean wired = this.room.getRoomInteractions().userSaysSomething(roomChatMessage.getUnit(), roomChatMessage.getMessage(), roomChatMessage.getBubble(), (roomChatMessage.getType() == ChatType.SHOUT));

		if (Bootstrap.getEngine().getGame().getChatCommands().commandExists(roomChatMessage.getMessage()))
		{
			final boolean result = Bootstrap.getEngine().getGame().getChatCommands().handleCommand(roomChatMessage.getUnit(), roomChatMessage.getMessage(), roomChatMessage.getType() == ChatType.SHOUT, roomChatMessage.getBubble().toInt());

			if (result)
			{
				this.room.getRoomUnitsHandler().send(new RoomUserTypingComposer(roomChatMessage.getUnit().getUnitId(), false));
				return;
			}
		}

		if (roomChatMessage.getUnit().getUnitType() == RoomUnitType.USER)
		{
			if (wired)
			{
				roomChatMessage.setType(ChatType.WHISPER);
			}

			Bootstrap.getEngine().getGame().getModerationManager().getChatlogs().addChatlog(((RoomUser) roomChatMessage.getUnit()).getHabbo(), this.room, roomChatMessage.getMessage());
		}

		switch (roomChatMessage.getType())
		{
			case SHOUT:
				this.room.getRoomUnitsHandler().send(new TalkShoutComposer(roomChatMessage));
				break;
			case TALK:
				this.room.getRoomUnitsHandler().send(new TalkComposer(roomChatMessage));
				break;
			case WHISPER:
				if (roomChatMessage.getUnit().getUnitType() == RoomUnitType.USER)
					((RoomUser) roomChatMessage.getUnit()).getConnection().send(new TalkWhisperComposer(roomChatMessage));

				if (roomChatMessage.getUnitTo() != null && roomChatMessage.getUnit().getUnitId() != roomChatMessage.getUnitTo().getUnitId())
				{
					if (roomChatMessage.getUnitTo().getHabbo().isOnline() && roomChatMessage.getUnit().getRoom().getRoomData().getId() == roomChatMessage.getUnitTo().getRoom().getRoomData().getId())
						roomChatMessage.getUnitTo().getConnection().send(new TalkWhisperComposer(roomChatMessage));
				}
				break;
		}

		if (roomChatMessage.getUnit().getUnitType() == RoomUnitType.USER)
		{
			if (roomChatMessage.getType() != ChatType.WHISPER)
			{
				for (final RoomBot roomBot : this.room.getRoomUnitsHandler().getBots())
				{
					if (roomBot.onUserChat(((RoomUser) roomChatMessage.getUnit()).getHabbo(), roomChatMessage.getMessage()))
						break;
				}
			}
		}

		roomChatMessage.getUnit().dispatchEvent(RoomUnitChatEventArgs.class, new RoomUnitChatEventArgs(roomChatMessage.getType(), roomChatMessage.getMessage(), roomChatMessage.getBubble()));
	}

	private boolean canChat(final RoomUnit unit)
	{
		return unit.getUnitType() != RoomUnitType.USER || (this.getLatestMessagesByUnit(unit).size() <= 5000);
	}

	private List<RoomChatMessage> getLatestMessagesByUnit(final RoomUnit unit)
	{
		final List<RoomChatMessage> messages = new ArrayList<>();
		final List<RoomChatMessage> oldMessages = this.messages;

		messages.addAll(oldMessages.stream().filter(message -> message.getUnit().getUnitId() == unit.getUnitId()).collect(Collectors.toList()));

		return messages;
	}

	@Override
	public void run()
	{
		while (this.room.isActive())
		{
			try
			{
				final int currentTime = TimeUtils.getUnixTimestamp();
				final List<RoomChatMessage> messages = new ArrayList<>();
				final List<RoomChatMessage> oldMessages = this.messages;

				for (final RoomChatMessage message : oldMessages)
				{
					if ((currentTime - message.getSaidOn()) > 5)
						continue;

					messages.add(message);
				}

				this.messages.clear();
				this.messages.addAll(messages);

				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
				this.room.crash();
			}
		}
	}
}
