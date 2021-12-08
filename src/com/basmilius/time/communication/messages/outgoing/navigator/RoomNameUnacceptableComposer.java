package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomNameUnacceptableComposer extends MessageComposer
{

	public static final int INVALID_PASSWORD = -100002;
	public static final int NEED_TO_BE_HC = 4009;
	public static final int INVALID_ROOM_NAME = 4010;
	public static final int CANNOT_PERM_BAN = 4011;

	private int errorCode;

	public RoomNameUnacceptableComposer()
	{
		this.errorCode = 4010;
	}

	public RoomNameUnacceptableComposer(int errorCode)
	{
		this.errorCode = errorCode;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.RoomNameUnacceptable);
		response.appendInt(this.errorCode);
		return response;
	}

}
