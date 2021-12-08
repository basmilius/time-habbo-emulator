package com.basmilius.time.communication.messages.outgoing.rooms.trading;

import com.basmilius.time.habbohotel.collections.UserItemsList;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.Map;

public class TradingItemsComposer extends MessageComposer
{

	private final Map<Habbo, UserItemsList> items;

	public TradingItemsComposer(final Map<Habbo, UserItemsList> items)
	{
		this.items = items;
	}

	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.TradingItems);
		for (final Map.Entry<Habbo, UserItemsList> entry : this.items.entrySet())
		{
			response.appendInt(entry.getKey().getId());
			response.appendInt(entry.getValue().size());
			for (final UserItem item : entry.getValue())
			{
				response.appendInt(item.getId());
				response.appendString(item.getBaseItem().getType());
				response.appendInt(item.getId());
				response.appendInt(item.getBaseItem().getSpriteId());
				response.appendInt(0);
				response.appendBoolean(true);
				item.serializeData(response, false, true);
				response.appendInt(0);
				response.appendInt(0);
				response.appendInt(0);
				if (item.getBaseItem().getType().equalsIgnoreCase("s"))
				{
					response.appendInt(0);
				}
			}
		}
		return response;
	}

}
