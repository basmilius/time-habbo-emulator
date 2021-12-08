package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.FloorPlanEditorDoorComposer;

@Event(id = Incoming.FloorPlanEditorRequestDoor)
public class FloorPlanEditorRequestDoorEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;

		Room room = connection.getHabbo().getCurrentRoom();

		if (room == null)
			return;

		if (!room.getRoomData().getPermissions().isOwner(connection.getHabbo()))
			return;

		connection.send(new FloorPlanEditorDoorComposer(room.getRoomData().getRoomModel()));
	}

}
