package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class DoorbellRemoveUserComposer extends MessageComposer
{

	private String userName;

	public DoorbellRemoveUserComposer(String userName)
	{
		this.userName = userName;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.DoorbellRemoveUser);
		response.appendString(this.userName);
		return response;
	}

}
