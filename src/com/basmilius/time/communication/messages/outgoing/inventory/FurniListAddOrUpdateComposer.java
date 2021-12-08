package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FurniListAddOrUpdateComposer extends MessageComposer
{

	private final UserItem item;

	public FurniListAddOrUpdateComposer(final UserItem item)
	{
		this.item = item;
	}

	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.FurniListAddOrUpdate);
		this.item.serialize(response);
		return response;
	}

}
