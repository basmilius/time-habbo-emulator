package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.habbohotel.catalogue.GiftWrappingParams;
import com.basmilius.time.habbohotel.catalogue.PurchaseException;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.PurchaseFailedComposer;
import com.basmilius.time.communication.messages.outgoing.catalogue.PurchaseHabboNotFoundComposer;

@Event(id = Incoming.PurchaseItemPresent)
public class PurchaseItemPresentEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		try
		{
			int pageId = packet.readInt();
			int itemId = packet.readInt();
			String extraData = packet.readString();
			String userName = packet.readString();
			String giftWish = packet.readString();
			int presentSpriteId = packet.readInt();
			int boxId = packet.readInt();
			int ribbonId = packet.readInt();
			boolean showHabboFromData = packet.readBoolean();

			CatalogueItem item = Bootstrap.getEngine().getGame().getCatalogueManager().getItem(itemId);
			CataloguePage page = Bootstrap.getEngine().getGame().getCatalogueManager().getPage(pageId);
			Habbo habboTo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(userName);

			if (habboTo == null)
			{
				connection.send(new PurchaseHabboNotFoundComposer());
				return;
			}

			Bootstrap.getEngine().getGame().getCatalogueManager().purchaseItem(connection, page, item, extraData, 1, new GiftWrappingParams(presentSpriteId, boxId, ribbonId, connection.getHabbo(), habboTo, giftWish, showHabboFromData));
		}
		catch (PurchaseException e)
		{
			connection.send(new PurchaseFailedComposer(PurchaseFailedComposer.PURCHASE_FAILED));
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

}
