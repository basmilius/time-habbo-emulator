package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;

@Command(
		executable = "cmd.syntax.unload",
		syntax = ":unload"
)
public class UnloadCommand extends ICommand
{

	public UnloadCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.invoker.getRoom().getRoomData().getPermissions().isOwner(((RoomUser) this.invoker).getHabbo()) || ((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_unload"))
		{
			this.invoker.getRoom().unload();
			return true;
		}
		return false;
	}

}
