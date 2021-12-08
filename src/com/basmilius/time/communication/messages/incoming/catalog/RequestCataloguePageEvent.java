package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.habbohotel.enums.CatalogueMode;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.CataloguePageComposer;

@Event(id = Incoming.RequestCataloguePage)
public class RequestCataloguePageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int pageId = packet.readInt();
		packet.readInt();
		final CatalogueMode mode = CatalogueMode.fromString(packet.readString());

		final CataloguePage page = Bootstrap.getEngine().getGame().getCatalogueManager().getPage(pageId);

		if (page == null)
			return;

		if (!page.getIsEnabled())
			return;

		if (page.getMode() != mode && page.getMode() != CatalogueMode.BUILDER_NORMAL)
			return;

		connection.send(new CataloguePageComposer(page, mode));
	}

}
