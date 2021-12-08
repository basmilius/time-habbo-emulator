package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomErrorPetComposer extends MessageComposer
{

	public static final int ROOM_ERROR_PETS_FORBIDDEN_HOTEL = 0;
	public static final int ROOM_ERROR_PETS_FORBIDDEN_ROOM = 1;
	public static final int ROOM_ERROR_PETS_MAX = 2;
	public static final int ROOM_ERROR_PETS_NO_FREE_TILES = 3;
	public static final int ROOM_ERROR_PETS_SELECTED_TILE_NOT_FREE = 4;
	public static final int ROOM_ERROR_PETS_MAX_OWNED = 5;

	private final int _errorCode;

	public RoomErrorPetComposer(int _errorCode)
	{
		this._errorCode = _errorCode;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomErrorPet);
		response.appendInt(this._errorCode);
		return response;
	}

}
