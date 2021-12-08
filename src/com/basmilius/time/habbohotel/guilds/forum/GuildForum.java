package com.basmilius.time.habbohotel.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.collections.GuildForumThreadListComparator;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.TimeUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class GuildForum
{

	private final Guild guild;
	private final List<GuildForumThread> threads;

	public GuildForum(final Guild guild)
	{
		this.guild = guild;
		this.threads = new ArrayList<>();
	}

	public final Guild getGuild()
	{
		return this.guild;
	}

	public final int getScore()
	{
		double score = 0;

		for (final GuildForumThread thread : this.threads)
		{
			score += (thread.getMessages().size() * 0.25) + 1;
		}

		return (int) Math.floor(score);
	}

	public final GuildForumThread getThread(final int id)
	{
		for (final GuildForumThread thread : this.threads)
			if (thread.getId() == id)
				return thread;

		return null;
	}

	public final GuildForumThread getLatestThread()
	{
		return (this.threads.size() > 0) ? this.threads.get(this.threads.size() - 1) : null;
	}

	public final List<GuildForumThread> getThreads(final Habbo habbo)
	{
		List<GuildForumThread> threads = this.threads.stream().filter(thread -> !thread.isHidden() || this.guild.isModerator(habbo)).collect(Collectors.toList());

		Collections.sort(threads, new GuildForumThreadListComparator(true));

		return threads;
	}

	public final void loadThreads()
	{
		synchronized (this.threads)
		{
			this.threads.clear();

			try
			{
				final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT id, guild_id, user_id, user_hidder_id, created_on, subject, is_hidden, is_locked, is_pinned FROM forums_threads WHERE guild_id = ?");
				statement.setInt(1, this.guild.getId());

				final ResultSet result = statement.executeQuery();
				while (result.next())
				{
					this.threads.add(new GuildForumThread(result));
				}
			}
			catch (SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
			}
		}
	}

	public final GuildForumThread postThread(final Habbo habbo, final String subject, final String message)
	{
		synchronized (this.threads)
		{
			try
			{
				final int timestamp = TimeUtils.getUnixTimestamp();
				final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO forums_threads (guild_id, user_id, created_on, subject) VALUES (?, ?, ?, ?)");
				statement.setInt(1, this.getGuild().getId());
				statement.setInt(2, habbo.getId());
				statement.setInt(3, timestamp);
				statement.setString(4, subject);
				statement.execute();

				final ResultSet keys = statement.getGeneratedKeys();
				keys.next();
				final int threadId = keys.getInt(1);

				final GuildForumThread thread = new GuildForumThread(threadId, this.getGuild().getId(), habbo.getId(), timestamp, subject, false, false, false);
				this.threads.add(thread);

				thread.postMessage(habbo, message);

				return thread;
			}
			catch (SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
			}

			return null;
		}
	}

	public final void serializeData(final Habbo habbo, final ServerMessage response)
	{
		response.appendInt(guild.getId());
		response.appendString(guild.getName());
		response.appendString(guild.getDescription());
		response.appendString(guild.getBadgeData());
		response.appendInt(0);
		response.appendInt(this.getScore());
		response.appendInt(this.getThreads(habbo).size());
		response.appendInt(0); // unread messages.
		response.appendInt(0); // idk
		response.appendInt(this.threads.size() > 0 ? this.getLatestThread().getHabbo().getId() : 0); // last message poster ID.
		response.appendString(this.threads.size() > 0 ? this.getLatestThread().getHabbo().getUsername() : ""); // last message poster Name.
		response.appendInt(this.threads.size() > 0 ? (TimeUtils.getUnixTimestamp() - this.getLatestThread().getCreatedOn()) : 0);
	}

}