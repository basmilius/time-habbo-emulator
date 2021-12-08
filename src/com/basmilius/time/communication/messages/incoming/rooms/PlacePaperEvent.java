package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.inventory.RemoveItemComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.SendPaperComposer;

@Event(id = Incoming.PlacePaper)
public class PlacePaperEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();

		if (!connection.hasHabbo())
			return;

		if (!connection.getHabbo().isInRoom())
			return;

		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);
		final Room room = connection.getHabbo().getCurrentRoom();
		
		if (item == null)
			return;
		
		if (!room.getRoomData().getPermissions().hasRights(connection.getHabbo()))
			return;

		String paperId = item.getCatalogItem().getName();
		paperId = paperId.replace("landscape_single_", "");
		paperId = paperId.replace("wallpaper_single_", "");
		paperId = paperId.replace("floor_single_", "");

		if (item.getCatalogItem().getName().contains("landscape"))
		{
			room.getRoomData().getRoomDecoration().setLandscape(Double.parseDouble(paperId));
			room.getRoomUnitsHandler().send(new SendPaperComposer("landscape", paperId));
		}
		else if (item.getCatalogItem().getName().contains("wallpaper"))
		{
			room.getRoomData().getRoomDecoration().setWallpaper(paperId);
			room.getRoomUnitsHandler().send(new SendPaperComposer("wallpaper", paperId));
		}
		else if (item.getCatalogItem().getName().contains("floor"))
		{
			room.getRoomData().getRoomDecoration().setFloorpaper(paperId);
			room.getRoomUnitsHandler().send(new SendPaperComposer("floor", paperId));
		}
		else
		{
			return;
		}

		item.setRoom(room);
		connection.send(new RemoveItemComposer(item.getId()));
	}

}
