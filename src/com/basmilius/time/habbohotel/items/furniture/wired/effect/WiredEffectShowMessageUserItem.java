package com.basmilius.time.habbohotel.items.furniture.wired.effect;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredEffectUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class WiredEffectShowMessageUserItem extends WiredEffectUserItem
{

	public WiredEffectShowMessageUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredEffectShowMessageUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public boolean handle(final RoomUnit unit, final UserItem item, final String text)
	{
		this.toggleState();
		
		final String message = this.getValue("message", "");

		if (!message.isEmpty())
		{
			if (unit != null && unit.getUnitType() == RoomUnitType.USER)
			{
				if (message.startsWith("!! ") && message.endsWith(" !!"))
				{
					unit.chatRoomMessage(ChatType.TALK, message.substring(3, message.length() - 3), ChatBubble.WIRED);
				}
				else
				{
					unit.chatRoomMessage(ChatType.WHISPER, message, ChatBubble.WIRED, null, true);
				}
			}
		}

		return true;
	}

	@Override
	public void save(final ClientMessage packet)
	{
		this.hasWiredServerData();
		
		packet.readInt();
		this.setValue("message", packet.readString());
	}

	@Override
	public void serializeWiredData(final ServerMessage response)
	{
		response.appendBoolean(false);
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(this.getBaseItem().getSpriteId());
		response.appendInt(this.getId());
		response.appendString(this.getValue("message", ""));
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(7);
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
