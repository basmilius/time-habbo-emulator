package com.basmilius.time.communication.messages.incoming.room.avatar;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.LookTo)
public class LookToMessageEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		final int x = packet.readInt();
		final int y = packet.readInt();

		if (!connection.getHabbo().isInRoom())
			return;
		
		if (connection.getHabbo().getRoomUser() == null)
			return;

		if (x == connection.getHabbo().getRoomUser().getX() && y == connection.getHabbo().getRoomUser().getY())
			return;

		connection.getHabbo().getRoomUser().lookAtPoint(x, y);
	}

}
