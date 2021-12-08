package com.basmilius.time.communication.messages.incoming.landing;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.landing.HotelViewDataOKComposer;

@Event(id = Incoming.HotelViewDataChecker)
public class HotelViewDataCheckerEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String data = packet.readString();

		if (data.split(",").length < 2)
		{
			connection.send(new HotelViewDataOKComposer("", ""));
			return;
		}

		String[] d = data.split(",");

		connection.send(new HotelViewDataOKComposer(data, d[d.length - 1]));
	}

}
