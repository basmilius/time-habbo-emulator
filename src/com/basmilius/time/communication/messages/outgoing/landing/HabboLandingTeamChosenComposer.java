package com.basmilius.time.communication.messages.outgoing.landing;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboLandingTeamChosenComposer extends MessageComposer
{

	private final String teamName;
	private final boolean chosen;

	public HabboLandingTeamChosenComposer(final String teamName, final boolean chosen)
	{
		this.teamName = teamName;
		this.chosen = chosen;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HabboLandingTeamChosen);
		response.appendString(this.teamName);
		response.appendBoolean(this.chosen);
		return response;
	}

}
