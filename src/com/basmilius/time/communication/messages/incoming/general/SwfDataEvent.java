package com.basmilius.time.communication.messages.incoming.general;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SwfData)
public class SwfDataEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		final boolean unknownBoolean1 = packet.readBoolean();
		final String unknownString1 = packet.readString();
		final String swfBase = packet.readString();
		final String swfVariables = packet.readString();

		// TODO Do something with this client data.
	}

}
