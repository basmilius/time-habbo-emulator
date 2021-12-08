package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.CatalogueOfferComposer;

@Event(id = Incoming.RequestCatalogueOffer)
public class RequestCatalogueOfferEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int offerId = packet.readInt();
		final CatalogueItem item = Bootstrap.getEngine().getGame().getCatalogueManager().getItemByOffer(offerId);

		if (item == null)
			return;

		connection.send(new CatalogueOfferComposer(item));
	}

}
