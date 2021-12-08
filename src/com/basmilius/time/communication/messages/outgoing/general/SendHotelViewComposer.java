package com.basmilius.time.communication.messages.outgoing.general;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class SendHotelViewComposer extends MessageComposer
{

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.SendHotelView);
		return response;
	}

}
