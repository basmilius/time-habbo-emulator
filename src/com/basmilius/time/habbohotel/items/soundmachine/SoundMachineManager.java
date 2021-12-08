package com.basmilius.time.habbohotel.items.soundmachine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.core.StringCrypt;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.SoundMachineUserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.util.json.JSONArray;
import com.basmilius.time.util.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SoundMachineManager extends IManager
{

	private List<Song> _songs = new ArrayList<>();

	@Override
	public void initialize()
	{
		this.loadSongs();
	}

	public void loadSongs()
	{
		try
		{
			Bootstrap.getEngine().getLogging().logNoNewLine(SoundMachineManager.class, "Loading soundtracks .. ");

			this._songs.clear();

			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM soundtracks");
			{
				if (statement != null)
				{
					final ResultSet result = statement.executeQuery();

					while (result.next())
					{
						this._songs.add(new Song(result));
					}

					Bootstrap.getEngine().getLogging().logOK();
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			Bootstrap.getEngine().onLaunchFail();
		}
	}

	public Song getSongById(int id)
	{
		for (Song song : this._songs)
			if (song.getId() == id)
				return song;

		return null;
	}

	public Song getSongBySha1Id(String sha1)
	{
		try
		{
			for (Song song : this._songs)
				if (StringCrypt.getSHA1Hash(Integer.toString(song.getId())).equalsIgnoreCase(sha1))
					return song;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public final int getCurrentCdForJukeBox(final UserItem jukebox)
	{
		final JSONObject data = jukebox.getServerData();
		return ((data.has("soundmachine") && data.getJSONObject("soundmachine").has("current_cd")) ? data.getJSONObject("soundmachine").getInt("current_cd") : 0);
	}

	public final boolean getIsJukeBoxPlaying(final UserItem jukebox)
	{
		final JSONObject data = jukebox.getServerData();
		return (data.has("soundmachine") && data.getJSONObject("soundmachine").has("is_playing") && data.getJSONObject("soundmachine").getBoolean("is_playing"));
	}

	public final void saveJukeBoxData(final UserItem jukebox, final boolean isPlaying, final int currentCd)
	{
		if (!jukebox.getServerData().has("soundmachine"))
		{
			jukebox.getServerData().put("soundmachine", new JSONObject());
		}
		jukebox.getServerData().getJSONObject("soundmachine").put("is_playing", isPlaying);
		jukebox.getServerData().getJSONObject("soundmachine").put("current_cd", currentCd);
		jukebox.saveServerData();
	}

	public List<UserItem> getCdsForJukeBox(final UserItem jukebox)
	{
		List<UserItem> cds = new ArrayList<>();

		if (jukebox.getServerData().has("soundmachine") && jukebox.getServerData().getJSONObject("soundmachine").has("cds"))
		{
			final JSONArray jCds = jukebox.getServerData().getJSONObject("soundmachine").getJSONArray("cds");
			for (int i = 0; i < jCds.length(); i++)
			{
				final UserItem cd = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(jCds.optInt(i));
				if (cd == null)
					continue;
				cds.add(cd);
			}
		}

		return cds;
	}

	public final void saveJukeBoxCds(final UserItem jukebox, final List<UserItem> cds)
	{
		if (!jukebox.getServerData().has("soundmachine"))
		{
			jukebox.getServerData().put("soundmachine", new JSONObject());
		}
		jukebox.getServerData().getJSONObject("soundmachine").put("cds", new JSONArray());
		for (final UserItem cd : cds)
		{
			jukebox.getServerData().getJSONObject("soundmachine").getJSONArray("cds").put(cd.getId());
		}
		jukebox.saveServerData();
	}

	public UserItem getJukeBoxForRoom(final Room room)
	{
		final List<SoundMachineUserItem> jukeboxes = room.getRoomObjectsHandler().getItems(SoundMachineUserItem.class);

		if (jukeboxes.size() > 0)
			return jukeboxes.get(0);

		return null;
	}

	@Override
	public void dispose()
	{
		this._songs.clear();
	}

}
