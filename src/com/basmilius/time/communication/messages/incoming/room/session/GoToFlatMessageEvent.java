package com.basmilius.time.communication.messages.incoming.room.session;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomDataComposer;

@Event(id = Incoming.GoToFlat, secondId = 1500)
public class GoToFlatMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int roomId = packet.readInt();
		final int data1 = packet.readInt();
		final int data2 = packet.readInt();

		final Room room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(roomId);

		if (room == null)
			return;

		connection.send(new RoomDataComposer(room, (data1 == 1), (data2 == 1)));
	}

}
