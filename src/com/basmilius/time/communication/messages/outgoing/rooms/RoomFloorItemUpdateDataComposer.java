package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomFloorItemUpdateDataComposer extends MessageComposer
{

	private final UserItem item;

	public RoomFloorItemUpdateDataComposer(final UserItem item)
	{
		this.item = item;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UpdateFloorItemData);
		response.appendString(this.item.getId());
		this.item.serializeLocalData(response, false, true);
		return response;
	}

}
