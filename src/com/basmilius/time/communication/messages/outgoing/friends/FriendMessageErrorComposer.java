package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class FriendMessageErrorComposer extends MessageComposer
{

	private final int userId;
	private final int errorCode;
	private final String message;

	public FriendMessageErrorComposer(Integer userId, Integer errorCode, String message)
	{
		this.userId = userId;
		this.errorCode = errorCode;
		this.message = message;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FriendMessageError);
		response.appendInt(this.errorCode);
		response.appendInt(this.userId);
		response.appendString(this.message);
		return response;
	}

}
