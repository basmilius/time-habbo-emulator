package com.basmilius.time.communication.messages.incoming.general;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.users.HomeRoomComposer;

@Event(id = Incoming.SetHomeRoom)
public class SetHomeRoomEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int roomId = packet.readInt();

		connection.getHabbo().getSettings().setHomeRoom(roomId);
		connection.send(new HomeRoomComposer(connection.getHabbo().getSettings().getHomeRoom(), -1));
	}

}
