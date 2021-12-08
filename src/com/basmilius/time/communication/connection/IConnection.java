package com.basmilius.time.communication.connection;

import com.basmilius.core.IDisposable;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public interface IConnection extends IDisposable
{

	void close();
	void close(final int reason);
	boolean isAuthenticated();
	boolean isConfigured();
	void progressReceivedData(final ClientMessage message);
	void send(final MessageComposer composer);
	void send(final QueuedComposers composers);
	void send(final ServerMessage message);
	void sendUnencrypted(final MessageComposer composer);
	void sendUnencrypted(final QueuedComposers composers);
	void sendUnencrypted(final ServerMessage message);

}
