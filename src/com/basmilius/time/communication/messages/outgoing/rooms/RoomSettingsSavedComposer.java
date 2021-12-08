package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomSettingsSavedComposer extends MessageComposer
{

	private int roomId;

	public RoomSettingsSavedComposer(int roomId)
	{
		this.roomId = roomId;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.RoomSettingsSaved);
		response.appendInt(this.roomId);
		return response;
	}

}
