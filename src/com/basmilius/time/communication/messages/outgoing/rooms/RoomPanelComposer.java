package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomPanelComposer extends MessageComposer
{

	private final int roomId;
	private final boolean isRoomOwner;

	public RoomPanelComposer(final int roomId, final boolean isRoomOwner)
	{
		this.roomId = roomId;
		this.isRoomOwner = isRoomOwner;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomPanel);
		response.appendInt(this.roomId);
		response.appendBoolean(this.isRoomOwner);
		return response;
	}

}
