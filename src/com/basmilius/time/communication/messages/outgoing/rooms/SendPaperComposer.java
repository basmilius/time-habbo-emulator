package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class SendPaperComposer extends MessageComposer
{

	private final String type;
	private final String data;

	public SendPaperComposer(String type, String data)
	{
		this.type = type;
		this.data = data;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.SendPaper);
		response.appendString(this.type);
		response.appendString(this.data);
		return response;
	}

}
