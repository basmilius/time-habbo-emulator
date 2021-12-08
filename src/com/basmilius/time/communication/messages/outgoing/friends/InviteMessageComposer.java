package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class InviteMessageComposer extends MessageComposer
{

	private final int userFromId;
	private final String message;

	public InviteMessageComposer(int userFromId, String message)
	{
		this.userFromId = userFromId;
		this.message = message;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.InviteMessage);
		response.appendInt(this.userFromId);
		response.appendString(this.message);
		return response;
	}

}
