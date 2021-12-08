package com.basmilius.time.communication.messages.outgoing.landing;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HotelViewDataParsedComposer extends MessageComposer
{

	private final String data;

	public HotelViewDataParsedComposer(String data)
	{
		this.data = data;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.HotelViewDataParsed);
		response.appendString(this.data);
		response.appendInt(0);
		return response;
	}

}
