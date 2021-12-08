package com.basmilius.time.communication.messages.outgoing.habbohelpers;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboHelpersGuideSessionStartedComposer extends MessageComposer
{

	private final Habbo habbo;
	private final Habbo helper;

	public HabboHelpersGuideSessionStartedComposer(final Habbo habbo, final Habbo helper)
	{
		this.habbo = habbo;
		this.helper = helper;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HabboHelpersGuideSessionStarted);
		response.appendInt(this.habbo.getId());
		response.appendString(this.habbo.getUsername());
		response.appendString(this.habbo.getLook());
		response.appendInt(this.helper.getId());
		response.appendString(this.helper.getUsername());
		response.appendString(this.helper.getLook());
		return response;
	}

}
