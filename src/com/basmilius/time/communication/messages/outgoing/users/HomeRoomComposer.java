package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HomeRoomComposer extends MessageComposer
{

	private final int homeRoom;
	private final int startRoom;

	public HomeRoomComposer(Integer homeRoom, Integer startRoom)
	{
		this.homeRoom = homeRoom;
		this.startRoom = startRoom;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.HomeRoom);
		response.appendInt(this.homeRoom);
		response.appendInt(this.startRoom);
		return response;
	}

}
