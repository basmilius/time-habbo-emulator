package com.basmilius.time.communication.messages.outgoing.landing;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboLandingNextLimitedRareComposer extends MessageComposer
{

	private final int seconds;
	private final int pageId;
	private final int itemId;
	private final String itemName;

	public HabboLandingNextLimitedRareComposer(final int seconds, final int pageId, final int itemId, final String itemName)
	{
		this.seconds = seconds;
		this.pageId = pageId;
		this.itemId = itemId;
		this.itemName = itemName;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		return response.init(Outgoing.HabboLandingNextLimitedRare).appendInt(this.seconds).appendInt(this.pageId).appendInt(this.itemId).appendString(this.itemName);
	}

}
