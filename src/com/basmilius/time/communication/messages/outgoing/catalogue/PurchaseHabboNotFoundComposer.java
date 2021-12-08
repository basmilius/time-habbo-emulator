package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PurchaseHabboNotFoundComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.PurchaseHabboNotFound);
		return response;
	}

}
