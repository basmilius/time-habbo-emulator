package com.basmilius.time.habbohotel.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.TimeUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class GuildForumThread
{

	private final List<GuildForumMessage> messages;
	private int id;
	private int guildId;
	private int habboId;
	private int habboHidderId;
	private int createdOn;
	private String subject;
	private boolean isHidden;
	private boolean isLocked;
	private boolean isPinned;

	public GuildForumThread(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.guildId = result.getInt("guild_id");
		this.habboId = result.getInt("user_id");
		this.habboHidderId = result.getInt("user_hidder_id");
		this.createdOn = result.getInt("created_on");
		this.subject = result.getString("subject");
		this.isHidden = result.getString("is_hidden").equalsIgnoreCase("1");
		this.isLocked = result.getString("is_locked").equalsIgnoreCase("1");
		this.isPinned = result.getString("is_pinned").equalsIgnoreCase("1");

		this.messages = new ArrayList<>();

		this.loadMessages();
	}

	public GuildForumThread(final int id, final int guildId, final int habboId, final int createdOn, final String subject, final boolean isHidden, final boolean isLocked, final boolean isPinned)
	{
		this.id = id;
		this.guildId = guildId;
		this.habboId = habboId;
		this.habboHidderId = 0;
		this.createdOn = createdOn;
		this.subject = subject;
		this.isHidden = isHidden;
		this.isLocked = isLocked;
		this.isPinned = isPinned;

		this.messages = new ArrayList<>();

		this.loadMessages();
	}

	public final int getId()
	{
		return this.id;
	}

	public final Guild getGuild()
	{
		return Bootstrap.getEngine().getGame().getGuildManager().getGuild(this.guildId);
	}

	public final Habbo getHabbo()
	{
		return Bootstrap.getEngine().getGame().getHabboManager().getHabbo(this.habboId);
	}

	public final Habbo getHabboHidder()
	{
		return Bootstrap.getEngine().getGame().getHabboManager().getHabbo(this.habboHidderId);
	}

	public final void setHabboHidder(final Habbo habbo)
	{
		this.habboHidderId = habbo.getId();
	}

	public final int getCreatedOn()
	{
		return this.createdOn;
	}

	public final String getSubject()
	{
		return this.subject;
	}

	public final boolean isHidden()
	{
		return this.isHidden;
	}

	public final void setHidden(final boolean hidden)
	{
		this.isHidden = hidden;
	}

	public final boolean isLocked()
	{
		return this.isLocked;
	}

	public final void setLocked(final boolean locked)
	{
		this.isLocked = locked;
	}

	public final boolean isPinned()
	{
		return this.isPinned;
	}

	public final void setPinned(final boolean pinned)
	{
		this.isPinned = pinned;
	}

	public final GuildForumMessage getMessage(final int id)
	{
		for (final GuildForumMessage message : this.messages)
			if (message.getId() == id)
				return message;

		return null;
	}

	public final int getMessageIndex(final GuildForumMessage message)
	{
		return this.getMessages().indexOf(message);
	}

	public final GuildForumMessage getMessageLatest()
	{
		return (this.messages.size() > 0) ? this.messages.get(this.messages.size() - 1) : null;
	}

	public final List<GuildForumMessage> getMessages()
	{
		return this.messages;
	}

	public final void loadMessages()
	{
		synchronized (this.messages)
		{
			this.messages.clear();

			try
			{
				final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT id, guild_id, thread_id, user_id, user_hidder_id, created_on, message, is_hidden FROM forums_messages WHERE thread_id = ? ORDER BY id ASC");
				statement.setInt(1, this.id);

				final ResultSet result = statement.executeQuery();
				while (result.next())
				{
					this.messages.add(new GuildForumMessage(result));
				}
			}
			catch (SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
			}
		}
	}

	public final GuildForumMessage postMessage(final Habbo habbo, final String message)
	{
		synchronized (this.messages)
		{
			try
			{
				final int timestamp = TimeUtils.getUnixTimestamp();
				final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO forums_messages (guild_id, thread_id, user_id, created_on, message) VALUES(?, ?, ?, ?, ?)");
				statement.setInt(1, this.guildId);
				statement.setInt(2, this.id);
				statement.setInt(3, habbo.getId());
				statement.setInt(4, timestamp);
				statement.setString(5, message);
				statement.execute();

				final ResultSet keys = statement.getGeneratedKeys();
				keys.next();
				final int messageId = keys.getInt(1);

				final GuildForumMessage post = new GuildForumMessage(messageId, this.guildId, this.id, habbo.getId(), timestamp, message);
				this.messages.add(post);

				return post;
			}
			catch (SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
			}

			return null;
		}
	}

	public final void save()
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE forums_threads SET user_hidder_id = ?, subject = ?, is_hidden = ?, is_locked = ?, is_pinned = ? WHERE id = ?");
			statement.setInt(1, this.habboHidderId);
			statement.setString(2, this.subject);
			statement.setString(3, this.isHidden ? "1" : "0");
			statement.setString(4, this.isLocked ? "1" : "0");
			statement.setString(5, this.isPinned ? "1" : "0");
			statement.setInt(6, this.id);
			statement.execute();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public final void serializeThread(final ServerMessage response)
	{
		final GuildForumMessage lastMessage = this.getMessages().get(this.getMessages().size() - 1);

		response.appendInt(this.id);
		response.appendInt(this.habboId);
		response.appendString(this.getHabbo().getUsername());
		response.appendString(this.subject);
		response.appendBoolean(this.isPinned);
		response.appendBoolean(this.isLocked);
		response.appendInt(TimeUtils.getUnixTimestamp() - this.createdOn);
		response.appendInt(this.getMessages().size());
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(lastMessage.getHabbo().getId());
		response.appendString(lastMessage.getHabbo().getUsername());
		response.appendInt(TimeUtils.getUnixTimestamp() - lastMessage.getCreatedOn());
		response.appendByte(this.isHidden ? 10 : 1);
		response.appendInt(this.isHidden ? this.getHabboHidder().getId() : 0);
		response.appendString(this.isHidden ? this.getHabboHidder().getUsername() : "");
		response.appendInt(42);
	}

}
