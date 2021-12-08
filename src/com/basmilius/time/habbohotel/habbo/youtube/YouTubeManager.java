package com.basmilius.time.habbohotel.habbo.youtube;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class YouTubeManager
{

	private final Habbo _habbo;
	private final List<YouTubePlaylist> _playlists;
	private UserItem _lastTv;

	public YouTubeManager(Habbo _habbo) throws SQLException
	{
		this._habbo = _habbo;
		this._playlists = new ArrayList<>();
		this._lastTv = null;

		this.loadVideos();
	}

	public void loadVideos() throws SQLException
	{
		this._playlists.clear();

		PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users_youtube WHERE parent_id = 0 AND type = 'playlist' AND user_id = ?");
		{
			statement.setInt(1, this._habbo.getId());
			ResultSet result = statement.executeQuery();

			while (result.next())
			{
				this._playlists.add(new YouTubePlaylist(result));
			}
		}
	}

	public Habbo getHabbo()
	{
		return this._habbo;
	}

	public YouTubePlaylist getPlaylist(int id)
	{
		for (YouTubePlaylist list : this._playlists)
			if (list.getId() == id)
				return list;

		return null;
	}

	public List<YouTubePlaylist> getPlaylists()
	{
		return this._playlists;
	}

	public UserItem getLastTv()
	{
		return this._lastTv;
	}

	public void setLastTv(UserItem item)
	{
		this._lastTv = item;
	}

}
