package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;

@Command(
		executable = "cmd.syntax.commands",
		syntax = ":commands"
)
public class CommandsCommand extends ICommand
{

	public CommandsCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		throw new CommandNotAvailableException(this, "Not yet implemented.");
	}

}
