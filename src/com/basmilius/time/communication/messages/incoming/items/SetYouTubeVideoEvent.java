package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SetYouTubeVideo)
public class SetYouTubeVideoEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int tvId = packet.readInt();
		final String videoId = packet.readString();

		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(tvId);

		if (item == null)
			return;

		item.setExtraData(videoId);
		item.updateStateInRoom();
		item.updateAllDataInRoom();
		item.resolveYouTubeData(true, connection.getHabbo());
	}

}
