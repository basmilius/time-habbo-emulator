package com.basmilius.time.habbohotel.rooms.pathfinding;

public class Factory implements NodeFactory
{
	@Override
	public AbstractNode createNode(int x, int y)
	{
		return new Node(x, y);
	}

}