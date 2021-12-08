package com.basmilius.time.communication.messages.outgoing.buildersclub;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class BuildersClubStatusComposer extends MessageComposer
{

	private final int secondsLeft;
	private final int maxItems;

	public BuildersClubStatusComposer(int secondsLeft, int maxItems)
	{
		if (secondsLeft < 0)
			secondsLeft = 0;

		if (maxItems < 0)
			maxItems = 0;

		this.secondsLeft = secondsLeft;
		this.maxItems = maxItems;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.BuildersClubStatus);
		response.appendInt(this.secondsLeft);
		response.appendInt(this.maxItems);
		response.appendInt(0);
		return response;
	}

}
