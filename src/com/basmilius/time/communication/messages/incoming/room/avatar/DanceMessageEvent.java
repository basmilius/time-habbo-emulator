package com.basmilius.time.communication.messages.incoming.room.avatar;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.Dance)
public class DanceMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;
		
		if (connection.getHabbo().getRoomUser() == null)
			return;

		int danceId = packet.readInt();

		if (danceId < 0 || danceId > 4)
			danceId = 0;

		connection.getHabbo().getRoomUser().dance(danceId);
	}

}
