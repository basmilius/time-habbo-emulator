package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomSettingsRightsAddedComposer extends MessageComposer
{

	private int roomId;
	private int userId;
	private String userName;

	public RoomSettingsRightsAddedComposer(int roomId, int userId, String userName)
	{
		this.roomId = roomId;
		this.userId = userId;
		this.userName = userName;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.RoomSettingsRightsAdded);
		response.appendInt(this.roomId);
		response.appendInt(this.userId);
		response.appendString(this.userName);
		return response;
	}

}
