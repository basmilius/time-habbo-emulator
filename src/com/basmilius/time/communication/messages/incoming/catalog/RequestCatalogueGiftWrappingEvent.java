package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.CatalogueGiftWrappingComposer;

@Event(id = Incoming.RequestCatalogueGiftWrapping)
public class RequestCatalogueGiftWrappingEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new CatalogueGiftWrappingComposer());
	}

}
