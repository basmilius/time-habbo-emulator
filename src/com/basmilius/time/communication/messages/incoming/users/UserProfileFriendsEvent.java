package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.users.UserProfileFriendsComposer;

@Event(id = Incoming.RequestUserProfileFriends)
public class UserProfileFriendsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(packet.readInt());
		habbo.loadMessenger();
		connection.send(new UserProfileFriendsComposer(habbo));
	}

}
