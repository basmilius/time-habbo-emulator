package com.basmilius.time.communication.messages.outgoing.ambassador;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class AmbassadorMutedComposer extends MessageComposer
{

	private final int seconds;

	public AmbassadorMutedComposer(final int seconds)
	{
		this.seconds = seconds;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		return response.init(Outgoing.AmbassadorMuted).appendInt(this.seconds);
	}

}
