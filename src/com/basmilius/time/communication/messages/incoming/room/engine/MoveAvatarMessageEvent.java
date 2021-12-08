package com.basmilius.time.communication.messages.incoming.room.engine;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.MoveAvatar)
public class MoveAvatarMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int x = packet.readInt();
		final int y = packet.readInt();

		final Habbo habbo = connection.getHabbo();
		final Room room = habbo.getCurrentRoom();
		final RoomUnit roomUnit = habbo.getRoomUser();

		if (roomUnit == null)
			return;

		if (!roomUnit.getCanWalk())
			return;

		try
		{
			if (x == roomUnit.getX() && y == roomUnit.getY())
				return;
			else if (roomUnit.getIsTeleporting())
			{
				roomUnit.setPos(x, y);
				roomUnit.setHeight(room.getRoomData().getRoomModel().getNodeHeights()[x][y]);
				roomUnit.updateStatus();
				return;
			}

			if (room.getRoomUnitsHandler().getUnitAt(new Node(x, y)) != null && !room.getRoomData().getRoomModel().getIsDoor(x, y))
				return;

			roomUnit.setGoal(x, y);
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

}
