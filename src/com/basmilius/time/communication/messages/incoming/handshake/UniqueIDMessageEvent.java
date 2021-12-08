package com.basmilius.time.communication.messages.incoming.handshake;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.UniqueID)
public class UniqueIDMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String machineId = packet.readString();
		// TODO Save machine id or something.
	}

}
