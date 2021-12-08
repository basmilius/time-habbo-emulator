package com.basmilius.time.habbohotel.rooms.pathfinding;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;

import java.util.LinkedList;
import java.util.Queue;

public class Pathfinder
{

	private Room room;
	private RoomUnit roomUnit;

	public Pathfinder(Room room, RoomUnit roomUnit)
	{
		this.room = room;
		this.roomUnit = roomUnit;
	}

	public Queue<Node> findPath()
	{
		return this.findPath(this.roomUnit.getGoal().getX(), this.roomUnit.getGoal().getY());
	}

	public Queue<Node> findPath(int goalX, int goalY)
	{
		final Map<Node> gameMap = this.room.getGameMap(new Node(goalX, goalY));
		Queue<Node> nodes = null;

		if (gameMap == null)
			return null;

		if (this.roomUnit != null)
		{
			if (!gameMap.getNode(goalX, goalY).isWalkable())
			{
				gameMap.setWalkable(goalX, goalY, this.roomUnit.getCanWalkOnGoal());
			}

			nodes = gameMap.findPath(this.roomUnit.getX(), this.roomUnit.getY(), goalX, goalY);
		}

		if (nodes == null)
			return new LinkedList<>();

		return nodes;
	}

	public Queue<Node> findPath(final UserItem item, int goalX, int goalY, boolean allowCollision)
	{
		Map<Node> gameMap = this.room.getGameMap(new Node(goalX, goalY));
		Queue<Node> nodes = null;

		if (gameMap == null)
			return null;

		if (item != null)
		{
			if (!gameMap.getNode(goalX, goalY).isWalkable())
			{
				gameMap.setWalkable(goalX, goalY, allowCollision);
			}

			for (final UserItem fItem : item.getRoom().getRoomObjectsHandler().getFloorItems())
			{
				if (!fItem.getIsWalkable() || fItem.getLength() > 1 || fItem.getWidth() > 1)
					continue;

				gameMap.setWalkable(fItem.getX(), fItem.getY(), fItem.getCanStack());
			}

			nodes = gameMap.findPath(item.getX(), item.getY(), goalX, goalY);
		}

		if (nodes == null)
			return new LinkedList<>();

		return nodes;
	}

	public int DistanceBetween(Node node)
	{
		return this.DistanceBetween(node.getX(), node.getY());
	}

	public int DistanceBetween(int X, int Y)
	{
		Queue<Node> nodes = this.findPath(X, Y);

		if (nodes == null)
			return 0;

		return nodes.size();
	}

}
