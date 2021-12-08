package com.basmilius.time.habbohotel.rooms.cycles;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.rooms.Room;

import java.util.List;

public abstract class ICycle implements Runnable
{

	private final Room room;
	private final Thread thread;

	public ICycle(Room room)
	{
		this.room = room;
		this.thread = new Thread(this);
	}

	protected Room getRoom()
	{
		return this.room;
	}

	protected List<RoomUnit> getRoomUnits()
	{
		return this.room.getRoomUnitsHandler().getUnits();
	}

	public void start()
	{
		this.thread.start();
	}

	public void stop()
	{
		this.thread.interrupt();
	}

	@Override
	public abstract void run();

}
