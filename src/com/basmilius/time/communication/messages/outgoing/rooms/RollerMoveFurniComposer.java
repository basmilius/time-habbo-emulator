package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RollerMoveFurniComposer extends MessageComposer
{

	private final UserItem item;
	private final Node nextCoord;
	private final UserItem roller;
	private final double newHeight;

	public RollerMoveFurniComposer(UserItem item, Node nextCoord, UserItem roller, Double newHeight)
	{
		this.item = item;
		this.nextCoord = nextCoord;
		this.roller = roller;
		this.newHeight = newHeight;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RollerMove);
		response.appendInt(this.item.getX());
		response.appendInt(this.item.getY());
		response.appendInt(this.nextCoord.getX());
		response.appendInt(this.nextCoord.getY());
		response.appendInt(1);
		response.appendInt(this.item.getId());
		response.appendString(Double.toString(this.item.getZ()));
		response.appendString(Double.toString(this.newHeight));
		response.appendInt((this.roller != null) ? this.roller.getId() : -1);
		return response;
	}

}
