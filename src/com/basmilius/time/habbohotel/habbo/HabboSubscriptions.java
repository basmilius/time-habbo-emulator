package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.outgoing.users.FuserightComposer;
import com.basmilius.time.communication.messages.outgoing.users.HabboClubComposer;
import com.basmilius.time.util.TimeUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HabboSubscriptions
{

	private final Habbo habbo;
	private final Map<String, Map<String, Integer>> _subscriptions;

	public HabboSubscriptions(Habbo habbo)
	{
		this.habbo = habbo;
		this._subscriptions = new HashMap<>();

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users_subscriptions WHERE user_id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, this.habbo.getId());
					final ResultSet result = statement.executeQuery();

					while (result.next())
					{
						Map<String, Integer> data = new HashMap<>();
						data.put("activated", result.getInt("timestamp_activated"));
						data.put("expiration", result.getInt("timestamp_expire"));
						this._subscriptions.put(result.getString("subscription_type"), data);
					}
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public Boolean hasSubscription(String id)
	{
		return this._subscriptions.containsKey(id);
	}

	public Map<String, Integer> getSubscription(String id)
	{
		if (this.hasSubscription(id))
			return this._subscriptions.get(id);
		return null;
	}

	public void createOrExtendSubscription(final String id, final int addTime) throws Exception
	{
		this.createOrExtendSubscription(id, addTime, true);
	}

	public void createOrExtendSubscription(final String id, final int addTime, final boolean isDays) throws Exception
	{
		int startTime = TimeUtils.getUnixTimestamp();
		int duration = (isDays) ? (addTime * (3600 * 24)) : addTime;
		int endTime;

		if (this.hasSubscription(id))
		{
			startTime = this.getSubscription(id).get("expiration");
			endTime = (startTime + duration);
		}
		else
		{
			endTime = (startTime + duration);
		}

		if (this.hasSubscription(id))
		{
			PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE users_subscriptions SET timestamp_expire = ? WHERE user_id = ? AND subscription_type = ?");
			{
				statement.setInt(1, endTime);
				statement.setInt(2, this.habbo.getId());
				statement.setString(3, id);
				statement.executeUpdate();
			}
		}
		else
		{
			PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO users_subscriptions (user_id, subscription_type, timestamp_activated, timestamp_expire) VALUES (?, ?, ?, ?)");
			{
				statement.setInt(1, this.habbo.getId());
				statement.setString(2, id);
				statement.setInt(3, (TimeUtils.getUnixTimestamp()));
				statement.setInt(4, endTime);
				statement.execute();
			}
		}

		if (this._subscriptions.containsKey(id))
		{
			this._subscriptions.remove(id);
		}

		Map<String, Integer> sdata = new HashMap<>();
		sdata.put("activated", startTime);
		sdata.put("expiration", endTime);
		this._subscriptions.put(id, sdata);

		this.habbo.getConnection().send(new HabboClubComposer(this.habbo, true));
		this.habbo.getConnection().send(new FuserightComposer(this.habbo));
	}

	public void dispose()
	{
		this._subscriptions.clear();
	}

}
