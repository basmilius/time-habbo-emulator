package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FriendErrorComposer extends MessageComposer
{

	public static final int FRIENDLIST_FULL = 1;
	public static final int FRIENDLIST_FULL_OTHER_HABBO = 2;
	public static final int REQUESTS_DISABLED = 3;
	public static final int USER_NOT_FOUND = 4;

	private final int _userId;
	private final int _errorCode;

	public FriendErrorComposer(int _userId, int _errorCode)
	{
		this._userId = _userId;
		this._errorCode = _errorCode;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FriendError);
		response.appendInt(this._userId);
		response.appendInt(this._errorCode);
		return response;
	}

}
