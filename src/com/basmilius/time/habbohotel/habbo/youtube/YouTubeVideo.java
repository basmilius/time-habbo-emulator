package com.basmilius.time.habbohotel.habbo.youtube;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YouTubeVideo
{

	private int _id;
	private Habbo _habbo;
	private int _currentPosition;
	private int _videoDuration;
	private String _videoId;
	private String _videoTitle;
	private YouTubePlaylist _playlist;

	public YouTubeVideo(final YouTubePlaylist playlist, final ResultSet result) throws SQLException
	{
		this._id = result.getInt("id");
		this._habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this._currentPosition = 0;
		this._videoDuration = result.getInt("video_length");
		this._videoId = result.getString("video_id");
		this._videoTitle = result.getString("title");
		this._playlist = playlist;
	}

	public int getId()
	{
		return this._id;
	}

	public Habbo getHabbo()
	{
		return this._habbo;
	}

	public int getCurrentPosition()
	{
		return this._currentPosition;
	}

	public void setCurrentPosition(int seconds)
	{
		this._currentPosition = seconds;
	}

	public int getVideoDuration()
	{
		return this._videoDuration;
	}

	public String getVideoId()
	{
		return this._videoId;
	}

	public String getVideoTitle()
	{
		return this._videoTitle;
	}

	public YouTubePlaylist getPlaylist()
	{
		return this._playlist;
	}

}
