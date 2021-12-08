package com.basmilius.time.communication.messages.incoming.room.engine;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.UseWallItem)
public class UseWallItemMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;

		final int itemId = packet.readInt();
		final int param = packet.readInt();
		final Room room = connection.getHabbo().getCurrentRoom();
		final UserItem item = room.getRoomObjectsHandler().getWallItem(itemId);

		if (item == null)
			return;

		room.getRoomInteractions().updateWallItemState(item, connection.getHabbo().getRoomUser(), param);
	}

}
