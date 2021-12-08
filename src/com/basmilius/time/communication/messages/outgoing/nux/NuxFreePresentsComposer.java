package com.basmilius.time.communication.messages.outgoing.nux;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NuxFreePresentsComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		/**
		 * ToDo: STRUCTURE:
		 *       [I:I I [I:S [I:S S]]]
		 */
		response.init(Outgoing.NuxFreePresents);
		response.appendInt(0);
		return response;
	}

}
