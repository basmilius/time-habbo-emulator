package com.basmilius.time.communication.messages.outgoing.habbohelpers;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboHelpersToolCloseComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HabboHelpersToolClose);
		return response;
	}

}
