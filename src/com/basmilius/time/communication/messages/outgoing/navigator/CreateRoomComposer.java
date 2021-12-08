package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CreateRoomComposer extends MessageComposer
{

	private final Room room;

	public CreateRoomComposer(Room room)
	{
		this.room = room;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.CreateRoom);
		response.appendInt(this.room.getRoomData().getId());
		response.appendString(this.room.getRoomData().getRoomName());
		return response;
	}

}
