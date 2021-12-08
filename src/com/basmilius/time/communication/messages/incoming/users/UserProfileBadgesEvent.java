package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.users.UserProfileBadgesComposer;

@Event(id = Incoming.RequestUserProfileBadges)
public class UserProfileBadgesEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(packet.readInt());

		if (habbo == null)
			return; // Habbo not found!

		connection.send(new UserProfileBadgesComposer(habbo, Bootstrap.getEngine().getGame().getBadgeManager().getBadgesForHabbo(habbo)));
	}

}
