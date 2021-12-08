package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.SpecialEffects;

@Command(
		executable = "cmd.syntax.moonwalk",
		syntax = ":moonwalk"
)
public class MoonwalkCommand extends ICommand
{

	public MoonwalkCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);

		this.setAllowBots(true);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.isServerUnitOrHasSubscription("habbo_club") || this.isServerUnitOrHasSubscription("pixel_vip"))
		{
			if (this.invoker.getEffectId() == SpecialEffects.MARS_LICK)
			{
				this.invoker.applyEffect(SpecialEffects.NONE);
			}
			else
			{
				this.invoker.applyEffect(SpecialEffects.MARS_LICK);
			}
			return true;
		}
		return false;
	}

}
