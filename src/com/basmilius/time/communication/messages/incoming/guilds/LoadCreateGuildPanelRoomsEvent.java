package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.CreateGuildPanelRoomsComposer;
import com.basmilius.time.communication.messages.outgoing.users.FuserightComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.LoadCreateGuildPanelRooms)
public class LoadCreateGuildPanelRoomsEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final List<Room> goodRooms = new ArrayList<>();
		final List<Room> rooms = Bootstrap.getEngine().getGame().getRoomManager().getRoomsWithOwner(connection.getHabbo());

		for (final Room room : rooms)
		{
			if (room.getRoomData().getGuild() == null)
				continue;

			goodRooms.add(room);
		}

		connection.send(new FuserightComposer(connection.getHabbo()));
		connection.send(new CreateGuildPanelRoomsComposer(goodRooms));
	}

}
