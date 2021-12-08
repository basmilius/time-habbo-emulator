package com.basmilius.time.communication.messages.incoming.landing;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.landing.HabboLandingNextLimitedRareComposer;

@Event(id = Incoming.HabboLandingNextLimitedRare)
public class HabboLandingNextLimitedRareEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new HabboLandingNextLimitedRareComposer(300, 151, 2873, "cloud_egg"));
	}

}
