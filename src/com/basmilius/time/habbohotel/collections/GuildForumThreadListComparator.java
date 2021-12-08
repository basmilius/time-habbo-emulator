package com.basmilius.time.habbohotel.collections;

import com.basmilius.time.habbohotel.guilds.forum.GuildForumThread;

import java.util.Comparator;

public class GuildForumThreadListComparator implements Comparator<GuildForumThread>
{

	private final boolean reversed;

	public GuildForumThreadListComparator(final boolean reversed)
	{
		this.reversed = reversed;
	}

	@Override
	public int compare(GuildForumThread thread1, GuildForumThread thread2)
	{
		return Integer.compare(thread1.getMessageLatest().getCreatedOn(), thread2.getMessageLatest().getCreatedOn()) * ((this.reversed) ? -1 : 1);
	}

}
