package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SetYouTubeVideoNext)
public class SetYouTubeVideoNextEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();
		final int mode = packet.readInt();

		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (item == null)
			return;

		if (item.getYouTubePlaylist() == null)
			return;

		if (mode == 0)
		{
			item.getYouTubePlaylist().previousVideo();
		}
		else if (mode == 1)
		{
			item.getYouTubePlaylist().nextVideo();
		}
	}

}
