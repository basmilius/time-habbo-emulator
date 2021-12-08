package com.basmilius.time.communication.messages.incoming.guilds.forum;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumThread;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.forum.GuildForumThreadMessagesComposer;

@Event(id = Incoming.GuildForumOpenThread)
public class GuildForumOpenThreadEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int guildId = packet.readInt();
		final int threadId = packet.readInt();
		final int startIndex = packet.readInt();
		int stopIndex = packet.readInt();

		final Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (guild == null)
			return;

		final GuildForumThread thread = guild.getGuildForum().getThread(threadId);

		if (thread == null)
			return;

		if (stopIndex <= startIndex)
		{
			stopIndex = (startIndex + 20);
		}

		if (stopIndex >= thread.getMessages().size())
		{
			stopIndex = thread.getMessages().size();
		}

		connection.send(new GuildForumThreadMessagesComposer(thread, startIndex, thread.getMessages().subList(startIndex, stopIndex)));
	}

}
