package com.basmilius.time.communication.messages.incoming.preferences;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.SetUIFlags)
public class SetUIFlagsMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int toolbarState = packet.readInt();
		connection.getHabbo().getSettings().setToolbarState(toolbarState);
	}

}
