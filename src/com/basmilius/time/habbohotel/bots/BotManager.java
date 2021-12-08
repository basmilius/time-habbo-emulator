package com.basmilius.time.habbohotel.bots;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager for bots.
 */
public class BotManager extends IManager
{

	private final List<Bot> bots;

	/**
	 * Constructor.
	 */
	public BotManager()
	{
		this.bots = new ArrayList<>();
	}

	public final List<Bot> getBotsForHabbo(final Habbo habbo)
	{
		final List<Bot> bots = new ArrayList<>();

		if (habbo != null)
		{
			try
			{
				final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM bots WHERE room_id = 0 AND user_id = ?");
				{
					if (statement != null)
					{
						statement.setInt(1, habbo.getId());
						final ResultSet result = statement.executeQuery();

						while (result.next())
						{
							if (!this.containsBot(result.getInt("id")))
							{
								final Bot bot = new Bot(result);
								this.bots.add(bot);
								bots.add(bot);
							}
							else
							{
								bots.add(this.getBot(result.getInt("id")));
							}
						}
					}
				}
			}
			catch (SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
			}
		}

		return bots;
	}

	/**
	 * Gets all the bots for the specified Room.
	 *
	 * @param room The Room.
	 * @return List
	 */
	public List<Bot> getBotsForRoom(final Room room)
	{
		final List<Bot> bots = new ArrayList<>();

		if (room != null)
		{
			try
			{
				final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM bots WHERE room_id = ?");
				{
					if (statement != null)
					{
						statement.setInt(1, room.getRoomData().getId());
						final ResultSet result = statement.executeQuery();

						while (result.next())
						{
							if (!this.containsBot(result.getInt("id")))
							{
								final Bot bot = new Bot(result);
								this.bots.add(bot);
								bots.add(bot);
							}
							else
							{
								bots.add(this.getBot(result.getInt("id")));
							}
						}
					}
				}
			}
			catch (SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
			}
		}

		return bots;
	}

	/**
	 * Gets a bot from the bot's ID.
	 *
	 * @param id Bot's ID.
	 * @return Bot
	 */
	public Bot getBot(int id)
	{
		for (final Bot bot : this.bots)
			if (bot.getId() == id)
				return bot;
		return null;
	}

	/**
	 * Gets if the Manager has a Bot.
	 *
	 * @param id Bot's ID.
	 * @return boolean
	 */
	public boolean containsBot(int id)
	{
		for (final Bot bot : this.bots)
			if (bot.getId() == id)
				return true;
		return false;
	}

	/**
	 * Initializes this Manager.
	 */
	@Override
	public void initialize()
	{

	}

	/**
	 * Disposes this Manager.
	 */
	@Override
	public void dispose()
	{
		this.bots.clear();
	}

}
