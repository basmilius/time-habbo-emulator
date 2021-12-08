package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.util.ObjectUtils;

@Command(
		executable = "cmd.syntax.enable",
		syntax = ":enable <effect>"
)
public class EnableCommand extends ICommand
{

	public EnableCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);

		this.setAllowBots(true);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.isServerUnitOrHasSubscription("pixel_vip"))
		{
			if (parts.length == 2 && ObjectUtils.isNumeric(parts[1]))
			{
				int effectId = Integer.parseInt(parts[1]);

				if (effectId == 102 && (!this.isUser() || !this.isServerUnitOrHasPermission("is_staff")))
					return false;

				this.invoker.applyEffect(effectId);

				return true;
			}
			return false;
		}
		this.sendUserNeedsVipMessage("cmd.syntax.enable");

		return true;
	}

}
