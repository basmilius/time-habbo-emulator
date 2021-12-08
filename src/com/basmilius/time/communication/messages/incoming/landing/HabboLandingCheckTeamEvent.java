package com.basmilius.time.communication.messages.incoming.landing;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.landing.HabboLandingTeamChosenComposer;

@Event(id = Incoming.HabboLandingCheckTeam)
public class HabboLandingCheckTeamEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String team = packet.readString();

		if (Bootstrap.getEngine().getGame().getCommunityEventManager().getCurrentCommunityEvent() == null)
			return;

		connection.send(new HabboLandingTeamChosenComposer(team, Bootstrap.getEngine().getGame().getCommunityEventManager().getCurrentCommunityEvent().habboHasChosenTeam(connection.getHabbo())));
	}

}
