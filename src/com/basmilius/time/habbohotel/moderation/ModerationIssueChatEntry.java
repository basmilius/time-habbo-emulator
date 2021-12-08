package com.basmilius.time.habbohotel.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;

public class ModerationIssueChatEntry
{

	private final int habboId;
	private final String message;

	public ModerationIssueChatEntry(final int habboId, final String message)
	{
		this.habboId = habboId;
		this.message = message;
	}

	public final Habbo getHabbo()
	{
		return Bootstrap.getEngine().getGame().getHabboManager().getHabbo(this.habboId);
	}

	public final String getMessage()
	{
		return this.message;
	}

}
