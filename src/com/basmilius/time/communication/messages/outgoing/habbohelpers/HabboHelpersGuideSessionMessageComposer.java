package com.basmilius.time.communication.messages.outgoing.habbohelpers;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboHelpersGuideSessionMessageComposer extends MessageComposer
{

	private final int senderId;
	private final String message;

	public HabboHelpersGuideSessionMessageComposer(final int senderId, final String message)
	{
		this.senderId = senderId;
		this.message = message;
	}

	@Override
	public final ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HabboHelpersGuideSessionMessage);
		response.appendString(this.message);
		response.appendInt(this.senderId);
		return response;
	}

}
