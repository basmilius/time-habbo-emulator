package com.basmilius.time.communication.messages.outgoing.rooms;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class SendModelComposer extends MessageComposer
{

	private final String modelName;
	private final int roomId;

	public SendModelComposer(String modelName, Integer roomId)
	{
		this.modelName = modelName;
		this.roomId = roomId;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.SendModel);
		response.appendString(this.modelName);
		response.appendInt(this.roomId);
		return response;
	}

}
