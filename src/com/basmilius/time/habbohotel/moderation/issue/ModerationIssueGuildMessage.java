package com.basmilius.time.habbohotel.moderation.issue;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumMessage;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.moderation.ModerationChatlogType;
import com.basmilius.time.habbohotel.moderation.ModerationIssueChatEntry;
import com.basmilius.time.habbohotel.moderation.ModerationIssueType;
import com.basmilius.time.habbohotel.wordfilter.WordEntry;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationHelpRequestSendComposer;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationNewTicketComposer;
import com.basmilius.time.communication.messages.outgoing.handshake.GenericErrorMessageComposer;
import com.basmilius.time.util.TimeUtils;
import com.basmilius.time.util.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModerationIssueGuildMessage extends ModerationIssue
{

	private final int guildId;
	private final int threadId;
	private final int messageId;

	public ModerationIssueGuildMessage(final ResultSet result) throws SQLException
	{
		super(result);

		this.guildId = super.getData().getInt("guild_id");
		this.threadId = super.getData().getInt("thread_id");
		this.messageId = super.getData().getInt("message_id");
	}

	public ModerationIssueGuildMessage(final int id, final int category, final int state, final int type, final int habboReporterId, final int habboStaffId, final int createdOn, final String message, final List<ModerationIssueChatEntry> chatEntries, final int guildId, final int threadId, final int messageId)
	{
		super(id, category, state, type, habboReporterId, habboStaffId, createdOn, message, chatEntries);

		this.guildId = guildId;
		this.threadId = threadId;
		this.messageId = messageId;
	}

	public static void make(final Habbo reporterHabbo, final int category, final int guildId, final int threadId, final int messageId, final String message)
	{
		try
		{
			final JSONObject data = new JSONObject();
			final int timestamp = TimeUtils.getUnixTimestamp();

			data.put("guild_id", guildId);
			data.put("thread_id", threadId);
			data.put("message_id", messageId);

			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO support_issues (type, category, state, user_reporter_id, user_staff_id, created_on, message, data) VALUES (?, ?, 1, ?, 0, ?, ?, ?)");
			statement.setInt(1, ModerationIssue.GuildMessageIssue);
			statement.setInt(2, category);
			statement.setInt(3, reporterHabbo.getId());
			statement.setInt(4, timestamp);
			statement.setString(5, message);
			statement.setString(6, data.toString());
			statement.execute();

			final ResultSet keys = statement.getGeneratedKeys();
			keys.first();
			final int id = keys.getInt(1);

			final ModerationIssueGuildMessage issue = new ModerationIssueGuildMessage(id, category, 1, ModerationIssue.GuildMessageIssue, reporterHabbo.getId(), 0, timestamp, message, new ArrayList<>(), guildId, threadId, messageId);
			Bootstrap.getEngine().getGame().getModerationManager().getIssues().add(issue);

			Bootstrap.getEngine().getGame().getModerationManager().send(new ModerationNewTicketComposer(0, issue));
			reporterHabbo.getConnection().send(new ModerationHelpRequestSendComposer(ModerationHelpRequestSendComposer.SENDED));
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			reporterHabbo.getConnection().send(new GenericErrorMessageComposer(13056, 0));
		}
	}

	@Override
	public final void serializeChatlog(final ServerMessage response)
	{
		final GuildForumMessage message = Bootstrap.getEngine().getGame().getGuildManager().getGuild(this.guildId).getGuildForum().getThread(this.threadId).getMessage(this.messageId);
		final Habbo messageHabbo = message.getHabbo();

		response.appendInt(this.getId());
		response.appendInt(this.getHabboReporterId());
		response.appendInt(messageHabbo.getId());
		response.appendInt(0);
		response.appendByte(ModerationChatlogType.ForumMessageLog);
		response.appendShort(3);
		response.appendString("groupId");
		response.appendByte(1);
		response.appendInt(this.guildId);
		response.appendString("threadId");
		response.appendByte(1);
		response.appendInt(this.threadId);
		response.appendString("messageId");
		response.appendByte(1);
		response.appendInt(this.messageId);
		response.appendShort(1);
		response.appendInt(message.getCreatedOn());
		response.appendInt(message.getHabbo().getId());
		response.appendString(message.getHabbo().getUsername());
		response.appendString(message.getMessage());
		response.appendBoolean(true);
	}

	@Override
	public final void serializeIssue(final ServerMessage response, final int open)
	{
		Bootstrap.getEngine().getGame().getGuildManager().getGuild(this.guildId).getGuildForum().loadThreads();
		final Habbo messageHabbo = Bootstrap.getEngine().getGame().getGuildManager().getGuild(this.guildId).getGuildForum().getThread(this.threadId).getMessage(this.messageId).getHabbo();
		final List<WordEntry> wordFilterEntries = Bootstrap.getEngine().getGame().getWordFilterManager().getWordEntriesForString(this.getMessage());

		response.appendInt(this.getId());
		response.appendInt(this.getState());
		response.appendInt(ModerationIssueType.Discussion);
		response.appendInt(this.getCategory());
		response.appendInt((TimeUtils.getUnixTimestamp() - this.getCreatedOn()) * 1000);
		response.appendInt(1);
		response.appendInt(open);
		response.appendInt(this.getHabboReporterId());
		response.appendString(Bootstrap.getEngine().getGame().getHabboManager().getHabbo(this.getHabboReporterId()).getUsername());
		response.appendInt(messageHabbo.getId());
		response.appendString(messageHabbo.getUsername());
		response.appendInt(this.getHabboStaffId());
		response.appendString(this.getHabboStaffId() > 0 ? Bootstrap.getEngine().getGame().getHabboManager().getHabbo(this.getHabboStaffId()).getUsername() : "");
		response.appendString(this.getMessage());
		response.appendInt(0);

		response.appendInt(wordFilterEntries.size());
		for (final WordEntry entry : wordFilterEntries)
		{
			response.appendString(entry.getWord().getWord());
			response.appendInt(entry.getStartIndex());
			response.appendInt(entry.getEndIndex());
		}
	}

	public final int getGuildId()
	{
		return this.guildId;
	}

	public final int getThreadId()
	{
		return this.threadId;
	}

	public final int getMessageId()
	{
		return this.messageId;
	}

}
