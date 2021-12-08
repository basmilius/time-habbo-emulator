package com.basmilius.time.habbohotel.events;

import com.basmilius.time.habbohotel.abstracts.IManager;

public class CommunityEventManager extends IManager
{

	private ICommunityEvent currentCommunityEvent;

	public CommunityEventManager()
	{
		this.currentCommunityEvent = new HabboUniversityCommunityEvent();
	}

	public ICommunityEvent getCurrentCommunityEvent()
	{
		return this.currentCommunityEvent;
	}

	@Override
	public void initialize() throws Exception
	{

	}

	@Override
	public void dispose()
	{

	}

}
