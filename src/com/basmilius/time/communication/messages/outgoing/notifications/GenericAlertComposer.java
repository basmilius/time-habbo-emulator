package com.basmilius.time.communication.messages.outgoing.notifications;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GenericAlertComposer extends MessageComposer
{

	private final String message;

	public GenericAlertComposer(String message)
	{
		this.message = message;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GenericAlert);
		response.appendString(this.message);
		return response;
	}

}
