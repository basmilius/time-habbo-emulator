package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CatalogueOfferComposer extends MessageComposer
{

	private final CatalogueItem item;

	public CatalogueOfferComposer(final CatalogueItem item)
	{
		this.item = item;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		return response.init(Outgoing.CatalogueOffer).appendBody(this.item);
	}

}
