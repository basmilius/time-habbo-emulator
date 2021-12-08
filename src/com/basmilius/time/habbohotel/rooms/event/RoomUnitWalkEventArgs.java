package com.basmilius.time.habbohotel.rooms.event;

import com.basmilius.time.core.event.IEventArgs;

public class RoomUnitWalkEventArgs extends IEventArgs
{

	private final int x;
	private final int y;
	private final double z;

	public RoomUnitWalkEventArgs(final int x, final int y, final double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public final int getX()
	{
		return this.x;
	}

	public final int getY()
	{
		return this.y;
	}

	public final double getZ()
	{
		return this.z;
	}

}
