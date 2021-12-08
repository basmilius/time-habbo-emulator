package com.basmilius.time.communication.messages.incoming.habbohelpers;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbohelpers.HabboHelpersTicket;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.habbohelpers.HabboHelpersGuideSessionPartnerIsTypingComposer;

@Event(id = Incoming.HabboHelpersTyping)
public class HabboHelpersTypingEvent extends MessageEvent
{

	@Override
	public final void handle() throws Exception
	{
		final boolean typing = packet.readBoolean();
		final HabboHelpersTicket ticket = Bootstrap.getEngine().getGame().getHabboHelpersManager().getTicketForSession(connection.getHabbo());

		if (ticket == null)
			return;

		ticket.send(new HabboHelpersGuideSessionPartnerIsTypingComposer(typing), connection.getHabbo());
	}

}
