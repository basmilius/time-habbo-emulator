package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomFloorItemUpdateComposer extends MessageComposer
{

	private final UserItem userItem;

	public RoomFloorItemUpdateComposer(final UserItem userItem)
	{
		this.userItem = userItem;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UpdateFloorItem);
		this.userItem.serializeFloor(response);
		return response;
	}

}
