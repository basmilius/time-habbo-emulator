package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.RoomUser;

import java.util.List;

@Command(
		executable = "cmd.syntax.ejectall",
		syntax = ":ejectall"
)
public class EjectAllCommand extends ICommand
{

	public EjectAllCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		final List<UserItem> items = this.invoker.getRoom().getRoomObjectsHandler().getItems();

		if (items.size() > 0 && this.invoker.getRoom().getRoomData().getPermissions().isOwner(((RoomUser) this.invoker).getHabbo()))
		{
			for (UserItem item : items)
			{
				if (item.getHabbo().getId() == this.invoker.getId())
					continue;

				item.getRoom().getRoomObjectsHandler().pickUp(item);
			}

			return true;
		}
		return false;
	}

}
