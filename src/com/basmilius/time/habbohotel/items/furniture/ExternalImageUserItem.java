package com.basmilius.time.habbohotel.items.furniture;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class ExternalImageUserItem extends UserItem
{

	public ExternalImageUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public ExternalImageUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public void onWiredCollision(final RoomUnit unit)
	{

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

		JSONObject json = new JSONObject();
		json.put("n", "Bas");
		json.put("s", 1);
		json.put("u", "unieke-id");
		json.put("w", "w");

		response.appendInt(3);
		response.appendInt(1);
		response.appendString(json.toString());
	}
}
