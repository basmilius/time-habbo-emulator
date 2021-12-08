package com.basmilius.time.habbohotel.moderation.issue;

import com.basmilius.time.habbohotel.moderation.ModerationIssueChatEntry;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ModerationIssue
{

	public static final int GuildMessageIssue = 1;
	public static final int GuildThreadIssue = 2;
	public static final int UserIssue = 3;

	private final int id;
	private final int type;
	private final int habboReporterId;
	private final int category;
	private final int createdOn;
	private final String message;
	private final JSONObject data;
	private final List<ModerationIssueChatEntry> chatEntries;
	protected int state;
	protected int habboStaffId;

	protected ModerationIssue(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.state = result.getInt("state");
		this.type = result.getInt("type");
		this.habboReporterId = result.getInt("user_reporter_id");
		this.habboStaffId = result.getInt("user_staff_id");
		this.category = result.getInt("category");
		this.createdOn = result.getInt("created_on");
		this.message = result.getString("message");
		this.data = new JSONObject(result.getString("data"));

		this.chatEntries = new ArrayList<>();
	}

	protected ModerationIssue(final int id, final int category, final int state, final int type, final int habboReporterId, final int habboStaffId, final int createdOn, final String message, final List<ModerationIssueChatEntry> chatEntries)
	{
		this.id = id;
		this.category = category;
		this.state = state;
		this.type = type;
		this.habboReporterId = habboReporterId;
		this.habboStaffId = habboStaffId;
		this.createdOn = createdOn;
		this.message = message;
		this.data = new JSONObject();

		this.chatEntries = chatEntries;
	}

	public final int getId()
	{
		return this.id;
	}

	public final int getState()
	{
		return this.state;
	}

	public final int getType()
	{
		return this.type;
	}

	public final int getHabboReporterId()
	{
		return this.habboReporterId;
	}

	public final int getHabboStaffId()
	{
		return this.habboStaffId;
	}

	public final int getCategory()
	{
		return this.category;
	}

	public final int getCreatedOn()
	{
		return this.createdOn;
	}

	public final String getMessage()
	{
		return this.message;
	}

	protected final JSONObject getData()
	{
		return this.data;
	}

	public final List<ModerationIssueChatEntry> getChatEntries()
	{
		return this.chatEntries;
	}

	public abstract void serializeChatlog(final ServerMessage response);

	public abstract void serializeIssue(final ServerMessage response, final int open);

}
