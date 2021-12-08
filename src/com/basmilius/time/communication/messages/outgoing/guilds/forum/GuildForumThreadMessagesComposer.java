package com.basmilius.time.communication.messages.outgoing.guilds.forum;

import com.basmilius.time.habbohotel.guilds.forum.GuildForumMessage;
import com.basmilius.time.habbohotel.guilds.forum.GuildForumThread;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class GuildForumThreadMessagesComposer extends MessageComposer
{

	private final GuildForumThread thread;
	private final int startIndex;
	private final List<GuildForumMessage> messages;

	public GuildForumThreadMessagesComposer(final GuildForumThread thread, final int startIndex, final List<GuildForumMessage> messages)
	{
		this.thread = thread;
		this.startIndex = startIndex;
		this.messages = messages;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.GuildForumThreadMessages);
		response.appendInt(this.thread.getGuild().getId());
		response.appendInt(this.thread.getId());
		response.appendInt(this.startIndex);
		response.appendInt(this.messages.size());
		for (final GuildForumMessage message : this.messages)
		{
			message.serializeMessage(response);
		}
		return response;
	}

}
