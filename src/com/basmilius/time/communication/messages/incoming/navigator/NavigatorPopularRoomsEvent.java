package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorFlatListComposer;

@Event(id = Incoming.NavigatorPopularRooms)
public class NavigatorPopularRoomsEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		String category = packet.readString();

		int categoryId = Integer.parseInt(category);

		if (categoryId == -1)
		{
			connection.send(new NavigatorFlatListComposer(Bootstrap.getEngine().getGame().getRoomManager().getPopularRooms()));
		}
		else
		{
			connection.send(new NavigatorFlatListComposer(Bootstrap.getEngine().getGame().getRoomManager().getPopularRoomsWithCategory(categoryId)));
		}
	}

}
