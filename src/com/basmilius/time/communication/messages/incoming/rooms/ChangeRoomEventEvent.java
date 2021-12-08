package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomEvent;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.ChangeRoomEvent)
public class ChangeRoomEventEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		packet.readInt();
		final String eventName = packet.readString();
		final String eventDescription = packet.readString();

		if (!connection.getHabbo().isInRoom())
			return;

		final Room room = connection.getHabbo().getCurrentRoom();

		if (room == null)
			return;

		final RoomEvent event = room.getRoomData().getRoomEvent();

		event.setName(eventName);
		event.setDescription(eventDescription);
		event.startEvent();
	}

}
