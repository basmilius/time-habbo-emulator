package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorFlatListComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.NavigatorFavoriteRooms)
public class NavigatorFavoriteRoomsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		List<Room> favoriteRooms = new ArrayList<>();

		for (final int favoriteRoom : connection.getHabbo().getSettings().getRoomFavorites().getFavorites())
		{
			favoriteRooms.add(Bootstrap.getEngine().getGame().getRoomManager().getRoom(favoriteRoom));
		}

		connection.send(new NavigatorFlatListComposer(favoriteRooms));
	}

}
