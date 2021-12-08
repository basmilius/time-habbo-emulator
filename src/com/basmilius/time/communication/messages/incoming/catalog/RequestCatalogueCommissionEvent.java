package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.CatalogueCommissionComposer;

@Event(id = Incoming.RequestCatalogueCommission)
public class RequestCatalogueCommissionEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new CatalogueCommissionComposer());
	}

}
