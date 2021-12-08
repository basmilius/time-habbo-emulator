package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.RedeemCredits)
public class RedeemCreditsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();
		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (item == null)
			return;

		if (!connection.getHabbo().isInRoom())
			return;

		final int amountCoins = item.getCatalogItem().getCostsCredits();

		connection.getHabbo().getSettings().updateCredits(amountCoins);

		item.delete();
	}

}
