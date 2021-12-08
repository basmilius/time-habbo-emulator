package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RollerMoveComposer extends MessageComposer
{

	private final RoomUnit roomUnit;
	private final Node nextCoord;
	private final UserItem roller;
	private final double newHeight;

	public RollerMoveComposer(RoomUnit roomUnit, Node nextCoord, UserItem roller, Double newHeight)
	{
		this.roomUnit = roomUnit;
		this.nextCoord = nextCoord;
		this.roller = roller;
		this.newHeight = newHeight;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RollerMove);
		response.appendInt(this.roomUnit.getX());
		response.appendInt(this.roomUnit.getY());
		response.appendInt(this.nextCoord.getX());
		response.appendInt(this.nextCoord.getY());
		response.appendInt(0);
		response.appendInt(this.roller.getId());
		response.appendInt(2);
		response.appendInt(this.roomUnit.getUnitId());
		response.appendString(Double.toString(this.roomUnit.getZ()));
		response.appendString(Double.toString(this.newHeight));
		return response;
	}

}
