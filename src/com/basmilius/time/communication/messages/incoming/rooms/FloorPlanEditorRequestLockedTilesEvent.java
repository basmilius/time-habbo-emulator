package com.basmilius.time.communication.messages.incoming.rooms;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.pathfinding.AffectedTile;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.FloorPlanEditorLockedTilesComposer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Event(id = Incoming.FloorPlanEditorRequestLockedTiles)
public class FloorPlanEditorRequestLockedTilesEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.getHabbo().isInRoom())
			return;

		Room room = connection.getHabbo().getCurrentRoom();

		if (room == null)
			return;

		if (!room.getRoomData().getPermissions().isOwner(connection.getHabbo()))
			return;

		List<Node> nodes = new ArrayList<>();

		for (final UserItem floorItem : room.getRoomObjectsHandler().getFloorItems())
		{
			nodes.add(floorItem.getNode());

			nodes.addAll(AffectedTile.getAffectedTilesAt(floorItem.getLength(), floorItem.getWidth(), floorItem.getX(), floorItem.getY(), floorItem.getRot()).stream().map(tile -> new Node(tile.X, tile.Y)).collect(Collectors.toList()));
		}

		connection.send(new FloorPlanEditorLockedTilesComposer(nodes));
	}

}
