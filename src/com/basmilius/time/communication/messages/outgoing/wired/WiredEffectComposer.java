package com.basmilius.time.communication.messages.outgoing.wired;

import com.basmilius.time.habbohotel.items.furniture.wired.WiredEffectUserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class WiredEffectComposer extends MessageComposer
{

	private final WiredEffectUserItem item;

	public WiredEffectComposer(WiredEffectUserItem item)
	{
		this.item = item;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.WiredEffect);
		this.item.serializeWiredData(response);
		return response;
	}

}
