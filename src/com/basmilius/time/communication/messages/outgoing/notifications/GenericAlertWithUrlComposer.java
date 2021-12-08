package com.basmilius.time.communication.messages.outgoing.notifications;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GenericAlertWithUrlComposer extends MessageComposer
{

	private final String message;
	private final String url;

	public GenericAlertWithUrlComposer(String message, String url)
	{
		this.message = message;
		this.url = url;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GenericAlertWithUrl);
		response.appendString(this.message);
		response.appendString(this.url);
		return response;
	}

}
