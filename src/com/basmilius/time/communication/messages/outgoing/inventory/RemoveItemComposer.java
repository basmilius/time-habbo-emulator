package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RemoveItemComposer extends MessageComposer
{

	private final int itemId;

	public RemoveItemComposer(final int itemId)
	{
		this.itemId = itemId;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RemoveItem);
		response.appendInt(this.itemId);
		return response;
	}

}
