package com.basmilius.time.communication.messages.incoming.room.engine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.MoveWallItem)
public class MoveWallItemMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();
		final String pos = packet.readString();

		if (!connection.getHabbo().isInRoom())
			return;

		final Room room = connection.getHabbo().getCurrentRoom();
		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (!room.getRoomData().getPermissions().hasRights(connection.getHabbo()))
			return;

		if (item == null)
			return;

		if (!item.getIsWallItem())
			return;

		item.setRoom(room);
		item.setWallPos(pos);
		item.updateAllDataInRoom();
	}

}
