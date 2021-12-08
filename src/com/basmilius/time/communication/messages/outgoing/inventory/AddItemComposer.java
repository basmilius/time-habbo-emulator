package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.habbohotel.collections.UserItemsList;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddItemComposer extends MessageComposer
{

	private final Map<Integer, UserItemsList> items;

	public AddItemComposer(UserItem item)
	{
		this.items = new HashMap<>();
		this.items.put(item.getBaseItem().getId(), new UserItemsList(new ArrayList<>()));
		this.items.get(item.getBaseItem().getId()).add(item);
	}

	public AddItemComposer(Map<Integer, UserItemsList> items)
	{
		this.items = items;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.AddItem);
		response.appendInt(this.items.size());
		for (final UserItemsList items : this.items.values())
		{
			response.appendInt(1); // Tab ID (TO DO: Rentable items have Tab ID 2)
			response.appendInt(items.size());
			for (final UserItem item : items)
			{
				response.appendInt(item.getId());
			}
		}
		return response;
	}

}
