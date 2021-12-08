package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.FriendRequestsComposer;

@Event(id = Incoming.RequestFriendRequests)
public class RequestFriendRequestsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new FriendRequestsComposer(connection.getHabbo().getMessenger().getFriendRequests()));
	}

}
