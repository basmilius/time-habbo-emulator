package com.basmilius.time.habbohotel.moderation;

import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModerationPresetIssue implements ISerialize
{

	private final String category;
	private final List<ModerationPresetIssueEntry> entries;

	public ModerationPresetIssue(ResultSet result) throws SQLException
	{
		this.category = result.getString("category");
		this.entries = new ArrayList<>();
	}

	public final String getCategory()
	{
		return this.category;
	}

	public final List<ModerationPresetIssueEntry> getEntries()
	{
		return this.entries;
	}

	@Override
	public final void serialize(ServerMessage response)
	{
		response.appendString(this.getCategory());
		response.appendBoolean(true);
		response.appendInt(this.entries.size());
		for (final ModerationPresetIssueEntry entry : this.entries)
		{
			entry.serialize(response);
		}
	}

}
