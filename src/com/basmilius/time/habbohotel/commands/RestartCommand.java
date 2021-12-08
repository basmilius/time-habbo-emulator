package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;

@Command(
		executable = "cmd.syntax.restart",
		syntax = ":restart"
)
public class RestartCommand extends ICommand
{

	public RestartCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		throw new CommandNotAvailableException(this, "Need a new restart command script.");
	}

}
