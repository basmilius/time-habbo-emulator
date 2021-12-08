package com.basmilius.time.communication.messages.outgoing.poll;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PollErrorComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.PollError);
		return response;
	}

}
