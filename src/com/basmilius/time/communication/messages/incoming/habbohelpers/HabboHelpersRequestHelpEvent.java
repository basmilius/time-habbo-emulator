package com.basmilius.time.communication.messages.incoming.habbohelpers;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.habbohelpers.HabboHelpersGuideSessionAttachedComposer;

@Event(id = Incoming.HabboHelpersRequestHelp)
public class HabboHelpersRequestHelpEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int type = packet.readInt();
		final String issue = packet.readString();

		Bootstrap.getEngine().getGame().getHabboHelpersManager().createHelpRequest(connection.getHabbo(), type, issue);

		connection.send(new HabboHelpersGuideSessionAttachedComposer(false, type, issue, 60));
	}

}
