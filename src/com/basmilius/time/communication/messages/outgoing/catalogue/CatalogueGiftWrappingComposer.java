package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CatalogueGiftWrappingComposer extends MessageComposer
{

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.CatalogueGiftWrapping);
		response.appendBoolean(true);
		response.appendInt(1);
		response.appendInt(10);
		response.appendInt(3089);
		response.appendInt(3090);
		response.appendInt(3091);
		response.appendInt(3092);
		response.appendInt(3093);
		response.appendInt(3094);
		response.appendInt(3095);
		response.appendInt(3096);
		response.appendInt(3097);
		response.appendInt(3098);
		response.appendInt(8);
		for (int i = 0; i < 9; i++)
		{
			if (i == 7)
				continue;
			response.appendInt(i);
		}
		response.appendInt(11);
		for (int i = 0; i < 11; i++)
		{
			response.appendInt(i);
		}
		response.appendInt(0);
		return response;
	}

}
