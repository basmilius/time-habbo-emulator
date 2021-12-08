package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomDataSerializer;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomDataComposer extends MessageComposer
{

	private final Room room;
	private final boolean data1;
	private final boolean data2;

	public RoomDataComposer(final Room room, final boolean data1, final boolean data2)
	{
		this.room = room;
		this.data1 = data1;
		this.data2 = data2;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomData);
		response.appendBody(new RoomDataSerializer(this.room, this.data1, this.data2));
		return response;
	}

}
