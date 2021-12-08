package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationActionResultComposer extends MessageComposer
{

	private int userId;
	private boolean success;

	public ModerationActionResultComposer(int userId, boolean success)
	{
		this.userId = userId;
		this.success = success;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationActionResult);
		response.appendInt(this.userId);
		response.appendBoolean(this.success);
		return response;
	}

}
