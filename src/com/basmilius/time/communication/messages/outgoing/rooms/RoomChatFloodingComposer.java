package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomChatFloodingComposer extends MessageComposer
{

	private final int seconds;

	public RoomChatFloodingComposer(final int seconds)
	{
		this.seconds = seconds;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ChatFlooding);
		response.appendInt(this.seconds);
		return response;
	}
}
