package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.FriendListInitComposer;
import com.basmilius.time.communication.messages.outgoing.friends.FriendListUsersComposer;

@Event(id = Incoming.RequestFriends)
public class RequestFriendsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.hasHabbo())
			return;

		connection.send(new FriendListInitComposer(connection));
		connection.send(new FriendListUsersComposer(connection));
		connection.getHabbo().getMessenger().handleOfflineMessages();
	}

}
