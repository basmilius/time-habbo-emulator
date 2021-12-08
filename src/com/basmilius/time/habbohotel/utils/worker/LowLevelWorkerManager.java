package com.basmilius.time.habbohotel.utils.worker;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.core.Version;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.util.SystemUtils;

import java.lang.management.ManagementFactory;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LowLevelWorkerManager extends IManager
{

	private final Timer timer;

	public LowLevelWorkerManager()
	{
		this.timer = new Timer();
	}

	@Override
	public final void initialize() throws Exception
	{
		(new LowLevelWorkerTask()).run();
	}

	@Override
	public final void dispose()
	{
		this.timer.cancel();

		final Map<String, Object> properties = new HashMap<>();
		properties.put("server.os", "");
		properties.put("server.cpu", -1);
		properties.put("server.ram", -1);
		properties.put("server.version", Version.VERSION_STRING);
		properties.put("server.online", false);
		properties.put("users.online", 0);

		LowLevelWorkerManager.this.saveServerStatus(properties);
	}

	private class LowLevelWorkerTask extends TimerTask
	{

		@Override
		public final void run()
		{
			// ReEnable the timer.
			LowLevelWorkerManager.this.timer.schedule(new LowLevelWorkerTask(), 20000);
			
			// Run Garbage collector.
			System.gc();
			
			// Save server settings.
			this.saveServerStatus();
		}

		private void saveServerStatus()
		{
			final Map<String, Object> properties = new HashMap<>();
			properties.put("server.os", SystemUtils.getOsName());
			properties.put("server.cpu", String.format("%s%%", Double.toString(Math.abs(ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage()))));
			properties.put("server.ram", String.format("%.3fMB", ((Bootstrap.freeMemory() / 1024) / 1024)));
			properties.put("server.version", Version.VERSION_STRING);
			properties.put("server.online", true);
			properties.put("users.online", Bootstrap.getEngine().getGame().getHabboManager().getOnlineHabbos().size());

			LowLevelWorkerManager.this.saveServerStatus(properties);
		}

	}

	private void saveServerStatus(final Map<String, Object> properties)
	{
		for (final Map.Entry<String, Object> property : properties.entrySet())
		{
			final String propertyName = property.getKey();
			final String propertyValue = property.getValue().toString();

			try
			{
				final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO `server_status` (property, value, last_update) VALUES (?, ?, UNIX_TIMESTAMP()) ON DUPLICATE KEY UPDATE property = VALUES(property), value = VALUES(value), last_update = VALUES(last_update)");
				{
					if (statement != null)
					{
						statement.setString(1, propertyName);
						statement.setString(2, propertyValue);
						statement.execute();
					}
				}
			}
			catch (SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
			}
		}
	}

}
