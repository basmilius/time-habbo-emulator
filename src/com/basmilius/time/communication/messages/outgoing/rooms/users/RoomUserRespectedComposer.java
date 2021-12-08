package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomUserRespectedComposer extends MessageComposer
{

	private final int unitId;
	private final int totalPoints;

	public RoomUserRespectedComposer(Integer unitId, Integer totalPoints)
	{
		this.unitId = unitId;
		this.totalPoints = totalPoints;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UserRespected);
		response.appendInt(this.unitId);
		response.appendInt(this.totalPoints);
		return response;
	}

}
