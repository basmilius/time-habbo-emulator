package com.basmilius.time.habbohotel.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.TimeUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class GuildForumMessage
{

	private int id;
	private int guildId;
	private int threadId;
	private int habboId;
	private int habboHidderId;
	private int createdOn;
	private String message;
	private boolean isHidden;

	public GuildForumMessage(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.guildId = result.getInt("guild_id");
		this.threadId = result.getInt("thread_id");
		this.habboId = result.getInt("user_id");
		this.habboHidderId = result.getInt("user_hidder_id");
		this.createdOn = result.getInt("created_on");
		this.message = result.getString("message");
		this.isHidden = result.getString("is_hidden").equalsIgnoreCase("1");
	}

	public GuildForumMessage(final int id, final int guildId, final int threadId, final int habboId, final int createdOn, final String message)
	{
		this.id = id;
		this.guildId = guildId;
		this.threadId = threadId;
		this.habboId = habboId;
		this.habboHidderId = 0;
		this.createdOn = createdOn;
		this.message = message;
		this.isHidden = false;
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

	public final GuildForumThread getThread()
	{
		return this.getGuild().getGuildForum().getThread(this.threadId);
	}

	public final int getCreatedOn()
	{
		return this.createdOn;
	}

	public final String getMessage()
	{
		return this.message;
	}

	public boolean isHidden()
	{
		return this.isHidden;
	}

	public final void setHidden(final boolean hidden)
	{
		this.isHidden = hidden;
	}

	public final void save()
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE forums_messages SET user_hidder_id = ?, message = ?, is_hidden = ? WHERE id = ?");
			statement.setInt(1, this.habboHidderId);
			statement.setString(2, this.message);
			statement.setString(3, this.isHidden ? "1" : "0");
			statement.setInt(4, this.id);
			statement.execute();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public final void serializeMessage(final ServerMessage response)
	{
		response.appendInt(this.id);
		response.appendInt(this.getThread().getMessageIndex(this));
		response.appendInt(this.habboId);
		response.appendString(this.getHabbo().getUsername());
		response.appendString(this.getHabbo().getLook());
		response.appendInt(TimeUtils.getUnixTimestamp() - this.createdOn);
		response.appendString(this.message);
		response.appendByte(this.isHidden ? 10 : 1);
		response.appendInt(this.isHidden ? this.getHabboHidder().getId() : 0);
		response.appendString(this.isHidden ? this.getHabboHidder().getUsername() : "");
		response.appendInt(42);
	}

}
