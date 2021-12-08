package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.RejectFriendship)
public class RejectFriendshipEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		packet.readBoolean();
		int modus = packet.readInt();

		if (modus == 1)
		{
			int habboFromId = packet.readInt();

			connection.getHabbo().getMessenger().handleRequest(habboFromId, false);
		}
		else
		{
			connection.getHabbo().getMessenger().removeAllRequests();
		}
	}

}
