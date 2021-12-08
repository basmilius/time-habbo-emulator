package com.basmilius.time.communication.messages.incoming.landing;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.landing.HotelViewDataComposer;

@Event(id = Incoming.HotelViewData)
public class HotelViewDataEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new HotelViewDataComposer(Bootstrap.getEngine().getGame().getReceptionManager().getSpotlightItems()));
	}

}
