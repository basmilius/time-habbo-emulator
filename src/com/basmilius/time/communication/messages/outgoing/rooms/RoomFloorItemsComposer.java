package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.ArrayList;
import java.util.List;

public class RoomFloorItemsComposer extends MessageComposer
{

	private final List<UserItem> items;
	private final List<Habbo> owners;

	public RoomFloorItemsComposer(final Room room)
	{
		this.items = room.getRoomObjectsHandler().getFloorItems();
		this.owners = new ArrayList<>();

		for (final UserItem item : this.items)
		{
			if (this.owners.contains(item.getHabbo()))
				continue;
			this.owners.add(item.getHabbo());
		}
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FloorItems);
		response.appendInt(this.owners.size());
		for (final Habbo owner : this.owners)
		{
			response.appendInt(owner.getId());
			response.appendString(owner.getUsername());
		}

		response.appendInt(this.items.size());
		for (UserItem item : this.items)
		{
			item.serializeFloor(response);
		}

		return response;
	}

}
