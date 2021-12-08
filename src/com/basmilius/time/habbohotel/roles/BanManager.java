package com.basmilius.time.habbohotel.roles;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.enums.CloseClientReason;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.outgoing.handshake.DisconnectReasonMessageComposer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BanManager extends IManager
{

	public final void createBan(final Habbo habbo, int hours, final String reason, final Habbo staffHabbo)
	{
		if (hours < 1)
			hours = 1;

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO bans (user_id, user_staff_id, ban_start, ban_expire, ban_expired, ban_reason) VALUES (?, ?, UNIX_TIMESTAMP(), (UNIX_TIMESTAMP() + ?), '0', ?)");
			{
				if (statement != null)
				{
					statement.setInt(1, habbo.getId());
					statement.setInt(2, staffHabbo.getId());
					statement.setInt(3, (hours * 3600));
					statement.setString(4, reason);
					statement.execute();
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}

		if (habbo.isOnline())
		{
			habbo.getConnection().send(new DisconnectReasonMessageComposer(CloseClientReason.BANNED));
		}
	}

	public final int getBansCount(final Habbo habbo)
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT 1 FROM bans WHERE user_id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, habbo.getId());
					final ResultSet result = statement.executeQuery();

					int count = 0;

					while (result.next())
						count++;

					return count;
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
		return 0;
	}

	public final boolean isBanned(final Habbo habbo)
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT 1 FROM bans WHERE user_id = ? AND ban_expired = '0' AND ban_expire > UNIX_TIMESTAMP()");
			if (statement != null)
			{
				statement.setInt(1, habbo.getId());
				final ResultSet result = statement.executeQuery();
				return result.next();
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
		
		return false;
	}

	@Override
	public final void initialize() throws Exception
	{

	}

	@Override
	public final void dispose()
	{

	}

}
