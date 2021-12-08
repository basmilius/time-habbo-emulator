package com.basmilius.time.habbohotel.rooms.event;

import com.basmilius.time.core.event.IEventArgs;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;

public class RoomUnitChatEventArgs extends IEventArgs
{

	private final ChatType type;
	private final String message;
	private final ChatBubble bubble;

	public RoomUnitChatEventArgs(final ChatType type, final String message, final ChatBubble bubble)
	{
		this.type = type;
		this.message = message;
		this.bubble = bubble;
	}

	public final ChatType getType()
	{
		return this.type;
	}

	public final String getMessage()
	{
		return this.message;
	}

	public final ChatBubble getBubble()
	{
		return this.bubble;
	}
}
