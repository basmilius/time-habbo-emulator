package com.basmilius.time.communication.messages.outgoing.wired;

import com.basmilius.time.habbohotel.items.furniture.wired.WiredTriggerUserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class WiredTriggerComposer extends MessageComposer
{

	private final WiredTriggerUserItem item;

	public WiredTriggerComposer(final WiredTriggerUserItem item)
	{
		this.item = item;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.WiredTrigger);
		this.item.serializeWiredData(response);
		return response;
	}

}
