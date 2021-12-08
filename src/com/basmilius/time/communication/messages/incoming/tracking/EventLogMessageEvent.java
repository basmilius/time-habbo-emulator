package com.basmilius.time.communication.messages.incoming.tracking;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.EventLog)
public class EventLogMessageEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		final String eventId = packet.readString();
		final String eventType = packet.readString();
		final String eventMessage = packet.readString();

		// TODO Do something with client events.
	}

}
