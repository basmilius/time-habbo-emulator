package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RelativeHeightmapComposer extends MessageComposer
{

	private final Room room;

	public RelativeHeightmapComposer(Room room)
	{
		this.room = room;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RelativeHeightmap);
		response.appendBoolean(true);
		response.appendInt(this.room.getRoomData().getRoomDecoration().getWallHeight());
		response.appendString(this.room.getRoomData().getRoomModel().getRelativeMap());
		return response;
	}

}
