package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.moderation.chatlog.Chatlog;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationRoomChatlogsComposer;

import java.util.List;

@Event(id = Incoming.ModerationRoomChatlogs)
public class ModerationRoomChatlogsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().getPermissions().contains("acc_supporttool"))
			return;

		packet.readInt();

		final int roomId = packet.readInt();
		final Room room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(roomId);
		final List<Chatlog> chatlogs = Bootstrap.getEngine().getGame().getModerationManager().getChatlogs().getChatlogsForRoom(room);

		connection.send(new ModerationRoomChatlogsComposer(room, chatlogs));
	}

}
