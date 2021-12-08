package com.basmilius.time.habbohotel.rooms.chat;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;

public class RoomChatMessage
{

	private final RoomUnit unit;
	private final Room room;
	private final String message;
	private final ChatBubble bubble;
	private final int saidOn;
	private ChatType type;
	private RoomUser unitTo;

	public RoomChatMessage(final RoomUnit unit, final Room room, final ChatType type, final String message, final ChatBubble bubble, final int saidOn)
	{
		this.unit = unit;
		this.room = room;
		this.type = type;
		this.message = message;
		this.bubble = bubble;
		this.saidOn = saidOn;
		this.unitTo = null;
	}

	public RoomUnit getUnit()
	{
		return this.unit;
	}

	public Room getRoom()
	{
		return this.room;
	}

	public ChatType getType()
	{
		return this.type;
	}

	public void setType(final ChatType type)
	{
		this.type = type;
	}

	public String getMessage()
	{
		String message = Bootstrap.getEngine().getGame().getWordFilterManager().filterString(this.message);

		if (this.unit.getUnitType() == RoomUnitType.BOT)
		{
			message = message.replace("%myname", this.unit.getName());

			if (this.unitTo != null)
			{
				message = message.replace("%username", this.unitTo.getName());
			}
		}

		return message;
	}

	public ChatBubble getBubble()
	{
		return this.bubble;
	}

	public int getSaidOn()
	{
		return this.saidOn;
	}

	public int getEmotion()
	{
		if (this.message.toLowerCase().contains(":d") || this.message.toLowerCase().contains(":)") || this.message.toLowerCase().contains(";d"))
			return 1;
		else if (this.message.toLowerCase().contains(":@") || this.message.toLowerCase().contains("-.-"))
			return 2;
		else if (this.message.toLowerCase().contains(":o") || this.message.toLowerCase().contains(";o"))
			return 3;
		else if (this.message.toLowerCase().contains(":(") || this.message.toLowerCase().contains(":c") || this.message.toLowerCase().contains(";c") || this.message.toLowerCase().contains(":'("))
			return 4;
		else
			return 0;
	}

	public RoomUser getUnitTo()
	{
		return this.unitTo;
	}

	public void setUnitTo(final RoomUser unitTo)
	{
		this.unitTo = unitTo;
	}
}
