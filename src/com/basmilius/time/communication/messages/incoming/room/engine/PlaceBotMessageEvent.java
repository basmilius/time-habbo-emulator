package com.basmilius.time.communication.messages.incoming.room.engine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.bots.Bot;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomBot;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.PlaceBot)
public class PlaceBotMessageEvent extends MessageEvent
{

	@Override
	public final void handle()
	{
		final int botId = packet.readInt();
		final Bot bot = Bootstrap.getEngine().getGame().getBotManager().getBot(botId);
		final Node node = packet.readNodeVector2();
		final Room room = connection.getHabbo().getCurrentRoom();

		if (room.getRoomData().getPermissions().isOwner(connection.getHabbo()))
		{
			final RoomBot roomBot = new RoomBot(room, bot);

			bot.setRoom(room);
			roomBot.setPosition(node);
			room.getRoomUnitsHandler().addRoomUnit(roomBot, true);
		}
	}

}
