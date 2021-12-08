package com.basmilius.time.communication.messages.outgoing.handshake;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class EnableTradingComposer extends MessageComposer
{

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.EnableTrading);
		response.appendBoolean(true);
		return response;
	}

}
