package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NavigatorMetaDataComposer extends MessageComposer
{
	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.NavigatorMetaData);
		response.appendInt(4);
		response.appendString("official_view");
		response.appendInt(0);
		response.appendString("hotel_view");
		response.appendInt(0);
		response.appendString("roomads_view");
		response.appendInt(0);
		response.appendString("myworld_view");
		response.appendInt(0);
		return response;
	}
}
