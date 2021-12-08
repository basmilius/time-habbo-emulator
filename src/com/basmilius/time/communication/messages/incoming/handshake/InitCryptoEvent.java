package com.basmilius.time.communication.messages.incoming.handshake;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.handshake.PrimeAndGeneratorComposer;

@Event(id = Incoming.InitCrypto)
public class InitCryptoEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		connection.send(new PrimeAndGeneratorComposer(connection.getEncryption().getRsaDHPrime(), connection.getEncryption().getRsaDHGenerator()));
	}

}
