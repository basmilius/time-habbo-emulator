package com.basmilius.time.communication.messages.outgoing.habbohelpers;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboHelpersToolComposer extends MessageComposer
{

	private boolean onDuty;
	private int guides;
	private int helpers;
	private int guardians;

	public HabboHelpersToolComposer(boolean onDuty, int guides, int helpers, int guardians)
	{
		this.onDuty = onDuty;
		this.guides = guides;
		this.helpers = helpers;
		this.guardians = guardians;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HabboHelpersTool);
		response.appendBoolean(this.onDuty);
		response.appendInt(this.guides);
		response.appendInt(this.helpers);
		response.appendInt(this.guardians);
		return response;
	}

}
