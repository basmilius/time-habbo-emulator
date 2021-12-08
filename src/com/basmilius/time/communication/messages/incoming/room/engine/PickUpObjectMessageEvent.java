package com.basmilius.time.communication.messages.incoming.room.engine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.PickUpObject)
public class PickUpObjectMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		@SuppressWarnings("unused")
		final int objectCategory = packet.readInt();
		final int objectId = packet.readInt();

		final UserItem object = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(objectId);
		final Room room = connection.getHabbo().getCurrentRoom();

		if (object == null || room == null)
			return;

		if (!room.getRoomObjectsHandler().containsItem(object))
			return;

		if (!room.getRoomData().getPermissions().hasRights(connection.getHabbo()))
			return;

		room.getRoomObjectsHandler().pickUp(object);
	}

}
