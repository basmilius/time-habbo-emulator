package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomUserComposer extends MessageComposer
{

	private final RoomUnit unit;

	public RoomUserComposer(final RoomUnit unit)
	{
		this.unit = unit;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomUsers);
		response.appendInt(1);
		this.unit.serialize(response);
		return response;
	}

}
