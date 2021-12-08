package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.habbohotel.enums.HabboValues;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.CatalogInitialized)
public class CatalogInitializedEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.getHabbo().setValue(HabboValues.CLIENT_CATALOGUE_OPENED, true);
	}

}
