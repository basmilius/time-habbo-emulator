package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.util.ObjectUtils;

@Command(
		executable = "cmd.syntax.carry",
		syntax = ":carry <item>"
)
public class CarryCommand extends ICommand
{

	public CarryCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);

		this.setAllowBots(true);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.isBot() && parts.length == 1)
		{
			parts = new String[]{":carry", "48"};
		}
		if (this.isServerUnitOrHasSubscription("pixel_vip"))
		{
			if (parts.length == 2 && ObjectUtils.isNumeric(parts[1]))
			{
				int itemId = Integer.parseInt(parts[1]);
				this.invoker.carryItem(itemId);

				return true;
			}
			return false;
		}

		this.sendUserNeedsVipMessage("cmd.syntax.carry");

		return true;
	}

}
