package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class DisconnectRoomComposer extends MessageComposer
{

	private final String userName;

	public DisconnectRoomComposer()
	{
		this.userName = null;
	}

	public DisconnectRoomComposer(final String userName)
	{
		this.userName = userName;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.DisconnectRoom);
		if (!(this.userName == null || this.userName.isEmpty()))
		{
			response.appendString(userName);
		}
		return response;
	}

}
