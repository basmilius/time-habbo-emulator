package com.basmilius.time;

import com.basmilius.time.core.CommandLineManager;
import com.basmilius.time.core.Version;
import com.basmilius.time.mods.ModsManager;

public class BootstrapLauncher
{

	public BootstrapLauncher() throws Exception
	{
		/**
		 * Initialize logging.
		 */
		Bootstrap.getEngine().getLogging().initialize();
		Bootstrap.getEngine().getLogging().clear();

		/**
		 * Generate TimeEmu logo for startup.
		 */
		Bootstrap.getEngine().getLogging().logLine("@|white                                                              |@");
		Bootstrap.getEngine().getLogging().logLine("@|white  |    o                             |         |              |@");
		Bootstrap.getEngine().getLogging().logLine("@|white  |--- .,-.-.,---.    ,---.,-.-..   .|    ,---.|--- ,---.,---.|@");
		Bootstrap.getEngine().getLogging().logLine("@|white  |    || | ||---'    |---'| | ||   ||    ,---||    |   ||    |@");
		Bootstrap.getEngine().getLogging().logLine("@|white  `---'`` ' '`---'    `---'` ' '`---'`---'`---^`---'`---'`    |@");
		Bootstrap.getEngine().getLogging().logLine(String.format("@|white                                     %s |@", Version.VERSION_STRING));
		Bootstrap.getEngine().getLogging().logBlank();

		/**
		 * Initialize configuration system.
		 */
		Bootstrap.getEngine().getConfig().initialize();

		/**
		 * Check for delay console argument.
		 */
		final CommandLineManager cml = Bootstrap.getEngine().getManager(CommandLineManager.class);
		if (cml != null)
		{
			if (Bootstrap.getEngine().getArgs() != null && cml.hasArgument("delay"))
			{
				int delay = cml.getInt("delay");
				Thread.sleep(delay);
			}
		}

		/**
		 * Initialize database and caching.
		 */
		Bootstrap.getEngine().getDatabase().initialize();
		Bootstrap.getEngine().getConfig().loadFromDatabase();

		/**
		 * Initialize game-systems.
		 */
		Bootstrap.getEngine().getGame().initialize();

		/**
		 * Initialize networking.
		 */
		Bootstrap.getEngine().getServer().setHost(Bootstrap.getEngine().getConfig().getString("game.host", "127.0.0.1"));
		Bootstrap.getEngine().getServer().setPort(Bootstrap.getEngine().getConfig().getIntArray("game.port", new int[]{993, 2413}));
		Bootstrap.getEngine().getServer().initialize();

		/**
		 * Initialize modification system.
		 */
		final ModsManager modsManager = Bootstrap.getEngine().getManager(ModsManager.class);
		if (modsManager != null)
		{
			modsManager.initialize();
		}

		/**
		 * Finish launching TimeEmulator.
		 */
		Bootstrap.getEngine().getLogging().log(BootstrapLauncher.class, "Time Emulator has finished loading. Now ready for connections!");
		Bootstrap.getEngine().getLogging().log(BootstrapLauncher.class, "Press ENTER to enter a server command.");
		Bootstrap.getEngine().getLogging().logBlank();
	}

}
