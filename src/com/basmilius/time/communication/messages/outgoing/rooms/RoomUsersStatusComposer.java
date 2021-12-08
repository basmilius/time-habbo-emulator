package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class RoomUsersStatusComposer extends MessageComposer
{

	private final List<RoomUnit> units;

	public RoomUsersStatusComposer(final List<RoomUnit> units)
	{
		this.units = units;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomStatuses);
		response.appendInt(this.units.size());
		for (final RoomUnit unit : this.units)
		{
			unit.serializeStatus(response);
		}
		return response;
	}

}
