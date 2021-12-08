package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.util.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabboCache
{

	private final Habbo habbo;

	private List<HabboRoomVisit> roomVisits;

	private boolean inventoryOpened;

	private int muteStartTime = 0;
	private int muteEndTime = 0;

	private Map<String, Object> registry;

	public HabboCache(Habbo habbo)
	{
		this.habbo = habbo;
		this.roomVisits = new ArrayList<>();
		this.inventoryOpened = false;
		this.registry = new HashMap<>();
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public HabboRoomVisit getRoomVisit(Room room)
	{
		for (HabboRoomVisit roomVisit : this.roomVisits)
			if (roomVisit.getRoom().getRoomData().getId() == room.getRoomData().getId() && roomVisit.getLeaveTime() == 0)
				return roomVisit;

		for (HabboRoomVisit roomVisit : this.roomVisits)
			if (roomVisit.getRoom().getRoomData().getId() == room.getRoomData().getId())
				return roomVisit;

		return null;
	}

	public List<HabboRoomVisit> getRoomVisits()
	{
		return this.roomVisits;
	}

	public boolean getInventoryOpened()
	{
		return this.inventoryOpened;
	}

	public void setInventoryOpened()
	{
		this.inventoryOpened = true;
	}

	public boolean isMuted()
	{
		return !(this.muteEndTime == 0 && this.muteStartTime == 0 || TimeUtils.getUnixTimestamp() >= this.muteEndTime);
	}

	public void setMute(final int startTime, final int endTime)
	{
		this.muteStartTime = startTime;
		this.muteEndTime = endTime;
	}

	public final boolean getBoolean(final String key, final boolean defaultValue)
	{
		if (this.registry.containsKey(key))
			return (boolean) this.registry.get(key);
		return defaultValue;
	}

	public final double getDouble(final String key, final double defaultValue)
	{
		if (this.registry.containsKey(key))
			return (double) this.registry.get(key);
		return defaultValue;
	}

	public final int getInt(final String key, final int defaultValue)
	{
		if (this.registry.containsKey(key))
			return (int) this.registry.get(key);
		return defaultValue;
	}

	public final String getString(final String key, final String defaultValue)
	{
		if (this.registry.containsKey(key))
			return (String) this.registry.get(key);
		return defaultValue;
	}

	public final void set(final String key, final Object value)
	{
		if (this.registry.containsKey(key))
			this.registry.remove(key);

		this.registry.put(key, value);
	}

	public void dispose()
	{
		this.roomVisits.clear();
	}

}
