package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.incoming.inventory.RequestInventoryBadgesEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SetBadges)
public class SetBadgesEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		for (int i = 0; i < 5; i++)
		{
			int slotId = packet.readInt();
			String badge = packet.readString();

			if (Bootstrap.getEngine().getGame().getBadgeManager().getBadge(connection.getHabbo(), slotId) != null)
			{
				Bootstrap.getEngine().getGame().getBadgeManager().setBadge(connection.getHabbo(), Bootstrap.getEngine().getGame().getBadgeManager().getBadge(connection.getHabbo(), slotId).getBadgeCode(), 0);
			}

			Bootstrap.getEngine().getGame().getBadgeManager().setBadge(connection.getHabbo(), badge, slotId);
		}

		RequestInventoryBadgesEvent requestInventoryBadgesEvent = new RequestInventoryBadgesEvent();
		requestInventoryBadgesEvent.connection = connection;
		requestInventoryBadgesEvent.packet = packet;
		requestInventoryBadgesEvent.handle();
	}

}
