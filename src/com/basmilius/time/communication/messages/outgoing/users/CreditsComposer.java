package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CreditsComposer extends MessageComposer
{

	private final SocketConnection connection;

	public CreditsComposer(SocketConnection connection)
	{
		this.connection = connection;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.Credits);
		response.appendString(this.connection.getHabbo().getSettings().getCredits() + ".0");
		return response;
	}

}
