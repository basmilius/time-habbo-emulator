package com.basmilius.time.habbohotel.items.furniture;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class RoomBackgroundUserItem extends UserItem
{

	public RoomBackgroundUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public RoomBackgroundUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
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

	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{
		String[] data = this.getExtraData().split(";");

		if (data.length == 4)
		{
			this.setExtraData((data[0].equals("1") ? "0" : "1") + ";" + data[1] + ";" + data[2] + ";" + data[3]);
		}
		else
		{
			this.setExtraData("1;126;126;126");
		}

		this.updateAllDataInRoom();
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

		response.appendInt(5);
		response.appendInt(4);

		if (this.getExtraData().contains(";"))
		{
			String[] data = this.getExtraData().split(";");
			response.appendInt(Integer.parseInt(data[0]));
			response.appendInt(Integer.parseInt(data[1]));
			response.appendInt(Integer.parseInt(data[2]));
			response.appendInt(Integer.parseInt(data[3]));
		}
		else
		{
			response.appendInt(0);
			response.appendInt(126);
			response.appendInt(126);
			response.appendInt(126);
		}
	}

}
