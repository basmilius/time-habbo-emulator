package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.users.UserProfileComposer;

@Event(id = Incoming.RequestUserProfile)
public class UserProfileEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(packet.readInt());
		if (habbo == null)
		{
			habbo = connection.getHabbo();
		}
		habbo.loadMessenger();

		connection.send(new UserProfileComposer(connection, habbo));
	}

}
