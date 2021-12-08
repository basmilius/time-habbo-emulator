package com.basmilius.time.communication.messages.incoming.landing;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.landing.HotelViewDataParsedComposer;

@Event(id = Incoming.HotelViewDataParse)
public class HotelViewDataParseEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new HotelViewDataParsedComposer(packet.readString()));
	}

}
