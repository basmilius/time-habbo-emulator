package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.util.TimeUtils;

public class HabboRoomVisit
{

	private Room room;
	private int enterTime;
	private int leaveTime;

	public HabboRoomVisit(Room room)
	{
		this.room = room;
		this.enterTime = TimeUtils.getUnixTimestamp();
		this.leaveTime = 0;
	}

	public Room getRoom()
	{
		return this.room;
	}

	public int getEnterTime()
	{
		return this.enterTime;
	}

	public int getLeaveTime()
	{
		return this.leaveTime;
	}

	public int getTimeInRoom()
	{
		if (this.leaveTime > 0)
			return (this.leaveTime - this.enterTime);
		else
			return (TimeUtils.getUnixTimestamp() - this.enterTime);
	}

	public int getHoursInRoom()
	{
		return (int) Math.floor(this.getTimeInRoom() / 3600);
	}

	public int getMinutesInRoom()
	{
		return (int) Math.floor((this.getTimeInRoom() / 60) % 60);
	}

	public void setLeaveTime()
	{
		this.leaveTime = TimeUtils.getUnixTimestamp();
	}

}
