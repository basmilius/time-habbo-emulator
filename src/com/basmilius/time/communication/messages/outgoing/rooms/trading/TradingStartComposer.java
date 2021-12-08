package com.basmilius.time.communication.messages.outgoing.rooms.trading;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.trading.TradingSession;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class TradingStartComposer extends MessageComposer
{

	private final Habbo habboOne;
	private final Habbo habboTwo;

	public TradingStartComposer(final TradingSession session)
	{
		this.habboOne = session.getHabboOne();
		this.habboTwo = session.getHabboTwo();
	}

	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.TradingStart);
		response.appendInt(this.habboOne.getId());
		response.appendInt(this.habboOne.getSettings().getCanTrade() ? 1 : 0);
		response.appendInt(this.habboTwo.getId());
		response.appendInt(this.habboTwo.getSettings().getCanTrade() ? 1 : 0);
		return response;
	}

}
