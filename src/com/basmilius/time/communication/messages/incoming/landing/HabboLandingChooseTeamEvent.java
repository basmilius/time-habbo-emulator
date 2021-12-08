package com.basmilius.time.communication.messages.incoming.landing;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.HabboLandingChooseTeam)
public class HabboLandingChooseTeamEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String team = packet.readString();

		if (Bootstrap.getEngine().getGame().getCommunityEventManager().getCurrentCommunityEvent() == null)
			return;

		final String badgeCode = Bootstrap.getEngine().getGame().getCommunityEventManager().getCurrentCommunityEvent().getBadgeForIdentifier(team);

		if (badgeCode.isEmpty())
			return;

		Bootstrap.getEngine().getGame().getBadgeManager().addBadge(connection.getHabbo(), badgeCode, 0, false);
	}

}
