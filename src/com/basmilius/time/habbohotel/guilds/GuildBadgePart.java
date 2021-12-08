package com.basmilius.time.habbohotel.guilds;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuildBadgePart
{

	private int _id;
	private String _partName1;
	private String _partName2;

	public GuildBadgePart(ResultSet result) throws SQLException
	{
		this._id = result.getInt("id");
		this._partName1 = result.getString("firstvalue");
		this._partName2 = result.getString("secondvalue");
	}

	public int getId()
	{
		return this._id;
	}

	public String getPartName1()
	{
		return this._partName1;
	}

	public String getPartName2()
	{
		return this._partName2;
	}

}
