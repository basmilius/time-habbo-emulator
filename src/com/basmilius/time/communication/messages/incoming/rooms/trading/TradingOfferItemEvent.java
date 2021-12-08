package com.basmilius.time.communication.messages.incoming.rooms.trading;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.trading.TradingSession;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.TradingOfferItem)
public class TradingOfferItemEvent extends MessageEvent
{

	@Override
	public final void handle()
	{
		final int itemId = packet.readInt();
		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (item == null || !item.getBaseItem().getAllowTrade())
			return;

		final TradingSession session = connection.getHabbo().getCurrentRoom().getTradingEngine().getSession(connection.getHabbo());
		
		if (session == null)
			return;
		
		session.offerItem(connection.getHabbo(), item);
	}

}
