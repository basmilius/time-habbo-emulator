package com.basmilius.time.communication.messages.incoming.general;

import com.basmilius.time.habbohotel.enums.HabboValues;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.general.HandshakeCompleteComposer;

@Event(id = Incoming.HandshakeComplete)
public class HandshakeCompleteEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.hasHabbo())
			return;

		connection.getHabbo().getValues().put(HabboValues.CLIENT_MESSENGER_INITIALIZED, true);
		connection.send(new HandshakeCompleteComposer());
	}

}
