package com.basmilius.time.habbohotel.moderation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModerationPresetUser
{

	private final int id;
	private final String type;
	private final String preset;

	public ModerationPresetUser(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.type = result.getString("type");
		this.preset = result.getString("preset");
	}

	public final int getId()
	{
		return this.id;
	}

	public final String getType()
	{
		return this.type;
	}

	public final String getPreset()
	{
		return this.preset;
	}
}
