package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomRightsComposer extends MessageComposer
{

	private final int rightId;

	public RoomRightsComposer(Integer rightId)
	{
		this.rightId = rightId;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomRights);
		response.appendInt(this.rightId);
		return response;
	}

}
