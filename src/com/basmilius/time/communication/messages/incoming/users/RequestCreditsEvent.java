package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.users.CreditsComposer;

@Event(id = Incoming.RequestCredits)
public class RequestCreditsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.hasHabbo())
			return;

		connection.send(new CreditsComposer(connection));
	}

}
