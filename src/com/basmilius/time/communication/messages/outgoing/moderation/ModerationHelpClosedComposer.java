package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationHelpClosedComposer extends MessageComposer
{

	public static final int CLOSED = 0;
	public static final int USELESS = 1;
	public static final int ABUSIVE = 2;

	private int result;

	public ModerationHelpClosedComposer(int result)
	{
		this.result = result;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationHelpClosed);
		response.appendInt(this.result);
		return response;
	}

}
