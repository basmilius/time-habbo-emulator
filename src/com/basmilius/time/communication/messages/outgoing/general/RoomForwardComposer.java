package com.basmilius.time.communication.messages.outgoing.general;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomForwardComposer extends MessageComposer
{

	private final int roomId;

	public RoomForwardComposer(final int roomId)
	{
		this.roomId = roomId;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomForward);
		response.appendInt(this.roomId);
		return response;
	}

}
