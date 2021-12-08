package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NewWallItemComposer extends MessageComposer
{

	private final Habbo owner;
	private final UserItem item;

	public NewWallItemComposer(Habbo owner, UserItem item)
	{
		this.owner = owner;
		this.item = item;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.NewWallItem);
		this.item.serializeWall(response);
		response.appendInt(this.owner.getId());
		response.appendString(this.owner.getUsername());
		return response;
	}

}
