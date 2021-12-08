package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.habbohotel.moderation.issue.ModerationIssue;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationNewTicketComposer extends MessageComposer
{

	private final int state;
	private final ModerationIssue issue;

	public ModerationNewTicketComposer(final int state, final ModerationIssue issue)
	{
		this.state = state;
		this.issue = issue;
	}

	@Override
	public final ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationNewTicket);
		this.issue.serializeIssue(response, state);
		return response;
	}

}
