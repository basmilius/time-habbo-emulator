package com.basmilius.time.communication.messages.incoming.roomsettings;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.general.LinkEventComposer;

@Event(id = Incoming.DeleteRoom)
public class DeleteRoomMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int roomId = packet.readInt();
		final Room room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(roomId);

		if (room == null)
			return;

		if (!room.getRoomData().getPermissions().isOwner(connection.getHabbo()))
			return;

		for (final RoomUser roomUser : room.getRoomUnitsHandler().getUsers())
		{
			final Habbo habbo = roomUser.getHabbo();
			roomUser.kick(true);
			habbo.getConnection().send(new LinkEventComposer("navigator/tab/popular"));
		}

		room.delete();
	}

}
