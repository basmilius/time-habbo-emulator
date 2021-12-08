package com.basmilius.time.communication.messages.outgoing.rooms.trading;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class TradingConfirmationComposer extends MessageComposer
{

	@Override
	public final ServerMessage compose()
	{
		return response.init(Outgoing.TradingConfirmation);
	}

}
