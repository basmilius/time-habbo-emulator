package com.basmilius.time.communication.messages.incoming.rooms.users;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.users.RoomUserTypingComposer;

@Event(id = Incoming.StartTyping)
public class StartTypingEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;

		if (connection.getHabbo().getRoomUser() == null)
			return;

		if (connection.getHabbo().getCanUseSpecialCommands())
			return; // We don't want our cover blown away

		connection.getHabbo().getCurrentRoom().getRoomUnitsHandler().send(new RoomUserTypingComposer(connection.getHabbo().getRoomUser().getUnitId(), true));
	}

}
