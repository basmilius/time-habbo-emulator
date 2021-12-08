package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ReceiveMessageComposer extends MessageComposer
{

	private final int friendId;
	private final String message;
	private final int secondsFromNow;

	public ReceiveMessageComposer(Integer friendId, String message, Integer secondsFromNow)
	{
		this.friendId = friendId;
		this.message = message;
		this.secondsFromNow = secondsFromNow;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.ReceiveMessage);
		response.appendInt(this.friendId);
		response.appendString(this.message);
		response.appendInt(this.secondsFromNow);
		return response;
	}

}
