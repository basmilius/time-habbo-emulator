package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HabboManager extends IManager
{

	private final List<Habbo> habbos;

	public HabboManager()
	{
		this.habbos = new ArrayList<>();
	}

	public List<Habbo> getOnlineHabbos()
	{
		return this.habbos.stream().filter(Habbo::isOnline).collect(Collectors.toList());
	}

	public Habbo getHabbo(final int id)
	{
		for (final Habbo habbo : this.habbos)
			if (habbo.getId() == id)
				return habbo;

		return null;
	}

	public final Habbo getHabboFromId(final int id)
	{
		return this.getHabboFromId(id, null);
	}

	private Habbo getHabboFromId(final int id, final ResultSet dataRow)
	{
		if (this.hasHabbo(id))
		{
			final Habbo habbo = this.getHabbo(id);

			try
			{
				if (dataRow != null)
				{
					habbo.updateData(dataRow);
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

			return habbo;
		}
		else
		{
			try
			{
				final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users WHERE id = ?");
				{
					if (statement != null)
					{
						statement.setInt(1, id);
						final ResultSet result = statement.executeQuery();

						if (result.next())
						{
							Habbo newHabbo = new Habbo(result);
							this.habbos.add(newHabbo);
							return newHabbo;
						}
					}
				}
				return null;
			}
			catch (SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
				return null;
			}
		}
	}

	public Habbo getHabboFromUsername(final String name)
	{
		for (final Habbo habbo : this.habbos)
			if (habbo.getUsername().equalsIgnoreCase(name))
				return habbo;

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users WHERE username = ?");
			{
				if (statement != null)
				{
					statement.setString(1, name);
					final ResultSet result = statement.executeQuery();

					if (result.next())
					{
						final Habbo habbo = new Habbo(result);
						this.habbos.add(habbo);
						return habbo;
					}
				}
			}
			return null;
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			return null;
		}
	}

	public Habbo getHabboFromSessionTicket(String ssoTicket)
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users WHERE auth_ticket = ?");
			{
				if (statement != null)
				{
					statement.setString(1, ssoTicket);
					final ResultSet result = statement.executeQuery();

					if (result.next())
					{
						return this.getHabboFromId(result.getInt("id"), result);
					}
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
		return null;
	}

	public boolean hasHabbo(final Habbo habbo)
	{
		for (Habbo hab : this.habbos)
			if (hab.getId() == habbo.getId())
				return true;

		return false;
	}

	public boolean hasHabbo(final int id)
	{
		for (Habbo hab : this.habbos)
			if (hab.getId() == id)
				return true;

		return false;
	}

	public void setValue(final String key, final boolean value)
	{
		for (final Habbo habbo : this.habbos)
		{
			habbo.setValue(key, value);
		}
	}

	public void disposeHabbo(final Habbo habbo)
	{
		this.habbos.remove(habbo);
	}

	@Override
	public void initialize()
	{

	}

	@Override
	public void dispose()
	{
		this.habbos.clear();
	}

}
