package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;

@Command(
		executable = "cmd.syntax.sit",
		syntax = ":sit"
)
public class SitCommand extends ICommand
{

	public SitCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);

		this.setAllowBots(true);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.invoker.getIsWalking() || this.invoker.getIsSitting())
			return true;

		if (this.invoker.getNode().equals(this.invoker.getRoom().getRoomData().getRoomModel().getDoorNode()))
			return true;

		if (!(this.invoker.getBodyRotation() % 2 == 0))
		{
			this.invoker.setRotation(this.invoker.getBodyRotation() - 1);
		}
		this.invoker.getStatuses().put("sit", "0.5");
		this.invoker.updateStatus();
		return true;
	}

}
