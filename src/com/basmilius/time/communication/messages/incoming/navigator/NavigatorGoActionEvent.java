package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.general.RoomForwardComposer;
import com.basmilius.time.communication.messages.outgoing.handshake.GenericErrorMessageComposer;
import com.basmilius.time.util.ObjectUtils;

import java.util.List;

@Event(id = Incoming.NavigatorGoAction)
public class NavigatorGoActionEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String request = packet.readString();

		switch (request)
		{
			case "home":
				connection.send(new RoomForwardComposer(connection.getHabbo().getSettings().getHomeRoom()));
				break;
			case "random_public_room_with_users":
				final List<Room> popularRooms = Bootstrap.getEngine().getGame().getRoomManager().getPopularRooms();
				final Room room = ObjectUtils.getRandomObject(popularRooms);
				
				if (room == null)
					break;
				
				connection.send(new RoomForwardComposer(room.getRoomData().getId()));
				break;
			default:
				connection.send(new GenericErrorMessageComposer(1, Incoming.NavigatorGoAction));
				break;
		}
	}

}
