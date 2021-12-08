package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.habbohotel.enums.AvatarAction;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class UserActionComposer extends MessageComposer
{

	private final int unitId;
	private final AvatarAction action;

	public UserActionComposer(int unitId, AvatarAction action)
	{
		this.unitId = unitId;
		this.action = action;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomUserAction);
		response.appendInt(this.unitId);
		response.appendInt(this.action.asInt());
		return response;
	}

}
