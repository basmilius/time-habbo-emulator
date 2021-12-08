package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomUpdateComposer extends MessageComposer
{

	private final int _roomId;

	public RoomUpdateComposer(int _roomId)
	{
		this._roomId = _roomId;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.RoomUpdate);
		response.appendInt(this._roomId);
		return response;
	}

}
