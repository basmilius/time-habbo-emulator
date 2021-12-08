package com.basmilius.time.communication.messages.outgoing.wired;

import com.basmilius.time.habbohotel.items.furniture.wired.WiredConditionUserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class WiredConditionComposer extends MessageComposer
{

	private final WiredConditionUserItem item;

	public WiredConditionComposer(final WiredConditionUserItem item)
	{
		this.item = item;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.WiredCondition);
		this.item.serializeWiredData(response);
		return response;
	}
}
