package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class RoomUpdateErrorComposer extends MessageComposer
{

	public static final int UPDATE_FAILED_1 = 1;
	public static final int UPDATE_FAILED_2 = 2;
	public static final int UPDATE_FAILED_3 = 3;
	public static final int UPDATE_FAILED_4 = 4;
	public static final int PASSWORD_EMPTY = 5;
	public static final int UPDATE_FAILED_6 = 6;
	public static final int ROOMNAME_EMPTY = 7;
	public static final int ROOMNAME_UNACCEPTABLE_WORDS = 8;
	public static final int UPDATE_FAILED_9 = 9;
	public static final int ROOMDESC_UNACCEPTABLE_WORDS = 10;
	public static final int ROOMTAGS_UNACCEPTABLE_WORDS = 11;
	public static final int EMPTY_ROOMNAME = 12;

	private int roomId;
	private int errorCode;

	public RoomUpdateErrorComposer(int roomId, int errorCode)
	{
		this.roomId = roomId;
		this.errorCode = errorCode;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.RoomUpdateError);
		response.appendInt(this.roomId);
		response.appendInt(this.errorCode);
		return response;
	}

}
