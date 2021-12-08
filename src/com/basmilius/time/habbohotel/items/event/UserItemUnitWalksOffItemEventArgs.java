package com.basmilius.time.habbohotel.items.event;

import com.basmilius.time.core.event.IEventArgs;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.items.UserItem;

public class UserItemUnitWalksOffItemEventArgs extends IEventArgs
{

	private final UserItem item;
	private final RoomUnit unit;

	public UserItemUnitWalksOffItemEventArgs(final UserItem item, final RoomUnit unit)
	{
		this.item = item;
		this.unit = unit;
	}

	public final UserItem getItem()
	{
		return this.item;
	}

	public final RoomUnit getUnit()
	{
		return this.unit;
	}

}
