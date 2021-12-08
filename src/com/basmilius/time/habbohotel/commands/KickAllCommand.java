package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;

@Command(
		executable = "cmd.syntax.kickall",
		syntax = ":kickall"
)
public class KickAllCommand extends ICommand
{

	public KickAllCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if ((this.invoker.getRoom().getRoomData().getPermissions().isOwner(((RoomUser) this.invoker).getHabbo())) || ((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_kickall"))
		{
			for (RoomUser user : this.invoker.getRoom().getRoomUnitsHandler().getUsers())
			{
				if (user.getHabbo().getId() == this.invoker.getId() || user.getHabbo().getPermissions().contains("acc_unkickable"))
					continue;

				user.kick();
			}
			return true;
		}
		return false;
	}

}
