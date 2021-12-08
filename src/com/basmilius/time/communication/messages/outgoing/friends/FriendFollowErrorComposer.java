package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FriendFollowErrorComposer extends MessageComposer
{

	public static final int NOT_IN_FRIENDLIST = 0;
	public static final int FRIEND_OFLINE = 1;
	public static final int FRIEND_IN_HOTELVIEW = 2;
	public static final int CANT_FOLLOW = 3;

	private final int _errorCode;

	public FriendFollowErrorComposer(Integer errorCode)
	{
		this._errorCode = errorCode;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FriendFollowError);
		response.appendInt(this._errorCode);
		return response;
	}

}
