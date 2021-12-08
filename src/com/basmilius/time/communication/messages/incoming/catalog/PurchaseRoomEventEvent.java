package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomEvent;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.PurchaseOKComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.RefreshInventoryComposer;
import com.basmilius.time.communication.messages.outgoing.navigator.MyRoomsComposer;
import com.basmilius.time.util.TimeUtils;

@Event(id = Incoming.PurchaseRoomEvent)
public class PurchaseRoomEventEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int pageId = packet.readInt();
		final int itemId = packet.readInt();
		final int roomId = packet.readInt();
		final String eventName = packet.readString();
		packet.readBoolean();
		final String eventDescription = packet.readString();

		final CatalogueItem item = Bootstrap.getEngine().getGame().getCatalogueManager().getItem(itemId);
		
		if (item == null)
			return;

		if (item.getName().equalsIgnoreCase("room_ad_plus_badge") && item.getPageId() == pageId)
		{
			final Room room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(roomId);
			
			if (room == null)
				return;

			if (!room.getRoomData().getPermissions().hasRights(connection.getHabbo()))
				return;

            /*
             * TODO: Add RADZZ badge to user that hasn't that badge already.
             * TODO: AddItemComposer, tab: 4, badge.
             */

			if (room.getRoomData().getRoomEvent() == null)
			{
				RoomEvent event = new RoomEvent(connection.getHabbo(), room, TimeUtils.getUnixTimestamp(), (TimeUtils.getUnixTimestamp() + 7200), eventName, eventDescription);
				event.startEvent();
			}
			else
			{
				room.getRoomData().getRoomEvent().extend(7200);
			}

			connection.send(new PurchaseOKComposer(item));
			connection.send(new RefreshInventoryComposer());
			connection.getHabbo().getSettings().getCurrencies().updateBalance(item.getCurrencyId(), -item.getCostsCurrency());
			connection.send(new MyRoomsComposer(connection.getHabbo()));
		}
	}

}
