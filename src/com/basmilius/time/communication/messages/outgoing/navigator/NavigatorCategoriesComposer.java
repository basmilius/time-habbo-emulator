package com.basmilius.time.communication.messages.outgoing.navigator;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NavigatorCategoriesComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.NavigatorCategories);
		response.appendInt(5); // always + 5 (for flat categories)
		response.appendString("recommended");
		response.appendString("popular");
		response.appendString("new_ads");
		response.appendString("staffpicks");
		response.appendString("official");
		return response;
	}

}
