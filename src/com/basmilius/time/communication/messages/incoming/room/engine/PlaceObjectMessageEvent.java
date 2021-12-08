package com.basmilius.time.communication.messages.incoming.room.engine;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.inventory.RemoveItemComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.NewFloorItemComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.NewWallItemComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.UpdateHeightmapComposer;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.util.ObjectUtils;

@Event(id = Incoming.PlaceObject)
public class PlaceObjectMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String data = packet.readString();

		if (!connection.getHabbo().isInRoom())
			return;

		if (data.isEmpty())
			return;

		final Room room = connection.getHabbo().getCurrentRoom();

		if (!room.getRoomData().getPermissions().hasRights(connection.getHabbo()))
			return;

		final String[] furniData = data.split(" ");
		final UserItem item = connection.getHabbo().getInventory().getItem(Integer.parseInt(furniData[0]));

		if (item == null)
			return;

		if (item.getIsWallItem())
		{
			final String wallPos = furniData[1] + " " + furniData[2] + " " + furniData[3];
			item.setRoom(room);
			item.setWallPos(wallPos);

			room.getRoomObjectsHandler().getWallItems().add(item);
			room.getRoomObjectsHandler().updateGameMap();
			room.getRoomUnitsHandler().send(new NewWallItemComposer(connection.getHabbo(), item));
		}
		else
		{
			if (!(ObjectUtils.isNumeric(furniData[0]) && ObjectUtils.isNumeric(furniData[1]) && ObjectUtils.isNumeric(furniData[2]) && ObjectUtils.isNumeric(furniData[3])))
				return;

			final int x = Integer.parseInt(furniData[1]);
			final int y = Integer.parseInt(furniData[2]);
			final int r = Integer.parseInt(furniData[3]);
			final double h = room.getRoomObjectsHandler().getStackHeight(new Node(x, y), item);
			final boolean canPlace = room.getRoomObjectsHandler().canPlace(new Node(x, y), item);

			if (!canPlace)
				return;

			item.setRoom(room);
			item.setX(x);
			item.setY(y);
			item.setRot(r);
			item.setZ(h);

			room.getRoomObjectsHandler().getFloorItems().add(item);
			room.getRoomObjectsHandler().updateGameMap();
			room.getRoomUnitsHandler().send(new NewFloorItemComposer(connection.getHabbo(), item));
			room.getRoomUnitsHandler().send(new UpdateHeightmapComposer(item.getNodes()));
		}
		connection.send(new RemoveItemComposer(item.getId()));
	}

}
