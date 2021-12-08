package com.basmilius.time.habbohotel.rooms.floorplan;

/**
 * Base for FloorPlans.
 * This is used for hardcoded server room models.
 */
public abstract class IFloorPlan
{

	private String heightmap;
	private int doorX;
	private int doorY;
	private int doorZ;
	private int doorRot;

	public IFloorPlan()
	{
		this.heightmap = "";
		this.doorX = 1;
		this.doorY = 1;
		this.doorZ = 0;
		this.doorRot = 4;
	}

	public String getHeightmap()
	{
		return this.heightmap.toLowerCase();
	}

	protected void setHeightmap(final String heightmap)
	{
		this.heightmap = heightmap;
	}

	public int getDoorX()
	{
		return this.doorX;
	}

	protected void setDoorX(final int doorX)
	{
		this.doorX = doorX;
	}

	public int getDoorY()
	{
		return this.doorY;
	}

	protected void setDoorY(final int doorY)
	{
		this.doorY = doorY;
	}

	public int getDoorZ()
	{
		return this.doorZ;
	}

	protected void setDoorZ(final int doorZ)
	{
		this.doorZ = doorZ;
	}

	public int getDoorRot()
	{
		return this.doorRot;
	}

	protected void setDoorRot(final int doorRot)
	{
		this.doorRot = doorRot;
	}
}
