package com.basmilius.time.communication.messages.incoming.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumThread;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.forum.GuildForumThreadsComposer;

import java.util.List;

@Event(id = Incoming.GuildForumGetThreads)
public class GuildForumGetThreadsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final int startIndex = packet.readInt();
		int stopIndex = packet.readInt();

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (guild == null)
			return;

		if (stopIndex <= startIndex)
		{
			stopIndex = (startIndex + 20);
		}

		if (stopIndex >= guild.getGuildForum().getThreads(connection.getHabbo()).size())
		{
			stopIndex = guild.getGuildForum().getThreads(connection.getHabbo()).size();
		}

		final List<GuildForumThread> threads = guild.getGuildForum().getThreads(connection.getHabbo()).subList(startIndex, stopIndex);

		connection.send(new GuildForumThreadsComposer(guild, startIndex, threads));
	}

}
