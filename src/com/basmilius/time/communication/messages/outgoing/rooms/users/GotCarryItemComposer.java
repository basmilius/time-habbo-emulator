package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GotCarryItemComposer extends MessageComposer
{

	private final int unitId;
	private final int itemId;

	public GotCarryItemComposer(int unitId, int itemId)
	{
		this.unitId = unitId;
		this.itemId = itemId;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GotCarryItem);
		response.appendInt(this.unitId);
		response.appendInt(this.itemId);
		return response;
	}

}
