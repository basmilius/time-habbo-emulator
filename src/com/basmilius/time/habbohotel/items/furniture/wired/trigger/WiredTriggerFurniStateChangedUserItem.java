package com.basmilius.time.habbohotel.items.furniture.wired.trigger;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredTriggerUserItem;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class WiredTriggerFurniStateChangedUserItem extends WiredTriggerUserItem
{

	public WiredTriggerFurniStateChangedUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredTriggerFurniStateChangedUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public boolean handle(final RoomUnit unit, final UserItem item, final String text)
	{
		if (item != null)
		{
			if (!this.isLinkedItem(item))
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
		packet.readString();
		this.saveLinkedItems(packet.readIntArray());
	}

	@Override
	public void serializeWiredData(final ServerMessage response)
	{
		response.appendBoolean(false);
		response.appendInt(5);
		response.appendInt(this.getLinkedItems().size());
		for (final UserItem linkedItem : this.getLinkedItems())
		{
			response.appendInt(linkedItem.getId());
		}
		response.appendInt(this.getBaseItem().getSpriteId());
		response.appendInt(this.getId());
		response.appendString("");
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(4);
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
