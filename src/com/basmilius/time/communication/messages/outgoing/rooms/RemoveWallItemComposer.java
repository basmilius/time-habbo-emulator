package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RemoveWallItemComposer extends MessageComposer
{

	private final UserItem item;
	private final Habbo ownerHabbo;

	public RemoveWallItemComposer(UserItem item, Habbo ownerHabbo)
	{
		this.item = item;
		this.ownerHabbo = ownerHabbo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RemoveWallItem);
		response.appendString(this.item.getId());
		response.appendInt(this.ownerHabbo.getId());
		return response;
	}

}
