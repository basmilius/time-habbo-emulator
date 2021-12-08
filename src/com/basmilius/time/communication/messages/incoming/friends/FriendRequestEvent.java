package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.FriendRequestComposer;

@Event(id = Incoming.FriendRequest)
public class FriendRequestEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String userName = packet.readString();
		final Habbo requestedHabbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(userName);

		if (requestedHabbo == null)
			return;

		boolean success = connection.getHabbo().getMessenger().addFriendRequest(requestedHabbo);

		if (!requestedHabbo.isOnline() || !success)
			return;

		requestedHabbo.getConnection().send(new FriendRequestComposer(connection.getHabbo()));
	}

}
