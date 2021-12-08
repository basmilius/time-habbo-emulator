package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomSettingsRightsRemovedComposer;
import com.google.common.collect.Lists;

import java.util.List;

@Event(id = Incoming.RoomSettingsRightsRemoveAll)
public class RoomSettingsRightsRemoveAllEvent extends MessageEvent
{
	
	@Override
	public final void handle()
	{
		final int roomId = packet.readInt();
		final Room room = connection.getHabbo().getCurrentRoom();

		if (room == null)
			return;
		
		if (roomId != room.getRoomData().getId())
			return;

		if (!room.getRoomData().getPermissions().isOwner(connection.getHabbo()))
			return;
		
		final List<Habbo> controllers = Lists.newLinkedList(room.getRoomData().getPermissions().getControllers());

		final boolean result = room.getRoomData().getPermissions().removeControllers();

		if (result)
		{
			final QueuedComposers composers = new QueuedComposers();
			for (final Habbo controller : controllers)
				composers.appendComposer(new RoomSettingsRightsRemovedComposer(room.getRoomData().getId(), controller.getId()));
			connection.send(composers);
		}
	}
	
}
