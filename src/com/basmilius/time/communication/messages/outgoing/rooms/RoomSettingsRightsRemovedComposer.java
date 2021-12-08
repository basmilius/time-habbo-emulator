package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomSettingsRightsRemovedComposer extends MessageComposer
{

	private int roomId;
	private int userId;

	public RoomSettingsRightsRemovedComposer(int roomId, int userId)
	{
		this.roomId = roomId;
		this.userId = userId;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.RoomSettingsRightsRemoved);
		response.appendInt(this.roomId);
		response.appendInt(this.userId);
		return response;
	}

}
