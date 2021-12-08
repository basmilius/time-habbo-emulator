package com.basmilius.time.habbohotel.rooms.pathfinding;

interface NodeFactory
{
	/**
	 * creates new instances of an implementation of the
	 * <code>AbstractNode</code>. In an implementation, it should return a new
	 * node with its position set to the given x and y values.
	 *
	 * @param x position on the x-axis
	 * @param y position on the y-axis
	 * @return AbstractNode
	 */
	AbstractNode createNode (int x, int y);

}