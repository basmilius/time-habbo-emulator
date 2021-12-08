package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class InventoryFurnitureComposer extends MessageComposer
{

	private final List<UserItem> items;
	private final int page;
	private final int pages;

	public InventoryFurnitureComposer(final List<UserItem> items, final int page, final int pages)
	{
		this.items = items;
		this.page = page;
		this.pages = pages;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FloorAndWallItems);
		response.appendInt(this.pages);
		response.appendInt(this.page);
		response.appendInt(items.size());
		for (UserItem item : items)
		{
			item.serialize(response);
		}
		return response;
	}

}
