package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class CreateGuildPanelRoomsComposer extends MessageComposer
{

	private final List<Room> _rooms;

	public CreateGuildPanelRoomsComposer(List<Room> _rooms)
	{
		this._rooms = _rooms;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.CreateGuildPanelRooms);
		response.appendInt(10); // ?
		response.appendInt(this._rooms.size());
		for (Room room : this._rooms)
		{
			response.appendInt(room.getRoomData().getId());
			response.appendString(room.getRoomData().getRoomName());
			response.appendBoolean(false); // Room has user rights
		}
		response.appendInt(5); // Preinstalled badge (while loop I I I)
		response.appendInt(10);
		response.appendInt(3);
		response.appendInt(4);
		response.appendInt(25);
		response.appendInt(17);
		response.appendInt(5);
		response.appendInt(25);
		response.appendInt(17);
		response.appendInt(3);
		response.appendInt(29);
		response.appendInt(11);
		response.appendInt(4);
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(0);
		return response;
	}

}
