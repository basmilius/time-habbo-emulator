package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.rooms.chat.RoomChatMessage;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class TalkShoutComposer extends MessageComposer
{

	private final RoomUnit unit;
	private final String text;
	private final int emotionId;
	private final int bubbleId;

	public TalkShoutComposer(RoomUnit unit, String text, Integer emotionId, Integer bubbleId)
	{
		this.unit = unit;
		this.text = text;
		this.emotionId = emotionId;
		this.bubbleId = bubbleId;
	}

	public TalkShoutComposer(final RoomChatMessage roomChatMessage)
	{
		this.unit = roomChatMessage.getUnit();
		this.text = roomChatMessage.getMessage();
		this.emotionId = roomChatMessage.getEmotion();
		this.bubbleId = roomChatMessage.getBubble().toInt();
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.Shout);
		response.appendInt(this.unit.getUnitId());
		response.appendString(this.text);
		response.appendInt(this.emotionId);
		response.appendInt(this.bubbleId);
		response.appendInt(0);
		response.appendInt(-1);
		return response;
	}

}
