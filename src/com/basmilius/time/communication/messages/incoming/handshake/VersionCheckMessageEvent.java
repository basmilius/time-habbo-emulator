package com.basmilius.time.communication.messages.incoming.handshake;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.VersionCheck)
public class VersionCheckMessageEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		Bootstrap.getEngine().getLogging().logDebugLine("Preparing " + packet.readString() + " for user..");
	}

}
