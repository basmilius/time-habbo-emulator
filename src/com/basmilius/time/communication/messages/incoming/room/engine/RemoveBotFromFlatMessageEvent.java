package com.basmilius.time.communication.messages.incoming.room.engine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.bots.Bot;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.RemoveBotFromFlat)
public class RemoveBotFromFlatMessageEvent extends MessageEvent
{

	@Override
	public final void handle()
	{
		final int botId = packet.readInt();
		final Bot bot = Bootstrap.getEngine().getGame().getBotManager().getBot(botId);
		final Room room = connection.getHabbo().getCurrentRoom();

		if (room.getRoomData().getPermissions().isOwner(connection.getHabbo()))
		{
			room.getRoomUnitsHandler().removeRoomUnit(bot.getRoomBot());
		}
	}

}
