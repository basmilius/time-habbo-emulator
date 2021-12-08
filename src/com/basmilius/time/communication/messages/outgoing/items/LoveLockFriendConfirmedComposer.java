package com.basmilius.time.communication.messages.outgoing.items;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class LoveLockFriendConfirmedComposer extends MessageComposer
{

	private final int itemId;

	public LoveLockFriendConfirmedComposer(final int itemId)
	{
		this.itemId = itemId;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.LoveLockFriendConfirmed);
		response.appendInt(this.itemId);
		return response;
	}

}
