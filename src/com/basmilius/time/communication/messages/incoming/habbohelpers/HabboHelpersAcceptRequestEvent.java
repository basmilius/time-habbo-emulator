package com.basmilius.time.communication.messages.incoming.habbohelpers;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbohelpers.HabboHelper;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.HabboHelpersAcceptRequest)
public class HabboHelpersAcceptRequestEvent extends MessageEvent
{

	@Override
	public final void handle() throws Exception
	{
		final boolean accept = packet.readBoolean();
		final HabboHelper helper = Bootstrap.getEngine().getGame().getHabboHelpersManager().getHelper(connection.getHabbo());

		if (helper == null)
			return;

		Bootstrap.getEngine().getGame().getHabboHelpersManager().pick(helper, accept);
	}

}
