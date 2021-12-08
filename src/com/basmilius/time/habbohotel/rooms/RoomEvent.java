package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.util.TimeUtils;

public class RoomEvent implements Runnable
{

	private final Habbo habbo;
	private final Room room;
	private final int startTime;
	private final Thread eventThread;
	private int stopTime;
	private String name;
	private String description;

	public RoomEvent(Habbo habbo, Room room, int startTime, int stopTime, String name, String description)
	{
		this.habbo = habbo;
		this.room = room;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.name = name;
		this.description = description;

		this.eventThread = new Thread(this);
	}

	void cancelEvent()
	{
		this.eventThread.interrupt();
		this.room.getRoomData().setRoomEvent(null);
	}

	public void startEvent()
	{
		this.eventThread.start();
		this.room.getRoomData().setRoomEvent(this);
	}

	public void extend(int time)
	{
		this.stopTime += time;
	}

	public Habbo getHabbo()
	{
		return habbo;
	}

	public Room getRoom()
	{
		return room;
	}

	public int getStartTime()
	{
		return startTime;
	}

	public int getStopTime()
	{
		return stopTime;
	}

	public int getTimeLeft()
	{
		return (int) Math.ceil(((this.stopTime - TimeUtils.getUnixTimestamp()) / 60));
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public void run()
	{
		while (true)
		{
			if (TimeUtils.getUnixTimestamp() >= this.stopTime)
			{
				this.cancelEvent();
			}

			try
			{
				Thread.sleep(60000);
			}
			catch (InterruptedException ignored)
			{
				return;
			}
		}
	}
}
