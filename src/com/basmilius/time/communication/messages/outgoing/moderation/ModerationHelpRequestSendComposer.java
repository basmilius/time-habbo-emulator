package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationHelpRequestSendComposer extends MessageComposer
{

	public static final int SENDED = 0;
	public static final int UNKNOWN = 1;
	public static final int NOT_COOL = 2;

	private int result;

	public ModerationHelpRequestSendComposer(int result)
	{
		this.result = result;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationHelpRequestSend);
		response.appendInt(this.result);
		return response;
	}

}
