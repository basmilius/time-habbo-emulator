package com.basmilius.time.communication.messages.outgoing.landing;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HotelViewDataOKComposer extends MessageComposer
{

	private final String data;
	private final String dataKey;

	public HotelViewDataOKComposer(String data, String dataKey)
	{
		this.data = data;
		this.dataKey = dataKey;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.HotelViewDataOK);
		response.appendString(this.data);
		response.appendString(this.dataKey);
		return response;
	}

}
