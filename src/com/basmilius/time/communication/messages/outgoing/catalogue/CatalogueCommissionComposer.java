package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CatalogueCommissionComposer extends MessageComposer
{

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.CatalogueCommission);
		response.appendBoolean(true);
		response.appendInt(1);
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(1);
		response.appendInt(10000);
		response.appendInt(48);
		response.appendInt(7);
		return response;
	}

}
