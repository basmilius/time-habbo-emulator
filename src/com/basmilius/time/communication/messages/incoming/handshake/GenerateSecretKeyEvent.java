package com.basmilius.time.communication.messages.incoming.handshake;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.handshake.ServerPublicKeyComposer;
import com.basmilius.time.util.ArrayUtils;

import java.math.BigInteger;
import java.util.Arrays;

@Event(id = Incoming.GenerateSecretKey)
public class GenerateSecretKeyEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		final BigInteger sharedKey = connection.getEncryption().initialize(packet.readString());
		
		if (!sharedKey.equals(BigInteger.ZERO))
		{
			byte[] sharedKeyBytes = sharedKey.toByteArray();
			if (sharedKeyBytes[sharedKeyBytes.length - 1] == 0)
				sharedKeyBytes = Arrays.copyOf(sharedKeyBytes, sharedKeyBytes.length - 1);
			
			ArrayUtils.reverse(sharedKeyBytes);
			
			//connection.getEncryption().initializeArc4(sharedKeyBytes);
			
			connection.send(new ServerPublicKeyComposer(connection.getEncryption().getRsaDHPublicKey()));
		}
	}

}
