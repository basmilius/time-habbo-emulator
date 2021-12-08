package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.habbohotel.catalogue.PurchaseException;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.PurchaseFailedComposer;

@Event(id = Incoming.PurchaseItem)
public class PurchaseItemEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		try
		{
			final int pageId = packet.readInt();
			final int itemId = packet.readInt();
			final String extraData = packet.readString();
			final int purchaseCount = packet.readInt();

			final CatalogueItem item = Bootstrap.getEngine().getGame().getCatalogueManager().getItem(itemId);
			final CataloguePage page = Bootstrap.getEngine().getGame().getCatalogueManager().getPage(pageId);

			Bootstrap.getEngine().getGame().getCatalogueManager().purchaseItem(connection, page, item, extraData, purchaseCount, null);
		}
		catch (PurchaseException e)
		{
			connection.send(new PurchaseFailedComposer(PurchaseFailedComposer.PURCHASE_FAILED));
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

}
