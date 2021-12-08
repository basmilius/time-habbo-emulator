package com.basmilius.time.communication.messages.outgoing.habbohelpers;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HabboHelpersGuideSessionPartnerIsTypingComposer extends MessageComposer
{

	private final boolean typing;

	public HabboHelpersGuideSessionPartnerIsTypingComposer(final boolean typing)
	{
		this.typing = typing;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HabboHelpersGuideSessionPartnerIsTyping);
		response.appendBoolean(this.typing);
		return response;
	}

}
