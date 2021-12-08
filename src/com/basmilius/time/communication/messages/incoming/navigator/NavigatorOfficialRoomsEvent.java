package com.basmilius.time.communication.messages.incoming.navigator;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.navigator.NavigatorOfficialsComposer;

@Event(id = Incoming.NavigatorOfficialRooms)
public class NavigatorOfficialRoomsEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		connection.send(new NavigatorOfficialsComposer());
	}

}
