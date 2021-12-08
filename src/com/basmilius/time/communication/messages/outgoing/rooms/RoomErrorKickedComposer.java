package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomErrorKickedComposer extends MessageComposer
{

	public static final int ROOM_ERROR_KICKED = 4008;
	public static final int ROOM_ERROR_CANT_SET_NOT_OWNER = -32000;

	private final int _errorCode;

	public RoomErrorKickedComposer()
	{
		this._errorCode = ROOM_ERROR_KICKED;
	}

	public RoomErrorKickedComposer(int _errorCode)
	{
		this._errorCode = _errorCode;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomErrorKicked);
		response.appendInt(this._errorCode);
		return response;
	}

}
