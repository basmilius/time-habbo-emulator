package com.basmilius.time.communication.messages.outgoing.items;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class LoveLockFriendDeniedComposer extends MessageComposer
{

	private final int itemId;

	public LoveLockFriendDeniedComposer(final int itemId)
	{
		this.itemId = itemId;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.LoveLockFriendDenied);
		response.appendInt(this.itemId);
		return response;
	}

}
