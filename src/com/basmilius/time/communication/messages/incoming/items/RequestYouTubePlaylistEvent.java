package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.InteractionType;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.items.YoutubeDisplayPlayVideoComposer;
import com.basmilius.time.communication.messages.outgoing.items.YoutubeDisplayPlaylistsComposer;

@Event(id = Incoming.RequestYouTubePlaylist)
public class RequestYouTubePlaylistEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();

		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (item == null || !item.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.YouTubeTV))
			return;

		connection.send(new YoutubeDisplayPlaylistsComposer(item, item.getHabbo()));

		if (item.getYouTubePlaylist() != null && item.getYouTubePlaylist().getCurrentVideo() != null)
		{
			connection.send(new YoutubeDisplayPlayVideoComposer(item, item.getYouTubePlaylist().getCurrentVideo()));
		}
	}

}
