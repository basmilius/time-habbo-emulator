package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FriendRequestComposer extends MessageComposer
{

	private final Habbo habbo;

	public FriendRequestComposer(Habbo habbo)
	{
		this.habbo = habbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FriendRequest);
		response.appendInt(this.habbo.getId());
		response.appendString(this.habbo.getUsername());
		response.appendString(this.habbo.getLook());
		return response;
	}

}
