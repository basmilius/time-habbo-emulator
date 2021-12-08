package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.users.FuserightComposer;
import com.basmilius.time.communication.messages.outgoing.users.HabboClubComposer;

@Event(id = Incoming.RequestHabboClub)
public class RequestHabboClubEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.hasHabbo())
			return;

		connection.send(new HabboClubComposer(connection.getHabbo(), false));
		connection.send(new FuserightComposer(connection.getHabbo()));
	}

}
