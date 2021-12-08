package com.basmilius.time.habbohotel.items.furniture.wired;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.wired.WiredEffectComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class WiredEffectUserItem extends WiredUserItem
{

	public WiredEffectUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredEffectUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{
		if (unit.getUnitType() == RoomUnitType.USER && this.getRoom().getRoomData().getPermissions().hasRights(((RoomUser) unit).getHabbo()))
		{
			((RoomUser) unit).getConnection().send(new WiredEffectComposer(this));
		}
	}

}
