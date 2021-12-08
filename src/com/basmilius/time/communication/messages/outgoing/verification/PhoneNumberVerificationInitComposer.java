package com.basmilius.time.communication.messages.outgoing.verification;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PhoneNumberVerificationInitComposer extends MessageComposer
{

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.PhoneNumberVerificationInit);
		return response;
	}

}
