package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RemoveRoomUserComposer extends MessageComposer
{

	private final int unitId;

	public RemoveRoomUserComposer(final int unitId)
	{
		this.unitId = unitId;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RemoveRoomUser);
		response.appendString(this.unitId);
		return response;
	}

}
