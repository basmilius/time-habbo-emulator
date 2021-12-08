package com.basmilius.time.communication.messages.outgoing.wired;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class WiredUpdateFailedComposer extends MessageComposer
{

	private final String info;

	public WiredUpdateFailedComposer(String info)
	{
		this.info = info;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.WiredUpdateFailed);
		response.appendString(this.info);
		return response;
	}

}
