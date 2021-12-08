package com.basmilius.time.habbohotel.moderation.issue;

import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModerationIssueUser extends ModerationIssue
{

	private final int habboReportedId;
	private final int roomId;

	public ModerationIssueUser(ResultSet result) throws SQLException
	{
		super(result);

		this.habboReportedId = super.getData().getInt("habbo_reported_id");
		this.roomId = super.getData().getInt("room_id");
	}

	@Override
	public final void serializeChatlog(final ServerMessage response)
	{
	}

	@Override
	public final void serializeIssue(final ServerMessage response, final int open)
	{
	}

	public final int getHabboReportedId()
	{
		return this.habboReportedId;
	}

	public final int getRoomId()
	{
		return this.roomId;
	}
}
