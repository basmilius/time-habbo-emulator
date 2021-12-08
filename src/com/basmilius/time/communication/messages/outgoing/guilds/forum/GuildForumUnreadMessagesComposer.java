package com.basmilius.time.communication.messages.outgoing.guilds.forum;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GuildForumUnreadMessagesComposer extends MessageComposer
{

	private final int messages;

	public GuildForumUnreadMessagesComposer(final int messages)
	{
		this.messages = messages;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.GuildForumUnreadMessages);
		response.appendInt(this.messages);
		return response;
	}

}
