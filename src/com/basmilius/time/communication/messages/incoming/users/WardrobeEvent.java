package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.users.WardrobeComposer;

@Event(id = Incoming.Wardrobe)
public class WardrobeEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new WardrobeComposer(connection.getHabbo().getSettings().getWardrobe()));
	}

}
