package com.basmilius.time.communication.messages.outgoing.availability;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class LatencyPongComposer extends MessageComposer
{

	private final int id;

	public LatencyPongComposer(int id)
	{
		this.id = id;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.LatencyPong);
		response.appendInt(this.id);
		return response;
	}

}
