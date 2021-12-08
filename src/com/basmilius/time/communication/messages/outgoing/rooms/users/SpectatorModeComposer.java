package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class SpectatorModeComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.SpectatorMode);
		return response;
	}

}
