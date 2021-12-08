package com.basmilius.time.core;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for reading the commandline arguments.
 */
public class CommandLineManager extends IManager
{

	private final Map<String, String> arguments;

	/**
	 * Constructor.
	 *
	 * @param arguments arguments passed to this Java application.
	 */
	public CommandLineManager(final String... arguments)
	{
		this.arguments = new HashMap<>();

		for (final String argument : arguments)
		{
			String[] data = argument.split("=");
			this.arguments.put(data[0], ((data.length > 1) ? data[1] : ""));
		}
	}

	/**
	 * Checks if the argument exists and returns a boolean.
	 *
	 * @param argument The argument to check.
	 * @return boolean
	 */
	public boolean hasArgument(final String argument)
	{
		return ((this.arguments.containsKey(argument) && ((System.getProperty(argument) != null))));
	}

	/**
	 * Gets the value of an argument and returns the boolean value.
	 *
	 * @param argument The argument to return.
	 * @return boolean
	 */
	public boolean getBoolean(final String argument)
	{
		if (this.hasArgument(argument))
		{
			if (this.arguments.containsKey(argument))
			{
				return this.arguments.get(argument).equals("1");
			}
			else
			{
				return System.getProperty(argument).equals("1");
			}
		}
		return false;
	}

	/**
	 * Gets the value of an argument and returns the integer value.
	 *
	 * @param argument The argument to return.
	 * @return int
	 */
	public int getInt(final String argument)
	{
		if (this.hasArgument(argument))
		{
			if (this.arguments.containsKey(argument))
			{
				return Integer.parseInt(this.arguments.get(argument));
			}
			else
			{
				return Integer.parseInt(System.getProperty(argument));
			}
		}
		return 0;
	}

	/**
	 * Gets the value of an argument and returns the String value.
	 *
	 * @param argument The argument to return.
	 * @return String
	 */
	public String getString(final String argument)
	{
		if (this.hasArgument(argument))
		{
			if (this.arguments.containsKey(argument))
			{
				return this.arguments.get(argument);
			}
			else
			{
				return System.getProperty(argument);
			}
		}
		return "";
	}

	/**
	 * Initializes this manager.
	 */
	@Override
	public void initialize()
	{

	}

	/**
	 * Disposes this manager.
	 */
	@Override
	public void dispose()
	{
		this.arguments.clear();
		Bootstrap.getEngine().getLogging().log(CommandLineManager.class, "Command Line Manager disposed!");
	}

}
