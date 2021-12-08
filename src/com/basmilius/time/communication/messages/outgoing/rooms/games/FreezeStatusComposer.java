package com.basmilius.time.communication.messages.outgoing.rooms.games;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FreezeStatusComposer extends MessageComposer
{

	private final boolean isPlaying;

	public FreezeStatusComposer(final boolean isPlaying)
	{
		this.isPlaying = isPlaying;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		return response.init(Outgoing.FreezeStatus).appendBoolean(this.isPlaying);
	}

}
