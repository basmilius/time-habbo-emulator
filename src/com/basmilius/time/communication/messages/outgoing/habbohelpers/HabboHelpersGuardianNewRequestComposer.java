package com.basmilius.time.communication.messages.outgoing.habbohelpers;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboHelpersGuardianNewRequestComposer extends MessageComposer
{

	private int seconds;

	public HabboHelpersGuardianNewRequestComposer(int seconds)
	{
		this.seconds = seconds;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HabboHelpersGuardianNewRequest);
		response.appendInt(this.seconds);
		return response;
	}

}
