package com.basmilius.time.core;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.util.ObjectUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Manager for reading configuration files.
 */
public class ConfigurationManager extends IManager
{

	private final String configFile;
	private final Properties lProperties;
	private final Properties mProperties;

	private Dictionary<String, Object> dProperties;

	/**
	 * Constructor.
	 *
	 * @param path Configuration file path.
	 */
	public ConfigurationManager(final String path)
	{
		this.configFile = path;
		this.dProperties = new Hashtable<>();
		this.lProperties = new Properties();
		this.mProperties = new Properties();
	}

	/**
	 * Initializes everything including local configuration and database configuration.
	 */
	@Override
	public void initialize()
	{
		this.dProperties = new Hashtable<>();

		try
		{
			Bootstrap.getEngine().getLogging().logNoNewLine(ConfigurationManager.class, "Loading server configuration .. ");

			this.lProperties.load(this.getClass().getResourceAsStream("/resources/local-config.properties"));
			this.mProperties.load(new FileInputStream(this.configFile));

			Bootstrap.getEngine().getLogging().logOK();
		}
		catch (IOException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
		}

		this.loadFromDatabase();
	}

	/**
	 * Loads the configuration from the database.
	 */
	public void loadFromDatabase()
	{
		if (Bootstrap.getEngine().getDatabase() == null || !Bootstrap.getEngine().getDatabase().isReady())
			return;

		try
		{
			PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT `key`, `value` FROM server_config ORDER BY `key` ASC");

			if (statement == null)
				return;

			ResultSet result = statement.executeQuery();

			if (result == null)
				return;

			while (result.next())
			{
				this.dProperties.put(result.getString("key"), result.getObject("value"));
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	/**
	 * Gets a configuration value as a String.
	 *
	 * @param key Configuration Key.
	 * @return String
	 */
	public String getString(final String key)
	{
		return this.getString(key, "");
	}

	/**
	 * Gets a configuration value as a String.
	 *
	 * @param key          Configuration Key.
	 * @param defaultValue Default value.
	 * @return String
	 */
	public String getString(final String key, final String defaultValue)
	{
		String value = (String) this.getValue(key);

		if (value == null)
			return defaultValue;

		return value;
	}

	/**
	 * Gets a configuration value as a String array.
	 *
	 * @param key          Configuration Key.
	 * @param defaultValue Default value.
	 * @return String[]
	 */
	public String[] getStringArray(final String key, final String[] defaultValue)
	{
		String result = this.getString(key, null);

		if (result == null)
			return defaultValue;

		return result.split(",");
	}

	/**
	 * Gets a configuration value as a boolean.
	 *
	 * @param key Configuration Key.
	 * @return boolean
	 */
	public boolean getBoolean(final String key)
	{
		return this.getBoolean(key, false);
	}

	/**
	 * Gets a configuration value as a boolean.
	 *
	 * @param key          Configuration Key.
	 * @param defaultValue Default value.
	 * @return boolean
	 */
	public boolean getBoolean(final String key, final boolean defaultValue)
	{
		return this.getString(key, (defaultValue) ? "1" : "0").equals("1") || this.getString(key, (defaultValue) ? "true" : "false").equals("true");
	}

	/**
	 * Gets a configuration value as an integer.
	 *
	 * @param key Configuration Key.
	 * @return int
	 */
	public int getInt(final String key)
	{
		return this.getInt(key, 0);
	}

	/**
	 * Gets a configuration value as an integer.
	 *
	 * @param key          Configuration Key.
	 * @param defaultValue Default value.
	 * @return int
	 */
	public int getInt(final String key, final int defaultValue)
	{
		String value = (String) this.getValue(key);

		if (value == null || !ObjectUtils.isNumeric(value))
			return defaultValue;

		return Integer.parseInt(value);
	}

	/**
	 * Gets a configuration value as a String array.
	 *
	 * @param key          Configuration Key.
	 * @param defaultValue Default value.
	 * @return String[]
	 */
	public int[] getIntArray(final String key, final int[] defaultValue)
	{
		final String result = this.getString(key, null);

		if (result == null)
			return defaultValue;

		final String[] parts = result.split(",");
		final int[] integers = new int[parts.length];

		int i = 0;
		for (final String part : parts)
		{
			integers[i++] = Integer.parseInt(part);
		}

		return integers;
	}

	/**
	 * Gets a configuration value as an object.
	 *
	 * @param key Configuration Key.
	 * @return int
	 */
	private Object getValue(final String key)
	{
		if (this.mProperties.containsKey(key))
		{
			return this.mProperties.getProperty(key);
		}
		else if (this.dProperties.get(key) != null)
		{
			return this.dProperties.get(key);
		}
		else if (this.lProperties.containsKey(key))
		{
			return this.lProperties.getProperty(key);
		}
		else
		{
			if (this.getBoolean("debug.mode", false))
			{
				Bootstrap.getEngine().getLogging().logErrorLine("[CONFIG] Config key is missing! " + key);
			}
			return null;
		}
	}

	/**
	 * Gets the properties that starts with key.
	 *
	 * @param key Starts With param.
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getPropertiesStartWith(final String key)
	{
		Map<String, T> properties = new HashMap<>();

		for (Object obj : this.lProperties.keySet())
		{
			String oKey = (String) obj;
			if (oKey.startsWith(key))
			{
				properties.put(oKey, (T) this.lProperties.getProperty(oKey));
			}
		}

		for (Object obj : this.mProperties.keySet())
		{
			String oKey = (String) obj;
			if (oKey.startsWith(key))
			{
				properties.put(oKey, (T) this.mProperties.getProperty(oKey));
			}
		}

		return properties;
	}

	/**
	 * Disposes this manager.
	 */
	@Override
	public void dispose()
	{
		this.mProperties.clear();
		Bootstrap.getEngine().getLogging().log(ConfigurationManager.class, "Configuration Manager disposed!");
	}

}
