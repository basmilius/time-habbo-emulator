package com.basmilius.time.communication.messages.incoming.poll;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.PollCancel)
public class PollCancelEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int pollId = packet.readInt();
	}

}
