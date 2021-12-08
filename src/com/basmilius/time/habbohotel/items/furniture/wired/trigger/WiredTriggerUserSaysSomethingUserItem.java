package com.basmilius.time.habbohotel.items.furniture.wired.trigger;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredTriggerUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class WiredTriggerUserSaysSomethingUserItem extends WiredTriggerUserItem
{

	public WiredTriggerUserSaysSomethingUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredTriggerUserSaysSomethingUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public boolean handle(final RoomUnit unit, final UserItem item, final String text)
	{
		if (unit != null && text != null && unit.getUnitType() == RoomUnitType.USER)
		{
			final int userId = this.getValue("user_id", 0);
			final String keyword = this.getValue("keyword", "");
			
			if (userId != 0)
				if (userId != ((RoomUser) unit).getHabbo().getId())
					return false;

			if (keyword.isEmpty() || !text.toLowerCase().contains(keyword.toLowerCase()))
				return false;

			this.toggleState();

			return true;
		}

		return false;
	}

	@Override
	public void save(final ClientMessage packet)
	{
		packet.readInt();
		this.setValue("user_id", packet.readInt());
		this.setValue("keyword", packet.readString());
	}

	@Override
	public void serializeWiredData(final ServerMessage response)
	{
		response.appendBoolean(false);
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(this.getBaseItem().getSpriteId());
		response.appendInt(this.getId());
		response.appendString(this.getValue("keyword", ""));
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(0);
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

}
