package com.basmilius.time.communication.messages.incoming.habbohelpers;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbohelpers.HabboHelpersTicket;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.HabboHelpersSendMessage)
public class HabboHelpersSendMessageEvent extends MessageEvent
{

	@Override
	public final void handle() throws Exception
	{
		final String message = packet.readString();
		final HabboHelpersTicket ticket = Bootstrap.getEngine().getGame().getHabboHelpersManager().getTicketForSession(connection.getHabbo());

		if (ticket == null)
			return;

		ticket.sendMessage(connection.getHabbo(), message);
	}
}
