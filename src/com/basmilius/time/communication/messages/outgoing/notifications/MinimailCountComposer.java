package com.basmilius.time.communication.messages.outgoing.notifications;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class MinimailCountComposer extends MessageComposer
{

	private final int count;

	public MinimailCountComposer(Integer count)
	{
		this.count = count;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.MinimailCount);
		response.appendInt(this.count);
		return response;
	}

}
