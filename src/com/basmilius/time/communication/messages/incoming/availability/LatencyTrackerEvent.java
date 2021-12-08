package com.basmilius.time.communication.messages.incoming.availability;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.LatencyTracker)
public class LatencyTrackerEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
	    /*
		 * TODO: Structure: I I I; LatencyTracker, think that the Habbo Client can calculate when there's some lagg.
		 */
		Bootstrap.getEngine().getLogging().logDebugLine(String.format("LatencyTrackerEvent: %d %d %d", packet.readInt(), packet.readInt(), packet.readInt()));
	}

}
