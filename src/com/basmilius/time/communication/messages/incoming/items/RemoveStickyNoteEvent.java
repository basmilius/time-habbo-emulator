package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.InteractionType;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.RemoveWallItemComposer;


@Event(id = Incoming.RemoveStickyNote)
public class RemoveStickyNoteEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();

		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (item == null)
			return;

		if (!item.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.PostIt))
			return;

		item.setRoom(null);
		item.delete();

		connection.send(new RemoveWallItemComposer(item, connection.getHabbo()));
	}

}
