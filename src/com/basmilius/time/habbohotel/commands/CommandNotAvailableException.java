package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;

public class CommandNotAvailableException extends Exception
{

	private final ICommand command;

	private final String message;

	public CommandNotAvailableException(ICommand command, String message)
	{
		this.command = command;
		this.message = message;
	}

	public ICommand getCommand()
	{
		return command;
	}

	@Override
	public String getMessage()
	{
		return message;
	}
}
