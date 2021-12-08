package com.basmilius.time.habbohotel.events;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;

import java.util.HashMap;
import java.util.Map;

public class ICommunityEvent
{

	private final Map<String, String> badges;

	public ICommunityEvent()
	{
		this.badges = new HashMap<>();
	}

	public boolean habboHasChosenTeam(final Habbo habbo)
	{
		boolean result = false;

		for (final String badge : this.badges.values())
			if (result)
				break;
			else
				result = Bootstrap.getEngine().getGame().getBadgeManager().hasBadge(habbo, badge);

		return result;
	}

	public String getBadgeForIdentifier(final String identifier)
	{
		if (this.badges.containsKey(identifier))
			return this.badges.get(identifier);
		return null;
	}

	public Map<String, String> getBadges()
	{
		return this.badges;
	}

}
