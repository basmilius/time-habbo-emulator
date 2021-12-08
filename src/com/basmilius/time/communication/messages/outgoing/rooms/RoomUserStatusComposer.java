package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomUserStatusComposer extends MessageComposer
{

	private final RoomUnit _unit;

	public RoomUserStatusComposer(RoomUnit unit)
	{
		this._unit = unit;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomStatuses);
		response.appendInt(1);
		this._unit.serializeStatus(response);
		return response;
	}

}
