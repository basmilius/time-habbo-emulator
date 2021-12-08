package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationTicketClosedAlertComposer extends MessageComposer
{

	public static final int HANDLED = 1;
	public static final int USELESS = 2;
	public static final int ABUSIVE = 3;

	private int reason;

	public ModerationTicketClosedAlertComposer(int reason)
	{
		this.reason = reason;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationTicketClosedAlert);
		response.appendInt(this.reason);
		return response;
	}

}
