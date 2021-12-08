package com.basmilius.time.communication.messages.outgoing.general;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class LinkEventComposer extends MessageComposer
{

	private final String link;

	public LinkEventComposer(String link)
	{
		this.link = link;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.LinkEvent);
		response.appendString(this.link);
		return response;
	}

}
