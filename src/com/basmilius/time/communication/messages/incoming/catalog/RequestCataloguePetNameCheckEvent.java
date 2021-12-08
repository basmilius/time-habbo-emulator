package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.CataloguePetNameCheckResultComposer;
import com.basmilius.time.util.StringUtils;

@Event(id = Incoming.RequestCataloguePetNameCheck)
public class RequestCataloguePetNameCheckEvent extends MessageEvent
{

	@Override
	public final void handle()
	{
		final String name = packet.readString();
		final int result;
		
		if (name.length() > 15)
			result = 1;
		else if (name.length() < 3)
			result = 2;
		else if (!StringUtils.isAlphaNum(name))
			result = 3;
		else if (Bootstrap.getEngine().getGame().getWordFilterManager().stringContainsBadWord(name))
			result = 4;
		else
			result = 0;
		
		connection.send(new CataloguePetNameCheckResultComposer(result, name));
	}

}
