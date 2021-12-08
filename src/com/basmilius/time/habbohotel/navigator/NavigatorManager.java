package com.basmilius.time.habbohotel.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NavigatorManager extends IManager
{

	private final List<OfficialItem> _publics;

	public NavigatorManager()
	{
		this._publics = new ArrayList<>();
	}

	void loadOfficials() throws SQLException
	{
		this._publics.clear();
		PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM navigator_publics");
		{
			ResultSet result = statement.executeQuery();

			while (result.next())
			{
				this._publics.add(new OfficialItem(result));
			}
		}
	}

	public List<OfficialItem> getOfficials()
	{
		return this._publics;
	}

	public List<OfficialItem> getOfficials(Integer parent_id)
	{
		return this.getOfficials().stream().filter(item -> item.getParentId() == parent_id).collect(Collectors.toList());
	}

	@Override
	public void initialize()
	{
		Bootstrap.getEngine().getLogging().logNoNewLine(NavigatorManager.class, "Loading navigator .. ");

		try
		{
			this.loadOfficials();

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
		this._publics.clear();
	}

}
