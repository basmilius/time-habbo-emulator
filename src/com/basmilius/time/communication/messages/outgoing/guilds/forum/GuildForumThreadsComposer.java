package com.basmilius.time.communication.messages.outgoing.guilds.forum;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumThread;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class GuildForumThreadsComposer extends MessageComposer
{

	private final Guild guild;
	private final int startIndex;
	private final List<GuildForumThread> threads;

	public GuildForumThreadsComposer(final Guild guild, final int startIndex, final List<GuildForumThread> threads)
	{
		this.guild = guild;
		this.startIndex = startIndex;
		this.threads = threads;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.GuildForumThreads);
		response.appendInt(this.guild.getId());
		response.appendInt(this.startIndex);
		response.appendInt(this.threads.size());
		for (final GuildForumThread thread : this.threads)
		{
			thread.serializeThread(response);
		}
		return response;
	}
}
