package com.basmilius.time.habbohotel.quests;

import com.basmilius.time.habbohotel.abstracts.IManager;
import com.google.common.collect.Lists;

import java.util.List;

public class QuestsManager extends IManager
{
	
	private final List<Quest> quests;
	
	public QuestsManager()
	{
		this.quests = Lists.newLinkedList();
	}
	
	public final List<Quest> getQuests()
	{
		return this.quests;
	}

	@Override
	public final void initialize() throws Exception
	{
	}

	@Override
	public final void dispose()
	{
	}
	
}
