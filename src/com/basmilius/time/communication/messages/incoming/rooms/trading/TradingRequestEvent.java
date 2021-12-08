package com.basmilius.time.communication.messages.incoming.rooms.trading;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.trading.TradingOpenFailedComposer;

@Event(id = Incoming.TradingRequest)
public class TradingRequestEvent extends MessageEvent
{

	@Override
	public final void handle()
	{
		final int unitId = packet.readInt();
		final RoomUnit unit = connection.getHabbo().getCurrentRoom().getRoomUnitsHandler().getUnit(unitId);

		if (unit == null)
			return;

		if (!RoomUser.class.isInstance(unit))
			return;

		final Habbo habbo = ((RoomUser) unit).getHabbo();
		final Room room = connection.getHabbo().getCurrentRoom();

		if (!habbo.isOnline() || !habbo.isInRoom())
			return;

		if (room.getTradingEngine().isTrading(connection.getHabbo()))
		{
			connection.send(new TradingOpenFailedComposer(TradingOpenFailedComposer.ErrorCode.YouAreTrading, connection.getHabbo().getUsername()));
			return;
		}

		if (room.getTradingEngine().isTrading(habbo))
		{
			connection.send(new TradingOpenFailedComposer(TradingOpenFailedComposer.ErrorCode.OtherIsTrading, habbo.getUsername()));
			return;
		}

		room.getTradingEngine().startSession(connection.getHabbo(), habbo);
	}

}
