package com.basmilius.time.communication.messages.incoming.inventory;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.bots.Bot;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.inventory.InventoryBotsComposer;

import java.util.List;

@Event(id = Incoming.RequestInventoryBots)
public class RequestInventoryBotsEvent extends MessageEvent
{

	@Override
	public final void handle()
	{
		if (!connection.hasHabbo())
			return;

		final List<Bot> bots = Bootstrap.getEngine().getGame().getBotManager().getBotsForHabbo(connection.getHabbo());
		connection.send(new InventoryBotsComposer(bots));
	}

}
