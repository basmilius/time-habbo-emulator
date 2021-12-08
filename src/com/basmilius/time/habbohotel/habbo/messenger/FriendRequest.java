package com.basmilius.time.habbohotel.habbo.messenger;

import com.basmilius.time.habbohotel.habbo.Habbo;

public class FriendRequest
{

	private final Habbo habbo;
	private final Habbo requesterHabbo;

	public FriendRequest(Habbo habbo, Habbo requesterHabbo)
	{
		this.habbo = habbo;
		this.requesterHabbo = requesterHabbo;
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public Habbo getRequesterHabbo()
	{
		return this.requesterHabbo;
	}
}
