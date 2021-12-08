package com.basmilius.time.communication.messages.outgoing.habbohelpers;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboHelpersGuideSessionErrorComposer extends MessageComposer
{

	private int errorCode;

	public HabboHelpersGuideSessionErrorComposer(int errorCode)
	{
		this.errorCode = errorCode;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HabboHelpersGuideSessionError);
		response.appendInt(this.errorCode);
		return response;
	}

}
