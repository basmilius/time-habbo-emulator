package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.ModerationIssueHandlingWindowSave)
public class ModerationIssueHandlingWindowSaveEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().getPermissions().contains("acc_supporttool"))
			return;

		final int x = packet.readInt();
		final int y = packet.readInt();
		final int width = packet.readInt();
		final int height = packet.readInt();

		// ToDo: Save this.
	}

}
