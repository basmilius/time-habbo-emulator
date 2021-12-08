package com.basmilius.time.communication.messages.outgoing.guilds.forum;

import com.basmilius.time.habbohotel.guilds.forum.GuildForumThread;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GuildForumThreadUpdateComposer extends MessageComposer
{

	private final GuildForumThread thread;

	public GuildForumThreadUpdateComposer(final GuildForumThread thread)
	{
		this.thread = thread;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.GuildForumThreadUpdate);
		response.appendInt(this.thread.getGuild().getId());
		this.thread.serializeThread(response);
		return response;
	}

}
