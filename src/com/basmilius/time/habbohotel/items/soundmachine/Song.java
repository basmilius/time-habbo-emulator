package com.basmilius.time.habbohotel.items.soundmachine;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Song
{

	private int id;
	private String name;
	private String author;
	private String track;
	private int length;

	public Song(ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.name = result.getString("name");
		this.author = result.getString("author");
		this.track = result.getString("track");
		this.length = result.getInt("length");
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public String getAuthor()
	{
		return this.author;
	}

	public String getTrack()
	{
		return this.track;
	}

	public int getLength()
	{
		return this.length;
	}
}
