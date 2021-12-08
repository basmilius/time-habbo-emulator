package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorFlatListComposer;

@Event(id = Incoming.NavigatorMyRooms)
public class NavigatorMyRoomsEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		connection.send(new NavigatorFlatListComposer(Bootstrap.getEngine().getGame().getRoomManager().getRoomsWithOwner(connection.getHabbo())));
	}

}
