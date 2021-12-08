package com.basmilius.time.communication.messages.outgoing.buildersclub;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class BuildersClubFurniCountComposer extends MessageComposer
{

	private final int furniCount;

	public BuildersClubFurniCountComposer(int furniCount)
	{
		this.furniCount = furniCount;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.BuildersClubFurniCount);
		response.appendInt(this.furniCount);
		return response;
	}

}
