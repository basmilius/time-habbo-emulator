package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.FindNewFriendsResultComposer;
import com.basmilius.time.communication.messages.outgoing.general.RoomForwardComposer;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Event(id = Incoming.FindNewFriends)
public class FindNewFriendsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final List<Room> popularRooms = Bootstrap.getEngine().getGame().getRoomManager().getPopularRooms().stream().filter(room -> room.getRoomUnitsHandler().getUsers().size() > 0).collect(Collectors.toList());

		if (popularRooms.size() > 0)
		{
			final Random random = new Random();
			final Room room = popularRooms.get(random.nextInt(popularRooms.size()));
			connection.send(new RoomForwardComposer(room.getRoomData().getId()));
		}

		connection.send(new FindNewFriendsResultComposer(popularRooms.size() > 0));
	}

}
