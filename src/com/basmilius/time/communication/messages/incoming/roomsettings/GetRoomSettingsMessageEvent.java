package com.basmilius.time.communication.messages.incoming.roomsettings;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomSettingsDataComposer;

@Event(id = Incoming.GetRoomSettings)
public class GetRoomSettingsMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int roomId = packet.readInt();
		final Room room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(roomId);

		connection.send(new RoomSettingsDataComposer(room));
	}

}
