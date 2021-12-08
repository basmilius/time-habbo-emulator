package com.basmilius.time;

import com.basmilius.time.collections.ManagersList;
import com.basmilius.time.core.CommandLineManager;
import com.basmilius.time.core.ConfigurationManager;
import com.basmilius.time.core.InputCommands;
import com.basmilius.time.core.LogManager;
import com.basmilius.time.habbohotel.GameManager;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.mods.ModsManager;
import com.basmilius.time.network.ServerManager;
import com.basmilius.time.storage.StorageManager;
import com.basmilius.time.util.TimeUtils;
import com.google.common.collect.ForwardingList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public final class BootstrapEngine
{

	private final String[] args;
	private boolean isReady;
	private final ForwardingList<IManager> managers;
	private Runtime runtime;
	private int timeStarted;

	public BootstrapEngine(final String[] args) throws Exception
	{
		this.args = args;
		this.managers = new ManagersList(new ArrayList<>());

		this.addManager(CommandLineManager.class, String[].class, this.args);
		this.addManager(ConfigurationManager.class, String.class, "configuration/config.properties");
		this.addManager(LogManager.class);
		this.addManager(GameManager.class);
		this.addManager(StorageManager.class);
		this.addManager(ServerManager.class);
		this.addManager(ModsManager.class);
	}

	public final String[] getArgs ()
	{
		return this.args;
	}

	public final boolean isReady ()
	{
		return this.isReady;
	}

	public final Runtime getRuntime ()
	{
		return this.runtime;
	}

	public final int getTimeStarted ()
	{
		return this.timeStarted;
	}

	public final ConfigurationManager getConfig()
	{
		return this.getManager(ConfigurationManager.class);
	}

	public final StorageManager getDatabase()
	{
		return this.getManager(StorageManager.class);
	}

	public final GameManager getGame()
	{
		return this.getManager(GameManager.class);
	}

	public final LogManager getLogging()
	{
		return this.getManager(LogManager.class);
	}

	public final ServerManager getServer()
	{
		return this.getManager(ServerManager.class);
	}

	public final void addManager(Class<? extends IManager> manager) throws IllegalAccessException, InstantiationException
	{
		this.managers.add(manager.newInstance());
	}

	public final <T> void addManager(Class<? extends IManager> manager, Class<?> types, T args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException
	{
		this.managers.add(manager.getConstructor(types).newInstance(args));
	}

	public final void onLaunchFail()
	{
		this.getLogging().logErrorLine("Launching TimeEmulator failed.");
		prepareShutdown();
	}

	public final void prepareShutdown()
	{
		System.exit(0);
	}

	@SuppressWarnings("unchecked")
	public final <T extends IManager> T getManager(Class<T> manager)
	{
		for (IManager managerInstance : this.managers)
			if (manager.isInstance(managerInstance))
			{
				return (T) managerInstance;
			}
		return null;
	}

	public final void run() throws Exception
	{
		this.runtime = Runtime.getRuntime();

		/**
		 * Reset the default charset to UTF-8.
		 */
		System.setProperty("file.encoding", "UTF-8");
		final Field charset = Charset.class.getDeclaredField("defaultCharset");
		charset.setAccessible(true);
		charset.set(null, null);

		/**
		 * Apply the ShutdownHook.
		 */
		this.getRuntime().addShutdownHook(new ShutdownHookThread());

		new BootstrapLauncher();

		this.isReady = true;
		this.timeStarted = TimeUtils.getUnixTimestamp();

		Bootstrap.freeMemory();

		new InputCommands();
	}

	public void dispose()
	{
		final CommandLineManager commandLineManager = this.getManager(CommandLineManager.class);
		final GameManager gameManager = this.getManager(GameManager.class);
		final ConfigurationManager configurationManager = this.getManager(ConfigurationManager.class);
		final StorageManager storageManager = this.getManager(StorageManager.class);
		final ModsManager modsManager = this.getManager(ModsManager.class);

		if (commandLineManager != null)
			commandLineManager.dispose();

		if (gameManager != null)
			gameManager.dispose();

		if (configurationManager != null)
			configurationManager.dispose();

		if (storageManager != null)
			storageManager.dispose();

		if (modsManager != null)
			modsManager.dispose();
	}

}
