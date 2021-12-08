package com.basmilius.time.habbohotel.roles;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionsManager extends IManager
{

	private final Map<Integer, List<String>> _permissions;

	public PermissionsManager()
	{
		this._permissions = new HashMap<>();
	}

	public List<String> getForRank(Integer rank)
	{
		return this._permissions.get(rank);
	}

	@Override
	public void initialize()
	{
		Bootstrap.getEngine().getLogging().logNoNewLine(PermissionsManager.class, "Loading permissions .. ");

		try
		{
			PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM permissions");
			{
				ResultSet result = statement.executeQuery();
				ResultSetMetaData resultData = result.getMetaData();

				List<String> p2 = new ArrayList<>();

				while (result.next())
				{
					List<String> p = new ArrayList<>();
					for (int i = 1; i <= resultData.getColumnCount(); i++)
					{
						if (result.getString(resultData.getColumnName(i)).equals("1"))
						{
							p.add(resultData.getColumnName(i));
						}

						if (!p2.contains(resultData.getColumnName(i)))
						{
							p2.add(resultData.getColumnName(i));
						}
					}
					this._permissions.put(result.getInt("rank_id"), p);
				}

				this._permissions.put(-1, p2);
			}

			Bootstrap.getEngine().getLogging().logOK();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			Bootstrap.getEngine().onLaunchFail();
		}
	}

	@Override
	public void dispose()
	{
		this._permissions.clear();
	}

}
