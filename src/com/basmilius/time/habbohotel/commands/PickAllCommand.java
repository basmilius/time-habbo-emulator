package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.RoomUser;

import java.util.List;

@Command(
		executable = "cmd.syntax.pickall",
		syntax = ":pickall"
)
public class PickAllCommand extends ICommand
{

	public PickAllCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		final List<UserItem> items = this.invoker.getRoom().getRoomObjectsHandler().getItemsWithOwner(((RoomUser) this.invoker).getHabbo());

		if (items.size() > 0)
		{
			for (final UserItem item : items)
			{
				item.getRoom().getRoomObjectsHandler().pickUp(item);
			}

			return true;
		}
		return false;
	}

}
