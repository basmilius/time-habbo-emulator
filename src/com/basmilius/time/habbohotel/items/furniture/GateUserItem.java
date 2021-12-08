package com.basmilius.time.habbohotel.items.furniture;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class GateUserItem extends UserItem
{

	public GateUserItem(final ResultSet result) throws SQLException
	{
		super(result);

		this.setIsWalkable(this.getExtraData().equalsIgnoreCase("1"));
	}

	public GateUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);

		this.setIsWalkable(this.getExtraData().equalsIgnoreCase("1"));
	}

	@Override
	public void onWiredCollision(final RoomUnit unit)
	{

	}

	@Override
	public void onWiredInteractsWithItem(final WiredUserItem wiredItem)
	{
		if (this.getExtraData().equalsIgnoreCase("1"))
		{
			this.setExtraData("0");
		}
		else
		{
			this.setExtraData("1");
		}
		this.updateStateInRoom();

		this.setIsWalkable(this.getExtraData().equalsIgnoreCase("1"));
		this.getRoom().getRoomObjectsHandler().updateGameMap();
	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{
		if (unit.getUnitType() != RoomUnitType.USER)
			return;

		if (!this.getRoom().getRoomData().getPermissions().hasRights(((RoomUser) unit).getHabbo()))
			return;

		if (this.getExtraData().equalsIgnoreCase("1"))
		{
			this.setExtraData("0");
		}
		else
		{
			this.setExtraData("1");
		}
		this.updateStateInRoom();

		this.setIsWalkable(this.getExtraData().equalsIgnoreCase("1"));
		this.getRoom().getRoomObjectsHandler().updateGameMap();
	}

	@Override
	public boolean onUnitSaysSomething(final RoomUnit unit, final String text, final ChatBubble chatBubble, final boolean isShouted)
	{
		return false;
	}

	@Override
	public void onUnitWalksOnItem(final RoomUnit unit)
	{

	}

	@Override
	public void onUnitWalksOffItem(final RoomUnit unit)
	{

	}

	@Override
	public void serializeData(final ServerMessage response, final boolean isInventory, final boolean isDataOnly)
	{
		if (!isDataOnly)
			response.appendInt(0);

		response.appendInt(0);
		response.appendString(this.getExtraData());
	}
}
