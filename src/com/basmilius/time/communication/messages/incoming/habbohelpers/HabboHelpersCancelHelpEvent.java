package com.basmilius.time.communication.messages.incoming.habbohelpers;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.habbohelpers.HabboHelpersToolCloseComposer;

@Event(id = Incoming.HabboHelpersCancelHelp)
public class HabboHelpersCancelHelpEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		Bootstrap.getEngine().getGame().getHabboHelpersManager().cancelHelp(connection.getHabbo());

		connection.send(new HabboHelpersToolCloseComposer());
	}

}
