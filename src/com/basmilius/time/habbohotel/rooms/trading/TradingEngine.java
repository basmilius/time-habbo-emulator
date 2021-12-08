package com.basmilius.time.habbohotel.rooms.trading;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.outgoing.rooms.trading.TradingStartComposer;

import java.util.ArrayList;
import java.util.List;

public class TradingEngine
{

	private final Room room;
	private final List<TradingSession> sessions;

	public TradingEngine(final Room room)
	{
		this.room = room;
		this.sessions = new ArrayList<>();
	}

	public final void cancel(final Habbo habbo, TradingSession session)
	{
		session.cancel(habbo);
		this.finalize(session);
	}

	public final void finalize(final TradingSession session)
	{
		synchronized (this.sessions)
		{
			this.sessions.remove(session);
		}
	}

	public final Room getRoom()
	{
		return this.room;
	}

	public final TradingSession getSession(final Habbo habbo)
	{
		if (this.isTrading(habbo))
			return (TradingSession) this.sessions.stream().filter(session -> session.getHabboOne() == habbo || session.getHabboTwo() == habbo).toArray()[0];

		return null;
	}

	public final boolean isTrading(final Habbo habbo)
	{
		return (this.sessions.stream().filter(session -> session.getHabboOne() == habbo || session.getHabboTwo() == habbo).toArray().length > 0);
	}

	public final void startSession(final Habbo habboOne, final Habbo habboTwo)
	{
		final TradingSession session = new TradingSession(this, habboOne, habboTwo);
		session.send(new TradingStartComposer(session));
		this.sessions.add(session);
	}

}
