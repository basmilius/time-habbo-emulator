package com.basmilius.time.communication.messages.outgoing.rooms.trading;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class TradingCloseComposer extends MessageComposer
{

	private final int habboId;
	private final boolean selfCancel;

	public TradingCloseComposer(final int habboId, final boolean selfCancel)
	{
		this.habboId = habboId;
		this.selfCancel = selfCancel;
	}

	@Override
	public final ServerMessage compose()
	{
		return response.init(Outgoing.TradingClose).appendInt(this.habboId).appendInt(selfCancel ? 1 : 0);
	}

}
