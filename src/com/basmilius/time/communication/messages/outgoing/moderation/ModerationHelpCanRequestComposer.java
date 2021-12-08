package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationHelpCanRequestComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		return response.init(Outgoing.ModerationHelpCanRequest);
	}

}
