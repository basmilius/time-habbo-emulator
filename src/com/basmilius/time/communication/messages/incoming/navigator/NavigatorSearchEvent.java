package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorFlatListComposer;

@Event(id = Incoming.NavigatorSearch)
public class NavigatorSearchEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String searchQuery = packet.readString();

		if (searchQuery.replace(" ", "").equalsIgnoreCase(""))
			return; // Empty search query.

		connection.send(new NavigatorFlatListComposer(Bootstrap.getEngine().getGame().getRoomManager().getRoomsWithQuery(searchQuery)));
	}

}
