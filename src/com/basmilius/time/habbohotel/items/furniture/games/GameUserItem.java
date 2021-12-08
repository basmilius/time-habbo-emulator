package com.basmilius.time.habbohotel.items.furniture.games;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.event.UserItemUnitInteractsWithItemEventArgs;
import com.basmilius.time.habbohotel.items.event.UserItemUnitWalksOffItemEventArgs;
import com.basmilius.time.habbohotel.items.event.UserItemUnitWalksOnItemEventArgs;
import com.basmilius.time.habbohotel.rooms.games.IGame;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GameUserItem extends UserItem
{

	public GameUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public GameUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	public final <T extends IGame> T getGame(Class<T> type)
	{
		if (this.getRoom() == null)
			return null;

		return this.getRoom().getGame(type);
	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{
		this.dispatchEvent(UserItemUnitInteractsWithItemEventArgs.class, new UserItemUnitInteractsWithItemEventArgs(this, unit, param));
	}

	@Override
	public void onUnitWalksOnItem(final RoomUnit unit)
	{
		this.dispatchEvent(UserItemUnitWalksOnItemEventArgs.class, new UserItemUnitWalksOnItemEventArgs(this, unit));
	}

	@Override
	public void onUnitWalksOffItem(final RoomUnit unit)
	{
		this.dispatchEvent(UserItemUnitWalksOffItemEventArgs.class, new UserItemUnitWalksOffItemEventArgs(this, unit));
	}

}