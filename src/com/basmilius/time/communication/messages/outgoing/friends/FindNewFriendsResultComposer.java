package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FindNewFriendsResultComposer extends MessageComposer
{

	private final boolean success;

	public FindNewFriendsResultComposer(boolean success)
	{
		this.success = success;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FindNewFriendsResult);
		response.appendBoolean(this.success);
		return response;
	}

}
