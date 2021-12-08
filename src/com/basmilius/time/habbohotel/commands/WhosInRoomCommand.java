package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationUserListComposer;
import com.basmilius.time.util.ObjectUtils;

import java.util.stream.Collectors;

@Command(
		executable = "cmd.syntax.whosinroom",
		syntax = ":whosinroom [roomid]"
)
public class WhosInRoomCommand extends ICommand
{

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public WhosInRoomCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.isUser() && this.getInvoker(RoomUser.class).getHabbo().getPermissions().contains("acc_supporttool"))
		{
			final Room room;

			if (parts.length > 1 && ObjectUtils.isNumeric(parts[1]))
				room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(Integer.parseInt(parts[1]));
			else
				room = this.invoker.getRoom();

			if (room == null)
				return true;

			this.getInvoker(RoomUser.class).getConnection().send(new ModerationUserListComposer(room.getRoomUnitsHandler().getUsers().stream().map(RoomUser::getHabbo).collect(Collectors.toList())));
			return true;
		}
		return false;
	}

}
