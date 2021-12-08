package com.basmilius.time.communication.messages.incoming.landing;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.DoorbellRemoveComposer;
import com.basmilius.time.communication.messages.outgoing.navigator.DoorbellRemoveUserComposer;

@Event(id = Incoming.HotelView)
public class HotelViewEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		// TODO: Send some information to the client, like competitions or so.

		// Remove user from room, if it is in a room.
		if (connection.getHabbo().isInRoom())
		{
			connection.getHabbo().leaveRoom();
		}

		// Check doorbell
		if (Bootstrap.getEngine().getGame().getRoomManager().getDoorbell().containsKey(connection.getHabbo().getUsername()))
		{
			final int roomId = Bootstrap.getEngine().getGame().getRoomManager().getDoorbell().get(connection.getHabbo().getUsername());
			final Room room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(roomId);

			if (room == null)
				return;

			connection.send(new DoorbellRemoveComposer());
			room.getRoomUnitsHandler().send(new DoorbellRemoveUserComposer(connection.getHabbo().getUsername()), true);
		}
	}

}
