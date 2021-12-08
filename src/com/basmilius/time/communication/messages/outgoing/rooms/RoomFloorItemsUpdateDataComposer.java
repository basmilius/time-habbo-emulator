package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class RoomFloorItemsUpdateDataComposer extends MessageComposer
{

	private final List<? extends UserItem> items;

	public RoomFloorItemsUpdateDataComposer(final List<? extends UserItem> items)
	{
		this.items = items;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.UpdateFloorItemsData);
		response.appendInt(this.items.size());
		for (final UserItem item : this.items)
		{
			response.appendInt(item.getId());
			item.serializeData(response, false, true);
		}
		return response;
	}

}
