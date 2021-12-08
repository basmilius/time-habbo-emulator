package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomUserIdleComposer extends MessageComposer
{

	private final int unitId;
	private final boolean isIdle;

	public RoomUserIdleComposer(Integer unitId, Boolean isIdle)
	{
		this.unitId = unitId;
		this.isIdle = isIdle;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.Idle);
		response.appendInt(this.unitId);
		response.appendBoolean(this.isIdle);
		return response;
	}

}
