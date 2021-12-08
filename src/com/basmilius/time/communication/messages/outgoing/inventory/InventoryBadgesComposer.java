package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.habbohotel.badges.Badge;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class InventoryBadgesComposer extends MessageComposer
{

	private final List<Badge> badges;
	private final List<Badge> usedBadges;

	public InventoryBadgesComposer(final List<Badge> badges, final List<Badge> usedBadges)
	{
		this.badges = badges;
		this.usedBadges = usedBadges;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.InventoryBadges);
		response.appendInt(this.badges.size());
		for (Badge badge : this.badges)
		{
			response.appendInt(badge.getSlotId());
			response.appendString(badge.getBadgeCode());
		}
		response.appendInt(this.usedBadges.size());
		for (Badge badge : this.usedBadges)
		{
			response.appendInt(badge.getSlotId());
			response.appendString(badge.getBadgeCode());
		}
		return response;
	}

}
