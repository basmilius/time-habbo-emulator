package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomErrorFullComposer extends MessageComposer
{

	public static final int ROOM_ERROR_GUESTROOM_FULL = 1;
	public static final int ROOM_ERROR_QUEUE = 3;
	public static final int ROOM_ERROR_OWNER_BANNED_YOU = 4;

	private final int _errorCode;
	private final String _parameter;

	public RoomErrorFullComposer(int _errorCode)
	{
		this._errorCode = _errorCode;
		this._parameter = "";
	}

	public RoomErrorFullComposer(int _errorCode, String _parameter)
	{
		this._errorCode = _errorCode;
		this._parameter = _parameter;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomErrorFull);
		response.appendInt(this._errorCode);
		if (!this._parameter.isEmpty())
		{
			response.appendString(this._parameter);
		}
		return response;
	}

}
