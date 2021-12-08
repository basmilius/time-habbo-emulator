package com.basmilius.time.habbohotel.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.moderation.chatlog.ChatlogManager;
import com.basmilius.time.habbohotel.moderation.collections.ModerationIssueList;
import com.basmilius.time.habbohotel.moderation.collections.ModerationPresetIssueList;
import com.basmilius.time.habbohotel.moderation.collections.ModerationPresetRoomList;
import com.basmilius.time.habbohotel.moderation.collections.ModerationPresetUserList;
import com.basmilius.time.habbohotel.moderation.issue.ModerationIssue;
import com.basmilius.time.habbohotel.moderation.issue.ModerationIssueGuildMessage;
import com.basmilius.time.habbohotel.moderation.issue.ModerationIssueGuildThread;
import com.basmilius.time.habbohotel.moderation.issue.ModerationIssueUser;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationRemoveTicketComposer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class ModerationManager extends IManager
{

	private final ChatlogManager chatlogs;
	private final ModerationIssueList issues;
	private final ModerationPresetIssueList presetsIssue;
	private final ModerationPresetRoomList presetsRoom;
	private final ModerationPresetUserList presetsUser;

	public ModerationManager()
	{
		this.chatlogs = new ChatlogManager();
		this.issues = new ModerationIssueList();
		this.presetsIssue = new ModerationPresetIssueList();
		this.presetsRoom = new ModerationPresetRoomList();
		this.presetsUser = new ModerationPresetUserList();
	}

	public final ChatlogManager getChatlogs()
	{
		return this.chatlogs;
	}

	public final ModerationIssue getIssue(final int id)
	{
		synchronized (this.issues)
		{
			for (final ModerationIssue issue : this.issues)
				if (issue.getId() == id)
					return issue;
			return null;
		}
	}

	public final ModerationIssueList getIssues()
	{
		synchronized (this.issues)
		{
			return this.issues;
		}
	}

	public final ModerationIssueList getIssues(final Habbo habbo)
	{
		synchronized (this.issues)
		{
			return this.issues.stream().filter(issue -> issue.getHabboReporterId() == habbo.getId()).collect(Collectors.toCollection(ModerationIssueList::new));
		}
	}

	public final ModerationPresetIssue getPresetByCategory(final String category)
	{
		synchronized (this.presetsIssue)
		{
			for (final ModerationPresetIssue presetIssue : this.presetsIssue)
				if (presetIssue.getCategory().equalsIgnoreCase(category))
					return presetIssue;

			return null;
		}
	}

	public final ModerationPresetIssueList getPresetsIssue()
	{
		return this.presetsIssue;
	}

	public final ModerationPresetRoomList getPresetsRoom()
	{
		return this.presetsRoom;
	}

	public final ModerationPresetUserList getPresetsUser()
	{
		return this.presetsUser;
	}

	public final ModerationToolRoomInfo getRoomInfo(final int roomId)
	{
		final Room room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(roomId);

		if (room == null)
			return null;

		return new ModerationToolRoomInfo(room);
	}

	public final ModerationToolUserInfo getUserInfo(final int habboId)
	{
		final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(habboId);

		if (habbo == null)
			return null;

		return new ModerationToolUserInfo(habbo);
	}

	public final void createGuildMessageIssue(final Habbo reporterHabbo, final int category, final int guildId, final int threadId, final int messageId, final String message)
	{
		ModerationIssueGuildMessage.make(reporterHabbo, category, guildId, threadId, messageId, message);
	}

	public final void createGuildThreadIssue(final Habbo reporterHabbo, final int category, final int guildId, final int threadId, final String message)
	{
		ModerationIssueGuildThread.make(reporterHabbo, category, guildId, threadId, message);
	}

	public final void deleteIssue(final ModerationIssue issue)
	{
		final int issueId = issue.getId();
		this.issues.remove(issue); // TODO: Set issue status to deleted.

		this.send(new ModerationRemoveTicketComposer(issueId));
	}

	public final void send(final MessageComposer composer)
	{
		Bootstrap.getEngine().getServer().getClientManager().sendBroadcastResponse(composer, "acc_supporttool");
	}

	@Override
	public final void initialize()
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT id, type, category, state, user_reporter_id, user_staff_id, created_on, message, data FROM support_issues WHERE state != 4 ORDER BY id ASC");
			{
				if (statement != null)
				{
					final ResultSet result = statement.executeQuery();

					while (result.next())
					{
						switch (result.getInt("type"))
						{
							case ModerationIssue.GuildMessageIssue:
								this.issues.add(new ModerationIssueGuildMessage(result));
								break;
							case ModerationIssue.GuildThreadIssue:
								this.issues.add(new ModerationIssueGuildThread(result));
								break;
							case ModerationIssue.UserIssue:
								this.issues.add(new ModerationIssueUser(result));
								break;
						}
					}

					result.close();
				}
			}

			final PreparedStatement issuePresetsStatement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM support_issue_presets");
			{
				if (issuePresetsStatement != null)
				{
					final ResultSet result = issuePresetsStatement.executeQuery();

					while (result.next())
					{
						ModerationPresetIssue presetIssue = this.getPresetByCategory(result.getString("category"));

						if (presetIssue == null)
						{
							this.presetsIssue.add(presetIssue = new ModerationPresetIssue(result));
						}

						presetIssue.getEntries().add(new ModerationPresetIssueEntry(result));
					}

					result.close();
				}
			}

			final PreparedStatement presetsStatement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM support_presets");
			{
				if (presetsStatement != null)
				{
					final ResultSet result = presetsStatement.executeQuery();

					while (result.next())
					{
						if (result.getString("type").equals("room"))
						{
							this.presetsRoom.add(new ModerationPresetRoom(result));
						}
						else if (result.getString("type").equals("user"))
						{
							this.presetsUser.add(new ModerationPresetUser(result));
						}
					}

					result.close();
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	@Override
	public final void dispose()
	{
		this.issues.clear();
		this.presetsIssue.clear();
		this.presetsRoom.clear();
		this.presetsUser.clear();
	}

}
