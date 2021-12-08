package com.basmilius.time.communication.messages.incoming.inventory;

import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.habbohotel.collections.UserItemsList;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.task.HabboInventoryLoaderTask;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.inventory.InventoryFurnitureComposer;
import com.basmilius.time.util.ObjectUtils;

import java.util.List;

@Event(id = Incoming.RequestInventory)
public class RequestInventoryEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		connection.getHabbo().getInventory().loadUserItems();
		connection.getHabbo().getHabboCache().setInventoryOpened();

		HabboInventoryLoaderTask.newInstance(new OnHabboInventoryLoaderCompletedCallback(connection), connection.getHabbo());
	}
	
	private static class OnHabboInventoryLoaderCompletedCallback implements HabboInventoryLoaderTask.IOnHabboInventoryLoaderCompleted
	{

		private final SocketConnection connection;

		public OnHabboInventoryLoaderCompletedCallback(final SocketConnection connection)
		{
			this.connection = connection;
		}

		@Override
		public void OnHabboInventoryLoaderCompleted(final UserItemsList userItems)
		{
			if (userItems.size() == 0)
			{
				connection.send(new InventoryFurnitureComposer(userItems, 0, 1));
				return;
			}
			
			final List<List<UserItem>> chunkedOwnedItems = ObjectUtils.getChunkedList(userItems, 255);
			for (int i = 0; i < chunkedOwnedItems.size(); i++)
			{
				connection.send(new InventoryFurnitureComposer(chunkedOwnedItems.get(i), i, chunkedOwnedItems.size()));
			}
		}
		
	}
	
}
