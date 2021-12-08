package com.basmilius.time.communication.messages.incoming.inventory;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.badges.Badge;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.inventory.InventoryBadgesComposer;
import com.basmilius.time.communication.messages.outgoing.users.UserProfileBadgesComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.RequestInventoryBadges)
public class RequestInventoryBadgesEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final List<Badge> badges = new ArrayList<>();
		final List<Badge> usedBadges = new ArrayList<>();
		final List<Badge> userBadges = Bootstrap.getEngine().getGame().getBadgeManager().getBadgesForHabbo(connection.getHabbo());

		for (final Badge badge : userBadges)
		{
			if (badge.getSlotId() > 0)
				usedBadges.add(badge);
			else
				badges.add(badge);
		}

		badges.addAll(usedBadges);

		connection.send(new InventoryBadgesComposer(badges, usedBadges));
		connection.send(new UserProfileBadgesComposer(connection.getHabbo(), Bootstrap.getEngine().getGame().getBadgeManager().getBadgesForHabbo(connection.getHabbo())));
	}

}
