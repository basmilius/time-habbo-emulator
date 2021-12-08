package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomTile;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HeightmapComposer extends MessageComposer
{

	private final Room room;

	public HeightmapComposer(final Room room)
	{
		this.room = room;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.Heightmap);
		response.appendInt(this.room.getRoomData().getRoomModel().getMapSizeX());
		response.appendInt(this.room.getRoomData().getRoomModel().getMapSize());
		for (int y = 0; y < this.room.getRoomData().getRoomModel().getMapSizeY(); y++)
		{
			for (int x = 0; x < this.room.getRoomData().getRoomModel().getMapSizeX(); x++)
			{
				if (this.room.getRoomData().getRoomModel().getSquares()[x][y] == RoomTile.CLOSED)
				{
					response.appendShort(65535);
				}
				else
				{
					response.appendShort(((int) this.room.getRoomObjectsHandler().getStackHeight(new Node(x, y), null, true)));
				}
			}
		}
		return response;
	}

}
