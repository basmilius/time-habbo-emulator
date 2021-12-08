package com.basmilius.time.habbohotel.guilds;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuildColor
{

	private int _id;
	private String _color;

	public GuildColor(ResultSet result) throws SQLException
	{
		this._id = result.getInt("id");
		this._color = result.getString("firstvalue");
	}

	public int getId()
	{
		return this._id;
	}

	public String getColor()
	{
		return this._color;
	}

}
