package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.RoomCategoriesComposer;

@Event(id = Incoming.RequestRoomCategories)
public class RequestRoomCategoriesEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.send(new RoomCategoriesComposer());
	}

}
