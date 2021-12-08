package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomSettingsRightsRemovedComposer;

@Event(id = Incoming.RoomSettingsRightsRemove)
public class RoomSettingsRightsRemoveEvent extends MessageEvent
{
	
	@Override
	public final void handle()
	{
		int count = packet.readInt();
		while (count-- > 0)
		{
			final int userId = packet.readInt();
			final Habbo user = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(userId);

			if (user == null)
				return;

			if (!connection.getHabbo().getMessenger().isFriendWith(user))
				return;

			final Room room = connection.getHabbo().getCurrentRoom();

			if (room == null)
				return;

			if (!room.getRoomData().getPermissions().isOwner(connection.getHabbo()))
				return;

			final boolean result = room.getRoomData().getPermissions().removeController(user);

			if (result)
			{
				connection.send(new RoomSettingsRightsRemovedComposer(room.getRoomData().getId(), user.getId()));
			}
		}
	}
	
}
