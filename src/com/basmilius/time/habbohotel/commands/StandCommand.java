package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.items.UserItem;

@Command(
		executable = "cmd.syntax.stand",
		syntax = ":stand"
)
public class StandCommand extends ICommand
{

	public StandCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);

		this.setAllowBots(true);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		boolean hasSeat = false;

		for (final UserItem item : this.invoker.getRoom().getRoomObjectsHandler().getFloorItemsAt(this.invoker.getNode()))
		{
			if (hasSeat)
				break;

			hasSeat = item.getCanSit();
		}

		if (hasSeat && this.invoker.getRoom().getRoomObjectsHandler().isSomethingOnNode(this.invoker.getNodeInFront()))
		{
			return true;
		}
		else if (hasSeat)
		{
			this.invoker.setGoal(this.invoker.getNodeInFront());
			return true;
		}

		if (this.invoker.getStatuses().containsKey("sit"))
		{
			this.invoker.getStatuses().remove("sit");
			this.invoker.updateStatus();
		}
		return true;
	}

}
