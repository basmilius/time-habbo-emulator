package com.basmilius.time.communication.messages.outgoing.handshake;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class DisconnectReasonMessageComposer extends MessageComposer
{

	private final int reason;

	public DisconnectReasonMessageComposer (int reason)
	{
		this.reason = reason;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.CloseClient);
		response.appendInt(this.reason);
		return response;
	}

}
