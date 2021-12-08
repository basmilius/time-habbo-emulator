package com.basmilius.time.communication.messages.incoming.landing;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.landing.HabboLandingAbstractTimerElementComposer;

@Event(id = Incoming.HabboLandingAbstractTimerElement)
public class HabboLandingAbstractTimerElementEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new HabboLandingAbstractTimerElementComposer());
	}

}
