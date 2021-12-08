package com.basmilius.time.communication.messages.outgoing.guilds.forum;

import com.basmilius.time.habbohotel.guilds.forum.GuildForumMessage;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GuildForumNewMessageComposer extends MessageComposer
{

	private final GuildForumMessage message;

	public GuildForumNewMessageComposer(final GuildForumMessage message)
	{
		this.message = message;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.GuildForumNewMessage);
		response.appendInt(this.message.getGuild().getId());
		response.appendInt(this.message.getThread().getId());
		this.message.serializeMessage(response);
		return response;
	}

}
