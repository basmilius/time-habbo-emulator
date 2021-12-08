package com.basmilius.time.communication.messages.incoming;

import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.communication.messages.ClientMessage;

public abstract class MessageEvent
{

	public SocketConnection connection;
	public ClientMessage packet;

	public abstract void handle() throws Exception;

}
