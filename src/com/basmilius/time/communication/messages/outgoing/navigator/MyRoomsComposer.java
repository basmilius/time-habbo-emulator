package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class MyRoomsComposer extends MessageComposer
{

	private final Habbo habbo;

	public MyRoomsComposer(final Habbo habbo)
	{
		this.habbo = habbo;
	}

	@Override
	public ServerMessage compose()
	{
		final List<Room> rooms = Bootstrap.getEngine().getGame().getRoomManager().getRoomsWithOwner(this.habbo);

		response.init(Outgoing.MyRooms);
		response.appendBoolean(true);
		response.appendInt(rooms.size());
		for (final Room room : rooms)
		{
			response.appendInt(room.getRoomData().getId());
			response.appendString(room.getRoomData().getRoomName());
			response.appendBoolean(true);
		}
		return response;
	}

}
