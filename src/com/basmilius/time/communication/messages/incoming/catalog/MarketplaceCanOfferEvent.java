package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.MarketplaceCanOfferComposer;

@Event(id = Incoming.MarketplaceCanOffer)
public class MarketplaceCanOfferEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int result = MarketplaceCanOfferComposer.CAN_OFFER;

		/**
		 * TODO: Disable marketplace for those with a trading lock.
		 */

		if (connection.getHabbo().getSettings().getCanTrade())
		{
			result = MarketplaceCanOfferComposer.NO_TRADING_PASS;
		}

		connection.send(new MarketplaceCanOfferComposer(result));
	}

}
