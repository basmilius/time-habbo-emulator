package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.AcceptFriendship)
public class AcceptFriendshipEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int count = packet.readInt();

		for (int i = 0; i < count; i++)
		{
			int habboFromId = packet.readInt();

			connection.getHabbo().getMessenger().handleRequest(habboFromId, true);
		}
	}

}
