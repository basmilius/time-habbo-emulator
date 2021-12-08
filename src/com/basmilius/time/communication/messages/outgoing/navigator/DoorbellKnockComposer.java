package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class DoorbellKnockComposer extends MessageComposer
{

	private String userName;

	public DoorbellKnockComposer(String userName)
	{
		this.userName = userName;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.DoorbellKnock);
		response.appendString(this.userName);
		return response;
	}

}
