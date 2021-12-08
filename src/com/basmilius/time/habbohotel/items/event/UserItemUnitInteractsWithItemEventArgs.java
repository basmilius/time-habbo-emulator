package com.basmilius.time.habbohotel.items.event;

import com.basmilius.time.core.event.IEventArgs;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.items.UserItem;

public class UserItemUnitInteractsWithItemEventArgs extends IEventArgs
{

	private final UserItem item;
	private final RoomUnit unit;
	private final int param;

	public UserItemUnitInteractsWithItemEventArgs(final UserItem item, final RoomUnit unit, final int param)
	{
		this.item = item;
		this.unit = unit;
		this.param = param;
	}

	public final UserItem getItem()
	{
		return this.item;
	}

	public final RoomUnit getUnit()
	{
		return this.unit;
	}

	public final int getParam()
	{
		return this.param;
	}

}
