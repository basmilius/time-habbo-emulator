package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.habbohotel.badges.Badge;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserProfileBadgesComposer extends MessageComposer
{

	private final Habbo habbo;
	private final List<Badge> badges;

	public UserProfileBadgesComposer(Habbo habbo, List<Badge> badges)
	{
		this.habbo = habbo;
		this.badges = new ArrayList<>();

		for (Badge badge : badges)
		{
			if (badge.getSlotId() == 0)
				continue;
			this.badges.add(badge);
		}

		Collections.sort(this.badges, (o1, o2) -> Integer.compare(o1.getSlotId(), o2.getSlotId()));
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UserProfileBadges);
		response.appendInt(this.habbo.getId());
		response.appendInt(this.badges.size());
		for (Badge badge : this.badges)
		{
			response.appendInt(badge.getSlotId());
			response.appendString(badge.getBadgeCode());
		}
		return response;
	}

}
