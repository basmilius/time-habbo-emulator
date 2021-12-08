package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomErrorBotComposer extends MessageComposer
{

	public static final int ROOM_ERROR_BOTS_FORBIDDEN_HOTEL = 0;
	public static final int ROOM_ERROR_BOTS_FORBIDDEN_ROOM = 1;
	public static final int ROOM_ERROR_BOTS_MAX = 2;
	public static final int ROOM_ERROR_BOTS_SELECTED_TILE_NOT_FREE = 3;
	public static final int ROOM_ERROR_BOTS_NAME_NOT_ACCEPTED = 4;

	private final int _errorCode;

	public RoomErrorBotComposer(int _errorCode)
	{
		this._errorCode = _errorCode;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomErrorBot);
		response.appendInt(this._errorCode);
		return response;
	}

}
