package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CanCreateRoomComposer extends MessageComposer
{

	private final int _userRoomCount;

	public CanCreateRoomComposer(Integer _userRoomCount)
	{
		this._userRoomCount = _userRoomCount;
	}

	@Override
	public ServerMessage compose()
	{
		final int maxRooms = Bootstrap.getEngine().getConfig().getInt("hotel.max.rooms.per.user", 15);
		response.init(Outgoing.CanCreateRoom);
		response.appendInt((maxRooms <= this._userRoomCount) ? 1 : 0);
		response.appendInt(maxRooms);
		return response;
	}

}
