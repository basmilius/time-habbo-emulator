package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabboRoomFavorites
{

	private Habbo habbo;
	private List<Integer> favorites;

	public HabboRoomFavorites(Habbo habbo) throws SQLException
	{
		this.habbo = habbo;
		this.favorites = new ArrayList<>();

		PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT room_id FROM navigator_favorites WHERE user_id = ?");
		{
			statement.setInt(1, this.habbo.getId());
			ResultSet result = statement.executeQuery();

			while (result.next())
			{
				this.favorites.add(result.getInt("room_id"));
			}
		}
	}

	public void addRoom(Integer id)
	{
		try
		{
			this.favorites.add(id);

			PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO navigator_favorites (user_id, room_id) VALUES (?, ?)");
			{
				statement.setInt(1, this.habbo.getId());
				statement.setInt(2, id);
				statement.execute();
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public void removeRoom(Integer id)
	{
		try
		{
			this.favorites.remove(id);

			PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("DELETE FROM navigator_favorites WHERE user_id = ? AND room_id = ?");
			{
				statement.setInt(1, this.habbo.getId());
				statement.setInt(2, id);
				statement.execute();
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public Integer[] getFavorites()
	{
		Integer[] roomIds = new Integer[this.favorites.size()];
		return this.favorites.toArray(roomIds);
	}

	public boolean isFavorited(int roomId)
	{
		return this.favorites.contains(roomId);
	}

}
