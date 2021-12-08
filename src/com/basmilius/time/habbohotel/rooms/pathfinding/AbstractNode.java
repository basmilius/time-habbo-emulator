package com.basmilius.time.habbohotel.rooms.pathfinding;

public abstract class AbstractNode
{

	public static final int BASICMOVEMENTCOST = 10;
	public static final int DIAGONALMOVEMENTCOST = 14;

	private int xPosition;
	private int yPosition;
	private boolean walkable;

	private AbstractNode previous;

	private boolean diagonally;

	private int movementPanelty;

	private int gCosts;

	private int hCosts;

	AbstractNode(int xPosition, int yPosition)
	{
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.walkable = true;
		this.movementPanelty = 0;
	}

	public boolean isDiagonaly()
	{
		return diagonally;
	}

	public void setIsDiagonaly(boolean isDiagonaly)
	{
		this.diagonally = isDiagonaly;
	}

	public void setCoordinates(int x, int y)
	{
		this.xPosition = x;
		this.yPosition = y;
	}

	public int getX()
	{
		return xPosition;
	}

	public int getY()
	{
		return yPosition;
	}

	public boolean isWalkable()
	{
		return walkable;
	}

	public void setWalkable(boolean walkable)
	{
		this.walkable = walkable;
	}

	public AbstractNode getPrevious()
	{
		return previous;
	}

	public void setPrevious(AbstractNode previous)
	{
		this.previous = previous;
	}

	public void setMovementPanelty(int movementPanelty)
	{
		this.movementPanelty = movementPanelty;
	}

	public int getfCosts()
	{
		return gCosts + hCosts;
	}

	public int getgCosts()
	{
		return gCosts;
	}

	private void setgCosts(int gCosts)
	{
		this.gCosts = gCosts + movementPanelty;
	}

	public void setgCosts(AbstractNode previousAbstractNode)
	{
		if (diagonally)
		{
			setgCosts(previousAbstractNode, DIAGONALMOVEMENTCOST);
		}
		else
		{
			setgCosts(previousAbstractNode, BASICMOVEMENTCOST);
		}
	}

	void setgCosts(AbstractNode previousAbstractNode, int basicCost)
	{
		setgCosts(previousAbstractNode.getgCosts() + basicCost);
	}

	public int calculategCosts(AbstractNode previousAbstractNode)
	{
		if (diagonally)
		{
			return (previousAbstractNode.getgCosts() + DIAGONALMOVEMENTCOST + movementPanelty);
		}
		else
		{
			return (previousAbstractNode.getgCosts() + BASICMOVEMENTCOST + movementPanelty);
		}
	}

	public int calculategCosts(AbstractNode previousAbstractNode, int movementCost)
	{
		return (previousAbstractNode.getgCosts() + movementCost + movementPanelty);
	}

	int gethCosts()
	{
		return hCosts;
	}

	void sethCosts(int hCosts)
	{
		this.hCosts = hCosts;
	}

	public abstract void sethCosts(AbstractNode endAbstractNode);

	@Override
	public String toString()
	{
		return "(" + getX() + ", " + getY() + "): h: " + gethCosts() + " g: " + getgCosts() + " f: " + getfCosts();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final AbstractNode other = (AbstractNode) obj;
		return this.xPosition == other.xPosition && this.yPosition == other.yPosition;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 17 * hash + this.xPosition;
		hash = 17 * hash + this.yPosition;
		return hash;
	}

}