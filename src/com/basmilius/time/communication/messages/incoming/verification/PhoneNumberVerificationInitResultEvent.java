package com.basmilius.time.communication.messages.incoming.verification;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.PhoneNumberVerificationInitResult)
public class PhoneNumberVerificationInitResultEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int result = packet.readInt();

		switch (result)
		{
			case 0:
				// activate phone number.
				break;
			case 2:
				// don't activate phone number.
				break;
		}
	}

}
