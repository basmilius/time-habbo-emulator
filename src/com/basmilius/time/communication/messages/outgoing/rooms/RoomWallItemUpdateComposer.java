package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomWallItemUpdateComposer extends MessageComposer
{

	private final UserItem userItem;

	public RoomWallItemUpdateComposer(final UserItem userItem)
	{
		this.userItem = userItem;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UpdateWallItem);
		this.userItem.serializeWall(response);
		return response;
	}

}
