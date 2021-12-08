package com.basmilius.time.communication.messages.incoming.tracking;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.availability.LatencyPongComposer;

@Event(id = Incoming.LatencyPingRequest)
public class LatencyPingRequestMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int id = packet.readInt();

		connection.send(new LatencyPongComposer(id));
	}

}
