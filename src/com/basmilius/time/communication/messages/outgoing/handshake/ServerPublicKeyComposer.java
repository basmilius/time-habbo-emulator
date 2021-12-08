package com.basmilius.time.communication.messages.outgoing.handshake;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ServerPublicKeyComposer extends MessageComposer
{

	private String publicKey;

	public ServerPublicKeyComposer(String publicKey)
	{
		this.publicKey = publicKey;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ServerPublicKey);
		response.appendString(this.publicKey);
		return response;
	}

}
