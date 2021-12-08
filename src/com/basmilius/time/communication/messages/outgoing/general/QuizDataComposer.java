package com.basmilius.time.communication.messages.outgoing.general;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class QuizDataComposer extends MessageComposer
{

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.QuizData);
		response.appendString("HabboWay1");
		response.appendInt(5);
		response.appendInt(1);
		response.appendInt(0);
		response.appendInt(7);
		response.appendInt(3);
		response.appendInt(4);
		return response;
	}

}
