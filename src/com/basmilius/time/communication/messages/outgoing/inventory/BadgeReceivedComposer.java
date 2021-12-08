package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.habbohotel.badges.Badge;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class BadgeReceivedComposer extends MessageComposer
{

	private final Badge badge;

	public BadgeReceivedComposer(Badge badge)
	{
		this.badge = badge;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.BadgeReceived);
		response.appendInt(this.badge.getId());
		response.appendString(this.badge.getBadgeCode());
		return response;
	}

}
