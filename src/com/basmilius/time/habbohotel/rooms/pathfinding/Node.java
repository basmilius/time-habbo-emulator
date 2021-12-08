package com.basmilius.time.habbohotel.rooms.pathfinding;

public class Node extends AbstractNode
{

	private double z;

	public Node(int xPosition, int yPosition)
	{
		super(xPosition, yPosition);

		this.z = 0;
	}

	public Node(int x, int y, double z)
	{
		super(x, y);

		this.z = z;
	}

	public int getX()
	{
		return super.getX();
	}

	public int getY()
	{
		return super.getY();
	}

	public double getZ()
	{
		return this.z;
	}

	@Override
	public void sethCosts(AbstractNode endNode)
	{
		this.sethCosts((absolute(this.getX() - endNode.getX()) + absolute(this.getY() - endNode.getY())) * BASICMOVEMENTCOST);
	}

	private int absolute(int a)
	{
		return a > 0 ? a : -a;
	}

	public boolean equals(Node node)
	{
		return (this.getX() == node.getX() && this.getY() == node.getY());
	}

}