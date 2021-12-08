package com.basmilius.time.habbohotel.items.furniture.wired;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WiredBlobUserItem extends UserItem
{

	private boolean isHidden = false;

	public WiredBlobUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredBlobUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public void onWiredCollision(final RoomUnit unit)
	{
		this.isHidden = (this.hasUnitOnIt() && this.getUnitOnIt().getUnitType() == RoomUnitType.USER);
		this.updateStateInRoom();
	}

	@Override
	public void onWiredInteractsWithItem(final WiredUserItem wiredItem)
	{

	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{

	}

	@Override
	public boolean onUnitSaysSomething(final RoomUnit unit, final String text, final ChatBubble chatBubble, final boolean isShouted)
	{
		return false;
	}

	@Override
	public void onUnitWalksOnItem(final RoomUnit unit)
	{
		this.isHidden = this.hasUnitOnIt();
		this.updateStateInRoom();

		/**
		 * TODO: Give the user OR the team where the user is in points.
		 */
	}

	@Override
	public void onUnitWalksOffItem(final RoomUnit unit)
	{
		this.isHidden = this.hasUnitOnIt();
		this.updateStateInRoom();
	}

	@Override
	public void serializeData(final ServerMessage response, final boolean isInventory, final boolean isDataOnly)
	{
		if (!isDataOnly)
			response.appendInt(1);

		response.appendInt(0);
		response.appendString(this.isHidden ? "1" : "0");
	}

}
