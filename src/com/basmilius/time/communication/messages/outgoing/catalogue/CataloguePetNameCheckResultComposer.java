package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CataloguePetNameCheckResultComposer extends MessageComposer
{

	private final int result;
	private final String name;
	
	public CataloguePetNameCheckResultComposer(final int result, final String name)
	{
		this.result = result;
		this.name = name;
	}

	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.CataloguePetNameCheckResult);
		response.appendInt(this.result);
		response.appendString(this.name);
		return response;
	}

}
