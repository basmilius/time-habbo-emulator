package com.basmilius.time.communication.messages.incoming.rooms.users;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.users.IgnoresComposer;

@Event(id = Incoming.IgnoreHabbo)
public class IgnoreHabboEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String userName = packet.readString();
		final Habbo ignoredUser = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(userName);

		if (ignoredUser == null)
			return;

		connection.getHabbo().getSettings().getIgnores().addHabboToIgnore(ignoredUser);
		connection.send(new IgnoresComposer(connection.getHabbo().getSettings().getIgnores().getIgnores()));
	}

}
