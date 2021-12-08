package com.basmilius.time.communication.messages.incoming.rooms.users;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.PutCarryItemDown)
public class PutCarryItemDownEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;

		if (connection.getHabbo().getRoomUser() == null)
			return;

		connection.getHabbo().getRoomUser().carryItem(0);
	}

}