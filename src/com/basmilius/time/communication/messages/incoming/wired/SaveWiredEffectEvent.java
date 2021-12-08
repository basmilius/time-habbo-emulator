package com.basmilius.time.communication.messages.incoming.wired;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.wired.WiredSavedComposer;

@Event(id = Incoming.SaveWiredEffect)
public class SaveWiredEffectEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();

		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (item == null || !WiredUserItem.class.isInstance(item))
			return;

		((WiredUserItem) item).save(packet);

		connection.send(new WiredSavedComposer());
	}

}
