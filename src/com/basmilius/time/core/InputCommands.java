package com.basmilius.time.core;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.outgoing.general.HotelClosingComposer;
import com.basmilius.time.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Parser for console commands.
 */
public class InputCommands implements Runnable
{

	/**
	 * Constructor.
	 */
	public InputCommands()
	{
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Constantly check whether there are any commands typed into the console.
	 */
	@Override
	public void run()
	{
		try
		{
			final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			while (br.readLine() != null)
			{
				final Console console = System.console();

				Bootstrap.getEngine().getLogging().pause();
				Bootstrap.getEngine().getLogging().logBlank();

				final String command = console.readLine(" > ");
				final String[] commandParts = command.split(" ");

				Bootstrap.getEngine().getLogging().resume();

				switch (commandParts[0])
				{
					case "gc":
					{
						Bootstrap.gc();
						Bootstrap.freeMemory();
						break;
					}
					case "maintenance":
					{
						int duration = 15;
						int minutes = 5;

						if (commandParts.length > 1 && ObjectUtils.isNumeric(commandParts[1]))
						{
							minutes = Integer.parseInt(commandParts[1]);
						}

						if (commandParts.length > 2 && ObjectUtils.isNumeric(commandParts[2]))
						{
							minutes = Integer.parseInt(commandParts[2]);
						}

						Bootstrap.getEngine().getServer().getClientManager().sendBroadcastResponse(new HotelClosingComposer(minutes, duration));
						break;
					}
					case "shutdown":
					{
						Bootstrap.getEngine().prepareShutdown();
						break;
					}
					case "update":
					{
						if (commandParts.length > 1)
						{
							switch (commandParts[1])
							{
								case "catalog":
								{
									Bootstrap.getEngine().getGame().getCatalogueManager().reloadCatalogue();
									break;
								}
								case "config":
								{
									Bootstrap.getEngine().getConfig().initialize();
									break;
								}
								case "items":
								{
									Bootstrap.getEngine().getGame().getItemsManager().reloadItems();
									break;
								}
								default:
								{
									System.err.println("Update command does not exists.");
									break;
								}
							}
						}
						break;
					}
					default:
					{
						System.err.println(" > Command does not exists!");
						break;
					}
				}
			}
		}
		catch (NullPointerException | IOException ignored)
		{

		}
	}

}
