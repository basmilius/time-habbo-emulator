package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorFavoritesComposer;

@Event(id = Incoming.NavigatorRequestFavorites)
public class NavigatorRequestFavoritesEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.hasHabbo())
			return;

		connection.send(new NavigatorFavoritesComposer(connection.getHabbo().getSettings().getRoomFavorites().getFavorites()));
	}

}
