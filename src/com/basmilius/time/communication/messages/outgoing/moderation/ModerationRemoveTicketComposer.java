package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationRemoveTicketComposer extends MessageComposer
{

	private int ticketId;

	public ModerationRemoveTicketComposer(int ticketId)
	{
		this.ticketId = ticketId;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationRemoveTicket);
		response.appendString(this.ticketId);
		return response;
	}
}
