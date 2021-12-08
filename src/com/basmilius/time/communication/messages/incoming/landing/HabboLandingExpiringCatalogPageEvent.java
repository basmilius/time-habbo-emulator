package com.basmilius.time.communication.messages.incoming.landing;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.landing.HabboLandingExpiringCatalogPageComposer;

@Event(id = Incoming.HabboLandingExpiringCatalogPage)
public class HabboLandingExpiringCatalogPageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final CataloguePage page = Bootstrap.getEngine().getGame().getCatalogueManager().getPage(17);
		connection.send(new HabboLandingExpiringCatalogPageComposer(page.getCaptionSave(), 7200, page.getPageTeaser()));
	}

}
