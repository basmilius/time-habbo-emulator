package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;

@Command(
		executable = "cmd.syntax.update.items",
		syntax = ":update_items"
)
public class UpdateItemsCommand extends ICommand
{

	public UpdateItemsCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_update_items"))
		{
			Bootstrap.getEngine().getGame().getItemsManager().reloadItems();
			return true;
		}
		return false;
	}

}
