package com.basmilius.time.communication.messages.incoming.room.session;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.OpenFlatConnection)
public class OpenFlatConnectionMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int roomId = packet.readInt();
		final String password = packet.readString();

		Bootstrap.getEngine().getGame().getRoomManager().loadRoomForHabbo(connection.getHabbo(), roomId, password);
	}

}
