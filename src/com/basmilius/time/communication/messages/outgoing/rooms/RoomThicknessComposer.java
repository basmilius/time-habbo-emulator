package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomThicknessComposer extends MessageComposer
{

	private final boolean hideWall;
	private final int wallThickness;
	private final int floorThickness;

	public RoomThicknessComposer(Boolean hideWall, Integer wallThickness, Integer floorThickness)
	{
		this.hideWall = hideWall;
		this.wallThickness = wallThickness;
		this.floorThickness = floorThickness;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomThickness);
		response.appendBoolean(this.hideWall);
		response.appendInt(this.wallThickness);
		response.appendInt(this.floorThickness);
		return response;
	}

}
