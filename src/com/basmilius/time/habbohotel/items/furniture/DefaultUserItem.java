package com.basmilius.time.habbohotel.items.furniture;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.trigger.WiredTriggerFurniStateChangedUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class DefaultUserItem extends UserItem
{

	public DefaultUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public DefaultUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public void onWiredCollision(final RoomUnit unit)
	{

	}

	@Override
	public void onWiredInteractsWithItem(final WiredUserItem wiredUserItem)
	{
		this.changeExtraData();
		this.updateStateInRoom();
	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{
		if (unit.getUnitType() != RoomUnitType.USER || !this.getRoom().getRoomData().getPermissions().hasRights(((RoomUser) unit).getHabbo()))
			return;

		this.changeExtraData();
		this.updateStateInRoom();

		for (WiredUserItem wiredTriggerFurniStateChanged : this.getRoom().getRoomObjectsHandler().getItems(WiredTriggerFurniStateChangedUserItem.class))
		{
			wiredTriggerFurniStateChanged.handleStack(unit, this, null);
		}
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
			response.appendInt(1);

		response.appendInt(0);
		response.appendString(this.getExtraData());
	}

}
