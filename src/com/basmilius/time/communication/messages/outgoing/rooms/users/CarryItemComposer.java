package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CarryItemComposer extends MessageComposer
{

	private final RoomUnit user;
	private final int itemId;

	public CarryItemComposer(RoomUnit user, int itemId)
	{
		this.user = user;
		this.itemId = itemId;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.CarryItem);
		response.appendInt(this.user.getUnitId());
		response.appendInt(this.itemId);
		return response;
	}

}
