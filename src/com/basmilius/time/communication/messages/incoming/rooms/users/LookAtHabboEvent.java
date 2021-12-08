package com.basmilius.time.communication.messages.incoming.rooms.users;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

/**
 * This event is fired when a Habbo clicks on another Habbo.
 * We have the LookToMessageEvent class, so this one is unused.
 */
@Event(id = Incoming.LookAtHabbo)
public class LookAtHabboEvent extends MessageEvent
{

	@Override
	@SuppressWarnings("unused")
	public void handle()
	{
		final int habboId = packet.readInt();
	}

}
