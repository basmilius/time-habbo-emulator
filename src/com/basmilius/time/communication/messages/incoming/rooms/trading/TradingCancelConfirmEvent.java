package com.basmilius.time.communication.messages.incoming.rooms.trading;

import com.basmilius.time.habbohotel.rooms.trading.TradingSession;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.TradingCancelConfirm)
public class TradingCancelConfirmEvent extends MessageEvent
{

	@Override
	public final void handle()
	{
		final TradingSession session = connection.getHabbo().getCurrentRoom().getTradingEngine().getSession(connection.getHabbo());

		if (session == null)
			return;

		session.cancelConfirm();
	}

}
