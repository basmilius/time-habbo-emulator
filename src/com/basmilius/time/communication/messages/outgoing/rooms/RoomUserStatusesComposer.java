package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomUserStatusesComposer extends MessageComposer
{

	private final Room room;

	public RoomUserStatusesComposer(final Room room)
	{
		this.room = room;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomStatuses);
		response.appendInt(this.room.getRoomUnitsHandler().getUnits().size());
		for (final RoomUnit unit : this.room.getRoomUnitsHandler().getUnits())
		{
			unit.serializeStatus(response);
			
			if (unit.getIsIdle())
				unit.setIdle(true, true);
		}
		return response;
	}

}
