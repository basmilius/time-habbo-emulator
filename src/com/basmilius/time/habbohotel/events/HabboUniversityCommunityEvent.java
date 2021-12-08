package com.basmilius.time.habbohotel.events;

public class HabboUniversityCommunityEvent extends ICommunityEvent
{

	public HabboUniversityCommunityEvent()
	{
		this.getBadges().put("alpha", "HU001");
		this.getBadges().put("beta", "HU002");
		this.getBadges().put("gamma", "HU003");
	}

}
