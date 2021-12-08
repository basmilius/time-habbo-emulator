package com.basmilius.time.habbohotel.habbo.youtube;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YouTubePlaylist implements Runnable
{

	private final Map<Integer, YouTubeCallback> _userItems;
	private int _id;
	private Habbo _habbo;
	private String _title;
	private String _description;
	private List<YouTubeVideo> _videos;
	private int _currentVideo;
	private Thread _thread;

	public YouTubePlaylist(ResultSet result) throws SQLException
	{
		this._id = result.getInt("id");
		this._habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this._title = result.getString("title");
		this._description = result.getString("description");

		this._videos = new ArrayList<>();
		this._currentVideo = -1;

		this._userItems = new HashMap<>();

		this.loadVideos();
	}

	void loadVideos() throws SQLException
	{
		this._videos.clear();

		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users_youtube WHERE parent_id = ? AND user_id = ? AND type = 'video' ORDER BY RAND()");
		{
			statement.setInt(1, this._id);
			statement.setInt(2, this._habbo.getId());

			final ResultSet result = statement.executeQuery();

			while (result.next())
			{
				this._videos.add(new YouTubeVideo(this, result));
			}
		}
	}

	public int getId()
	{
		return this._id;
	}

	public Habbo getHabbo()
	{
		return this._habbo;
	}

	public String getTitle()
	{
		return this._title;
	}

	public String getDescription()
	{
		return this._description;
	}

	public List<YouTubeVideo> getVideos()
	{
		return this._videos;
	}

	public YouTubeVideo getCurrentVideo()
	{
		if (this._currentVideo > -1)
		{
			return this._videos.get(this._currentVideo);
		}
		else if (this._videos.size() > 0)
		{
			return this._videos.get(0);
		}
		else
		{
			return null;
		}
	}

	YouTubeVideo getNextVideo()
	{
		this._currentVideo = (this._currentVideo + 1);

		if (this._currentVideo >= this._videos.size())
		{
			this._currentVideo = 0;
		}

		return this.getCurrentVideo();
	}

	YouTubeVideo getPreviousVideo()
	{
		this._currentVideo = (this._currentVideo - 1);

		if (this._currentVideo < 0)
		{
			this._currentVideo = (this._videos.size() - 1);
		}

		return this.getCurrentVideo();
	}

	public void start(UserItem item, YouTubeCallback runnable)
	{
		this._userItems.put(item.getId(), runnable);

		if (this._thread != null)
			return;

		this._thread = new Thread(this);
		this._thread.start();
	}

	@SuppressWarnings("deprecation")
	public void stop(UserItem item)
	{
		if (this._userItems.containsKey(item.getId()))
		{
			this._userItems.remove(item.getId());
		}

		if (this._userItems.size() == 0 && this._thread != null)
		{
			this._thread.stop();
			this._thread = null;
		}
	}

	@SuppressWarnings("deprecation")
	void resetThread()
	{
		this._thread.stop();
		this._thread = null;

		this._thread = new Thread(this);
		this._thread.start();
	}

	public void nextVideo()
	{
		this.getCurrentVideo().setCurrentPosition(0);
		this.resetThread();
	}

	public void previousVideo()
	{
		this.getCurrentVideo().setCurrentPosition(0);
		this.getPreviousVideo();
		this.getPreviousVideo();
		this.resetThread();
	}

	@Override
	public void run()
	{
		while (true)
		{
			if (this._userItems.size() == 0)
				break;

			YouTubeVideo video = this.getNextVideo();
			video.setCurrentPosition(0);

			synchronized (this._userItems)
			{
				for (YouTubeCallback runnable : this._userItems.values())
				{
					runnable.run(video);
				}
			}

			int seconds = 0;

			while (seconds < video.getVideoDuration())
			{
				video.setCurrentPosition(seconds);
				seconds++;

				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException ignored)
				{

				}
			}

			video.setCurrentPosition(0);
		}
	}

}
