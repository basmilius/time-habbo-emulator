package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.util.ObjectUtils;

@Command(
		executable = "cmd.syntax.warp",
		syntax = ":warp <x> <y>"
)
public class WarpCommand extends ICommand
{

	public WarpCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_warp") && parts.length == 3 && ObjectUtils.isNumeric(parts[1]) && ObjectUtils.isNumeric(parts[2]))
		{
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);

			RoomUser roomUser = ((RoomUser) this.invoker);
			roomUser.setPos(x, y);
			roomUser.updateStatus();
			return true;
		}
		return false;
	}

}
