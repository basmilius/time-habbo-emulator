package com.basmilius.time.storage;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public final class StorageManager extends IManager
{

	private HikariDataSource dataSource;
	private boolean isReady;

	private int connectionNum;
	private Connection[] connections;
	private Statement statement;

	public StorageManager()
	{
		this.connectionNum = 0;
		this.isReady = false;
	}

	@Override
	public void initialize() throws Exception
	{
		try
		{
			Bootstrap.getEngine().getLogging().logNoNewLine(StorageManager.class, "Setting up a database connection .. ");
			Class.forName("com.mysql.jdbc.Driver");

			final HikariConfig config = new HikariConfig();
			config.setMaximumPoolSize(Bootstrap.getEngine().getConfig().getInt("db.pool.size", 5));
			config.setDriverClassName("com.mysql.jdbc.Driver");
			config.setJdbcUrl("jdbc:mysql://" + Bootstrap.getEngine().getConfig().getString("db.hostname") + ":" + Bootstrap.getEngine().getConfig().getString("db.port") + "/" + Bootstrap.getEngine().getConfig().getString("db.database"));
			config.addDataSourceProperty("user", Bootstrap.getEngine().getConfig().getString("db.username", "root"));
			config.addDataSourceProperty("password", Bootstrap.getEngine().getConfig().getString("db.password", ""));
			this.dataSource = new HikariDataSource(config);
			this.isReady = true;

			this.connections = new Connection[Bootstrap.getEngine().getConfig().getInt("db.pool.size", 5)];
			for (int i = 0; i < this.connections.length; i++)
			{
				this.connections[i] = this.dataSource.getConnection();
			}

			this.statement = this.connections[0].createStatement();

			Bootstrap.getEngine().getLogging().logOK();
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

	private Connection getConnection()
	{
		this.connectionNum++;
		if (this.connectionNum >= this.connections.length)
			this.connectionNum = 0;

		return this.connections[this.connectionNum];
	}

	public final boolean isReady()
	{
		return this.isReady;
	}

	public final PreparedStatement prepare(final String query) throws SQLException
	{
		if (!this.isReady)
			throw new SQLException("Database connection is not ready yet.");

		try
		{
			return this.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		}
		catch (MySQLNonTransientConnectionException ignored)
		{
			try
			{
				this.initialize();
				return this.prepare(query);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public Integer readInt32(String query) throws SQLException
	{
		ResultSet result = this.statement.executeQuery(query);
		result.first();
		return result.getInt(query.split(" ")[1]);
	}

	@Override
	public void dispose()
	{
		this.dataSource.close();
		Bootstrap.getEngine().getLogging().log(StorageManager.class, "Storage Manager disposed!");
	}

}