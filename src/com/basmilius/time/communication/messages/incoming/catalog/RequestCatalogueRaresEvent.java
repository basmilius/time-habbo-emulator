package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.CatalogueRaresComposer;

@Event(id = Incoming.RequestCatalogueRares)
public class RequestCatalogueRaresEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new CatalogueRaresComposer());
	}

}
