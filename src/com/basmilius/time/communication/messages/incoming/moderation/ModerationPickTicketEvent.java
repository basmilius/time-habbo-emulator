package com.basmilius.time.communication.messages.incoming.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationNewTicketComposer;

@Event(id = Incoming.ModerationPickTicket)
public class ModerationPickTicketEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().getPermissions().contains("acc_supporttool"))
			return;

		final int state = packet.readInt();
		final int issueId = packet.readInt();

		connection.send(new ModerationNewTicketComposer(state, Bootstrap.getEngine().getGame().getModerationManager().getIssue(issueId)));
	}

}
