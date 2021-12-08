package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.habbohotel.abstracts.IPixelException;

class RoomException extends IPixelException
{

	private final String message;
	private final Room room;

	public RoomException(final Room room, final String message)
	{
		this.errorCode = 2402;
		this.message = message;
		this.room = room;
	}

	public final String getMessage()
	{
		return this.message;
	}

	public final Room getRoom()
	{
		return this.room;
	}
}
