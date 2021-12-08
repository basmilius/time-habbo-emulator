package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NavigatorLiftedRoomsComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.NavigatorLiftedRooms);

		response.appendInt(2);
		response.appendInt(1);
		response.appendInt(0);
		response.appendString("navigator/lifted_rooms/welcomelounge.png");
		response.appendString("Test 1");
		response.appendInt(3);
		response.appendInt(0);
		response.appendString("navigator/lifted_rooms/kateupton.png");
		response.appendString("Test 2");

		return response;
	}

}
