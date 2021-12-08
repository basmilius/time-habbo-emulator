package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomSettingsRightsComposer extends MessageComposer
{

	private final Room room;

	public RoomSettingsRightsComposer(Room room)
	{
		this.room = room;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomSettingsRights);
		response.appendInt(this.room.getRoomData().getId());
		response.appendInt(this.room.getRoomData().getPermissions().getControllers().size());
		for (final Habbo controller : this.room.getRoomData().getPermissions().getControllers())
		{
			response.appendInt(controller.getId());
			response.appendString(controller.getUsername());
		}
		return response;
	}

}
