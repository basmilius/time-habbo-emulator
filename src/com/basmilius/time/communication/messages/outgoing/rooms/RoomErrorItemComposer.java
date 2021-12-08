package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomErrorItemComposer extends MessageComposer
{

	public static final int ROOM_ERROR_CANT_PLACE_ITEM_NOT_OWNER = 1;
	public static final int ROOM_ERROR_CANT_PLACE_ITEM = 11;
	public static final int ROOM_ERROR_CANT_PLACE_MORE_STICKIES = 12;
	public static final int ROOM_ERROR_MAX_ITEMS = 20;
	public static final int ROOM_ERROR_MAX_PETS = 21;
	public static final int ROOM_ERROR_MAX_ROLLERS = 22;
	public static final int ROOM_ERROR_JUST_ONE_SOUNDMACHINE = 23;

	private final int _errorCode;

	public RoomErrorItemComposer(int _errorCode)
	{
		this._errorCode = _errorCode;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomErrorItem);
		response.appendInt(this._errorCode);
		return response;
	}

}
