package com.basmilius.time.communication.messages.outgoing.rooms.trading;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class TradingAcceptComposer extends MessageComposer
{

	private final int userId;
	private final boolean accepted;

	public TradingAcceptComposer(final int userId, final boolean accepted)
	{
		this.userId = userId;
		this.accepted = accepted;
	}

	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.TradingAccept);
		response.appendInt(this.userId);
		response.appendInt(this.accepted ? 1 : 0);
		return response;
	}

}
