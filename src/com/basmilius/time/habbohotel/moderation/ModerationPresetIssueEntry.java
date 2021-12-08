package com.basmilius.time.habbohotel.moderation;

import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModerationPresetIssueEntry implements ISerialize
{

	private final int id;
	private final String category;
	private final String name;
	private final String message;
	private final String reminder;
	private final int banFor;
	private final int muteFor;

	public ModerationPresetIssueEntry(ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.category = result.getString("category");
		this.name = result.getString("name");
		this.message = result.getString("message");
		this.reminder = result.getString("reminder");
		this.banFor = result.getInt("ban_for");
		this.muteFor = result.getInt("mute_for");
	}

	public final int getId()
	{
		return this.id;
	}

	public final String getCategory()
	{
		return this.category;
	}

	public final String getName()
	{
		return this.name;
	}

	public final String getMessage()
	{
		return this.message;
	}

	public final String getReminder()
	{
		return this.reminder;
	}

	public final int getBanFor()
	{
		return this.banFor;
	}

	public final int getMuteFor()
	{
		return this.muteFor;
	}

	@Override
	public final void serialize(ServerMessage response)
	{
		response.appendString(this.getName());
		response.appendString(this.getMessage());
		response.appendInt(this.getBanFor());
		response.appendInt(1);
		response.appendInt(this.getMuteFor());
		response.appendInt(1);
		response.appendString(this.getReminder());
	}

}
