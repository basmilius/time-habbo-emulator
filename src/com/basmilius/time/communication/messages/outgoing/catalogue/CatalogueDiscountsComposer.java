package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CatalogueDiscountsComposer extends MessageComposer
{

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.CatalogueDiscounts);
		response.appendInt(100);
		response.appendInt(Bootstrap.getEngine().getConfig().getInt("hotel.catalog.discounts.amount", 6));
		response.appendInt(1);
		response.appendInt(1);
		response.appendInt(2);
		response.appendInt(40);
		response.appendInt(99);
		return response;
	}

}
