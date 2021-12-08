package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomSerializer;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class NavigatorFlatListComposer extends MessageComposer
{

	private final List<Room> rooms;

	public NavigatorFlatListComposer(final List<Room> rooms)
	{
		this.rooms = rooms;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.NavigatorFlatList);
		response.appendInt(2);
		response.appendString("");
		response.appendInt(this.rooms.size());
		for (final Room room : this.rooms)
		{
			response.appendBody(new RoomSerializer(room));
		}
		response.appendBoolean(false);
		return response;
	}

}
