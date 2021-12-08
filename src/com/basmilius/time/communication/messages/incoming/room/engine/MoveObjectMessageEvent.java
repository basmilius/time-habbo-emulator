package com.basmilius.time.communication.messages.incoming.room.engine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomFloorItemUpdateComposer;

@Event(id = Incoming.MoveObject)
public class MoveObjectMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int furniId = packet.readInt();
		final Node node = packet.readNodeVector2();
		final int rotation = packet.readInt();

		if (!connection.getHabbo().isInRoom())
			return;

		final Room room = connection.getHabbo().getCurrentRoom();
		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(furniId);

		if (item == null)
			return;

		if (!room.getRoomData().getPermissions().hasRights(connection.getHabbo()))
		{
			room.getRoomUnitsHandler().send(new RoomFloorItemUpdateComposer(item));
		}

		final int oldX = item.getX();
		final int oldY = item.getY();
		final int oldR = item.getRot();
		final int oldLength = item.getLength();
		final int oldWidth = item.getWidth();

		final double height = room.getRoomObjectsHandler().getStackHeight(node, item);
		final boolean canPlace = room.getRoomObjectsHandler().canPlace(node, item);

		if (!canPlace)
		{
			room.getRoomUnitsHandler().send(new RoomFloorItemUpdateComposer(item));
			return;
		}

		item.setX(node.getX());
		item.setY(node.getY());
		item.setZ(height);
		item.setRot(rotation);
		item.updateAllDataInRoom();

		room.getRoomObjectsHandler().updateGameMap();

		room.getRoomInteractions().updateTile(oldX, oldY, oldR, oldWidth, oldLength);
		room.getRoomInteractions().updateTile(item.getX(), item.getY(), item.getRot(), item.getWidth(), item.getLength());
	}

}
