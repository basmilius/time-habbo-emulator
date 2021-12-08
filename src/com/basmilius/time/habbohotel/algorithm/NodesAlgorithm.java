package com.basmilius.time.habbohotel.algorithm;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;

public class NodesAlgorithm extends IAlgorithm<NodesAlgorithm.NodesAlgorithmParameter>
{

	public NodesAlgorithm(final NodesAlgorithmParameter node)
	{
		this.setObject(node);
	}

	public RoomUnit getNearestRoomUnit()
	{
		RoomUnit unit = null;
		final int maxSize = ((this.getObject().getRoom().getRoomData().getRoomModel().getMapSizeX() > this.getObject().getRoom().getRoomData().getRoomModel().getMapSizeY()) ? this.getObject().getRoom().getRoomData().getRoomModel().getMapSizeX() : this.getObject().getRoom().getRoomData().getRoomModel().getMapSizeY());

		for (int i = 0; i < maxSize; i++)
		{
			if (unit != null)
				break;

			for (int x = (this.getObject().getNode().getX() - i); x < (this.getObject().getNode().getX() + i); x++)
			{
				if (unit != null)
					break;

				for (int y = (this.getObject().getNode().getY() - i); y < (this.getObject().getNode().getY() + i); y++)
				{
					if (unit != null)
						break;

					unit = this.getObject().getRoom().getRoomUnitsHandler().getUnitAt(new Node(x, y));
				}
			}
		}

		return unit;
	}

	public static class NodesAlgorithmParameter
	{

		private final Node node;
		private final Room room;

		public NodesAlgorithmParameter(final Node node, final Room room)
		{
			this.node = node;
			this.room = room;
		}

		public Node getNode()
		{
			return this.node;
		}

		public Room getRoom()
		{
			return this.room;
		}
	}

}
