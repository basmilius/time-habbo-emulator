package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomUserTypingComposer extends MessageComposer
{

	private final int unitId;
	private final boolean isTyping;

	public RoomUserTypingComposer(Integer unitId, Boolean isTyping)
	{
		this.unitId = unitId;
		this.isTyping = isTyping;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.TypingChange);
		response.appendInt(this.unitId);
		response.appendInt((this.isTyping) ? 1 : 0);
		return response;
	}

}
