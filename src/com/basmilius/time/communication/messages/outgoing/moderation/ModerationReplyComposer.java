package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationReplyComposer extends MessageComposer
{

	private String message;

	public ModerationReplyComposer(String message)
	{
		this.message = message;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationReply);
		response.appendString(this.message);
		return response;
	}

}
