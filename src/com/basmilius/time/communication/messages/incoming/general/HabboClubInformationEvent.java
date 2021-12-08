package com.basmilius.time.communication.messages.incoming.general;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.habbohotel.enums.HabboValues;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.incoming.catalog.RequestCatalogueEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.general.ClubInfoComposer;
import com.basmilius.time.communication.messages.outgoing.handshake.GenericErrorMessageComposer;

import java.util.List;

@Event(id = Incoming.HabboClubInformation)
public class HabboClubInformationEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().getValues().containsKey(HabboValues.CLIENT_CATALOGUE_OPENED))
		{
			RequestCatalogueEvent rce = new RequestCatalogueEvent();
			rce.connection = connection;
			rce.packet = packet;
			rce.handle();
		}
		
		final CataloguePage page = Bootstrap.getEngine().getGame().getCatalogueManager().getPage("club_buy");
		
		if (page == null)
			return;

		List<CatalogueItem> items = page.getItems();

		if (items == null || (items.size() == 0))
		{
			connection.send(new GenericErrorMessageComposer((items == null) ? 0 : 1, Incoming.HabboClubInformation));
			return;
		}

		connection.send(new ClubInfoComposer(connection.getHabbo(), packet.readInt(), items));
	}

}
