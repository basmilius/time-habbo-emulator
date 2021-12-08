package com.basmilius.time.habbohotel.rooms.pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Map<T extends AbstractNode>
{

	/**
	 * weather or not it is possible to walk diagonally on the map in general.
	 */
	private static final boolean CANMOVEDIAGONALY = true;

	/**
	 * holds nodes. first dim represents x-, second y-axis.
	 */
	private final T[][] nodes;

	/**
	 * width + 1 is size of first dimension of nodes.
	 */
	private final int width;
	/**
	 * higth + 1 is size of second dimension of nodes.
	 */
	private final int higth;

	/**
	 * a Factory to create instances of specified nodes.
	 */
	private final NodeFactory nodeFactory;
	/**
	 * list containing nodes not visited but adjacent to visited nodes.
	 */
	private List<T> openList;
	/**
	 * list containing nodes already visited/taken care of.
	 */
	private List<T> closedList;

	/**
	 * constructs a squared map with given width and hight.
	 * <p>
	 * The nodes will be instanciated througth the given nodeFactory.
	 *
	 * @param width
	 * @param higth
	 * @param nodeFactory
	 */
	@SuppressWarnings("unchecked")
	public Map(int width, int higth, NodeFactory nodeFactory)
	{
		// TODO check parameters. width and higth should be > 0.
		this.nodeFactory = nodeFactory;
		nodes = (T[][]) new AbstractNode[width][higth];
		this.width = width - 1;
		this.higth = higth - 1;
		initEmptyNodes();
	}

	/**
	 * initializes all nodes. Their coordinates will be set correctly.
	 */
	@SuppressWarnings("unchecked")
	private void initEmptyNodes()
	{
		for (int i = 0; i <= width; i++)
		{
			for (int j = 0; j <= higth; j++)
			{
				nodes[i][j] = (T) nodeFactory.createNode(i, j);
			}
		}
	}

	/**
	 * gets nodes walkable field at given coordinates to given value.
	 * <p>
	 * x/y must be bigger or equal to 0 and smaller or equal to width/hight.
	 *
	 * @param x
	 * @param y
	 */
	public boolean getWalkable(int x, int y)
	{
		return nodes.length <= x && nodes[x].length <= y || nodes[x][y].isWalkable();

	}

	/**
	 * sets nodes walkable field at given coordinates to given value.
	 * <p>
	 * x/y must be bigger or equal to 0 and smaller or equal to width/hight.
	 *
	 * @param x
	 * @param y
	 * @param bool
	 */
	public void setWalkable(int x, int y, boolean bool)
	{
		if (nodes.length <= x && nodes[x].length <= y)
			return;

		nodes[x][y].setWalkable(bool);
	}

	/**
	 * returns node at given coordinates.
	 * <p>
	 * x/y must be bigger or equal to 0 and smaller or equal to width/hight.
	 *
	 * @param x
	 * @param y
	 * @return node
	 */
	public final T getNode(int x, int y)
	{
		// TODO check parameter.
		return nodes[x][y];
	}

	public final List<T> getNodes()
	{
		List<T> nodes = new ArrayList<>();

		for (int x = 0; x < this.nodes.length; x++)
		{
			for (int y = 0; y < this.nodes[x].length; y++)
			{
				nodes.add(this.getNode(x, y));
			}
		}

		return nodes;
	}

	/* Variables and methodes for path finding */

	// variables needed for path finding

	/**
	 * prints map to sto. Feel free to override this method.
	 * <p>
	 * a player will be represented as "o", an unwakable terrain as "#".
	 * Movement penalty will not be displayed.
	 */
	public void drawMap()
	{
		for (int i = 0; i <= width; i++)
		{
			print(" _"); // boarder of map
		}
		print("\n");

		for (int j = higth; j >= 0; j--)
		{
			print("|"); // boarder of map
			for (int i = 0; i <= width; i++)
			{
				if (nodes[i][j].isWalkable())
				{
					print("  ");
				}
				else
				{
					print(" #"); // draw unwakable
				}
			}
			print("|\n"); // boarder of map
		}

		for (int i = 0; i <= width; i++)
		{
			print(" _"); // boarder of map
		}
	}

	/**
	 * prints something to sto.
	 */
	private void print(String s)
	{
		System.out.print(s);
	}

	public final Queue<T> findPath(int oldX, int oldY, int newX, int newY)
	{
		return this.findPath(oldX, oldY, newX, newY, true);
	}

	/**
	 * finds an allowed path from start to goal coordinates on this map.
	 * <p>
	 * This method uses the A* algorithm. The hCosts value is calculated in the
	 * given Node implementation.
	 * <p>
	 * This method will return a LinkedList containing the start node at the
	 * beginning followed by the calculated shortest allowed path ending with
	 * the end node.
	 * <p>
	 * If no allowed path exists, an empty list will be returned.
	 * <p>
	 * <p>
	 * x/y must be bigger or equal to 0 and smaller or equal to width/hight.
	 *
	 * @param oldX
	 * @param oldY
	 * @param newX
	 * @param newY
	 * @return
	 */
	public final Queue<T> findPath(int oldX, int oldY, int newX, int newY, boolean shortest)
	{
		if (oldX == newX && oldY == newY)
			return new LinkedList<>();

		openList = new LinkedList<>();
		closedList = new LinkedList<>();

		openList.add(nodes[oldX][oldY]); // add starting node to open list

		boolean done = false;
		T current;
		while (!done)
		{
			current = ((shortest) ? lowestFInOpen() : highestFInOpen()); // get node with lowest fCosts from
			// openList
			closedList.add(current); // add current node to closed list
			openList.remove(current); // delete current node from open list

			if ((current.getX() == newX) && (current.getY() == newY))
			{ // found
				// goal
				return calcPath(nodes[oldX][oldY], current);
			}

			// for all adjacent nodes:
			List<T> adjacentNodes = getAdjacent(current);
			for (T currentAdj : adjacentNodes)
			{
				if (!openList.contains(currentAdj))
				{
					currentAdj.setPrevious(current);
					currentAdj.sethCosts(nodes[newX][newY]);
					currentAdj.setgCosts(current);
					openList.add(currentAdj);
				}
				else
				{
					if (currentAdj.getgCosts() > currentAdj.calculategCosts(current))
					{
						currentAdj.setPrevious(current);
						currentAdj.setgCosts(current);
					}
				}
			}

			if (openList.isEmpty())
			{ // no path exists
				return new LinkedList<>(); // return empty list
			}
		}
		return null; // unreachable
	}

	/**
	 * calculates the found path between two points according to their given
	 * <code>previousNode</code> field.
	 *
	 * @param start
	 * @param goal
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Queue<T> calcPath(T start, T goal)
	{
		// TODO if invalid nodes are given (eg cannot find from
		// goal to start, this method will result in an infinite loop!)
		LinkedList<T> path = new LinkedList<>();

		T curr = goal;
		boolean done = false;
		while (!done)
		{
			if (curr == null)
				continue;

			path.addFirst(curr);
			curr = ((T) curr.getPrevious());

			if (curr != null && start != null && curr.equals(start))
			{
				done = true;
			}
		}
		return path;
	}

	/**
	 * returns the node with the lowest fCosts.
	 *
	 * @return
	 */
	private T lowestFInOpen()
	{
		// TODO currently, this is done by going through the whole openList!
		T cheapest = openList.get(0);
		for (T anOpenList : openList)
		{
			if (anOpenList.getfCosts() < cheapest.getfCosts())
			{
				cheapest = anOpenList;
			}
		}
		return cheapest;
	}

	/**
	 * returns the node with the lowest fCosts.
	 *
	 * @return
	 */
	private T highestFInOpen()
	{
		// TODO currently, this is done by going through the whole openList!
		T cheapest = openList.get(0);
		for (T anOpenList : openList)
		{
			if (anOpenList.getfCosts() > cheapest.getfCosts())
			{
				cheapest = anOpenList;
			}
		}
		return cheapest;
	}

	/**
	 * returns a LinkedList with nodes adjacent to the given node. if those
	 * exist, are walkable and are not already in the closedList!
	 */
	private List<T> getAdjacent(T node)
	{
		// TODO make loop
		int x = node.getX();
		int y = node.getY();
		List<T> adj = new LinkedList<>();

		T temp;
		if (x > 0)
		{
			temp = this.getNode((x - 1), y);
			if (temp.isWalkable() && !closedList.contains(temp))
			{
				temp.setIsDiagonaly(false);
				adj.add(temp);
			}
		}

		if (x < width)
		{
			temp = this.getNode((x + 1), y);
			if (temp.isWalkable() && !closedList.contains(temp))
			{
				temp.setIsDiagonaly(false);
				adj.add(temp);
			}
		}

		if (y > 0)
		{
			temp = this.getNode(x, (y - 1));
			if (temp.isWalkable() && !closedList.contains(temp))
			{
				temp.setIsDiagonaly(false);
				adj.add(temp);
			}
		}

		if (y < higth)
		{
			temp = this.getNode(x, (y + 1));
			if (temp.isWalkable() && !closedList.contains(temp))
			{
				temp.setIsDiagonaly(false);
				adj.add(temp);
			}
		}

		// add nodes that are diagonaly adjacent too:
		if (CANMOVEDIAGONALY)
		{
			if (x < width && y < higth)
			{
				temp = this.getNode((x + 1), (y + 1));
				if (temp.isWalkable() && !closedList.contains(temp))
				{
					temp.setIsDiagonaly(true);
					adj.add(temp);
				}
			}

			if (x > 0 && y > 0)
			{
				temp = this.getNode((x - 1), (y - 1));
				if (temp.isWalkable() && !closedList.contains(temp))
				{
					temp.setIsDiagonaly(true);
					adj.add(temp);
				}
			}

			if (x > 0 && y < higth)
			{
				temp = this.getNode((x - 1), (y + 1));
				if (temp.isWalkable() && !closedList.contains(temp))
				{
					temp.setIsDiagonaly(true);
					adj.add(temp);
				}
			}

			if (x < width && y > 0)
			{
				temp = this.getNode((x + 1), (y - 1));
				if (temp.isWalkable() && !closedList.contains(temp))
				{
					temp.setIsDiagonaly(true);
					adj.add(temp);
				}
			}
		}
		return adj;
	}

	@Override
	public void finalize() throws Throwable
	{
	    /*
		 * if (closedList != null) this.closedList.clear();
		 */
		/* this.nodes = null; */
		/*
		 * if (openList != null) this.openList.clear();
		 */
		super.finalize();
	}
}