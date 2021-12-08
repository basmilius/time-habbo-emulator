package com.basmilius.time.habbohotel.habbo.messenger;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Friend
{

	private final Habbo myHabbo;
	private final Habbo habbo;
	private int relation;

	public Friend(Habbo myHabbo, Habbo habbo, int relation)
	{
		this.myHabbo = myHabbo;
		this.habbo = habbo;
		this.relation = relation;
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public Habbo getMyHabbo()
	{
		return this.myHabbo;
	}

	public int getRelation()
	{
		return this.relation;
	}

	public void setRelation(int relation)
	{
		this.relation = relation;

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE messenger_friendships SET relation = ? WHERE user_one_id = ? AND user_two_id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, relation);
					statement.setInt(2, getMyHabbo().getId());
					statement.setInt(3, getHabbo().getId());
					statement.executeUpdate();
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

}
