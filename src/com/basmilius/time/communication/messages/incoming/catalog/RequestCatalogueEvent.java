package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.habbohotel.enums.CatalogueMode;
import com.basmilius.time.habbohotel.enums.HabboValues;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.CataloguePagesComposer;

@Event(id = Incoming.RequestCatalogue)
public class RequestCatalogueEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		boolean lastWasNormal = connection.getHabbo().getValue(HabboValues.CLIENT_CATALOGUE_LAST_NORMAL, false);

		CatalogueMode mode = CatalogueMode.fromString(packet.readString());

		if (lastWasNormal && mode != CatalogueMode.NORMAL)
		{
			connection.getHabbo().setValue(HabboValues.CLIENT_CATALOGUE_OPENED, false);
		}

		/*if (client.getHabbo().getValue(HabboValues.CLIENT_CATALOGUE_OPENED, false))
		    return;*/

		connection.send(new CataloguePagesComposer(connection.getHabbo(), mode));

		connection.getHabbo().setValue(HabboValues.CLIENT_CATALOGUE_LAST_NORMAL, mode == CatalogueMode.NORMAL);
	}

}
