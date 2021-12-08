package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.SpecialEffects;

@Command(
		executable = "cmd.syntax.habnam",
		syntax = ":habnam"
)
public class HabNamCommand extends ICommand
{

	public HabNamCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);

		this.setAllowBots(true);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (!this.isPet())
		{
			if (this.invoker.getEffectId() == SpecialEffects.HABNAM_STYLE)
			{
				this.invoker.applyEffect(SpecialEffects.NONE);
			}
			else
			{
				this.invoker.applyEffect(SpecialEffects.HABNAM_STYLE);
			}
			return true;
		}
		return false;
	}

}
