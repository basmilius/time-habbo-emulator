package com.basmilius.time.communication.messages.outgoing.habbohelpers;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboHelpersGuideSessionAttachedComposer extends MessageComposer
{

	private boolean onDuty;
	private int type;
	private String issue;
	private int seconds;

	public HabboHelpersGuideSessionAttachedComposer(boolean onDuty, int type, String issue, int seconds)
	{
		this.onDuty = onDuty;
		this.type = type;
		this.issue = issue;
		this.seconds = seconds;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HabboHelpersGuideSessionAttached);
		response.appendBoolean(this.onDuty);
		response.appendInt(this.type);
		response.appendString(this.issue);
		response.appendInt(this.seconds);
		return response;
	}

}
