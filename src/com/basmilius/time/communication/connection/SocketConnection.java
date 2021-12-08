package com.basmilius.time.communication.connection;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.communication.messages.outgoing.handshake.DisconnectReasonMessageComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericAlertComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericAlertWithUrlComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericModAlertComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericModAlertWithUrlComposer;
import com.basmilius.time.habbohotel.enums.GenericAlertType;
import com.basmilius.time.habbohotel.gameclients.GameClientChannel;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.util.crypto.HabboEncryption;

import java.net.SocketAddress;

public class SocketConnection extends Thread implements IConnection
{

	private final HabboEncryption encryption;
	private Habbo habbo;
	private final GameClientChannel socket;

	private Class<? extends MessageComposer> lastComposer;

	private boolean disposed;

	public SocketConnection(final GameClientChannel socket)
	{
		this.encryption = new HabboEncryption();
		this.habbo = null;
		this.socket = socket;

		this.disposed = false;
	}

	public final HabboEncryption getEncryption ()
	{
		return this.encryption;
	}

	public final Habbo getHabbo ()
	{
		return this.habbo;
	}

	public final GameClientChannel getSocket ()
	{
		return this.socket;
	}

	public final SocketAddress getSocketAddress()
	{
		return this.getSocket().remoteAddress();
	}

	public final boolean hasHabbo()
	{
		return this.getHabbo() != null;
	}

	public final void sendNotif(final GenericAlertType alertType, final String message)
	{
		this.sendNotif(alertType, message, "");
	}

	public final void sendNotif(final GenericAlertType alertType, final String message, final String url)
	{
		switch(alertType)
		{
			case MOD_TEXT:
				this.send(new GenericModAlertComposer(message));
				break;
			case MOD_TEXT_WITH_URL:
				this.send(new GenericModAlertWithUrlComposer(message, url));
				break;
			case TEXT:
				this.send(new GenericAlertComposer(message));
				break;
			case TEXT_WITH_URL:
				this.send(new GenericAlertWithUrlComposer(message, url));
				break;
		}
	}

	public final void setHabbo(final Habbo habbo)
	{
		this.habbo = habbo;
	}

	@Override
	public final void close ()
	{
		this.close(0);
	}

	@Override
	public final void close (final int reason)
	{
		this.send(new DisconnectReasonMessageComposer(reason));
	}

	@Override
	public final boolean isAuthenticated ()
	{
		return false;
	}

	@Override
	public final boolean isConfigured ()
	{
		return false;
	}

	@Override
	public final void progressReceivedData (final ClientMessage message)
	{
		try
		{
			if (Bootstrap.getEngine().getServer().getHabboMessages().isRegistred(message.getMessageId()))
			{
				final Class<? extends MessageEvent> messageHandler = Bootstrap.getEngine().getServer().getHabboMessages().getMessageHandler(message.getMessageId());
				if (messageHandler != null)
				{
					final MessageEvent messageEvent = messageHandler.newInstance();

					Bootstrap.getEngine().getLogging().logPacketLine(String.format("<Event> <%s %d> -> %s", messageEvent.getClass().getSimpleName(), message.getMessageId(), message.getMessageBody()), false);

					messageEvent.connection = this;
					messageEvent.packet = message;
					messageEvent.handle();
				}
			}
			else
			{
				Bootstrap.getEngine().getLogging().logPacketLine(String.format("<Unknown Event> <Unknown %d> -> %s", message.getMessageId(), message.getMessageBody()), true);
			}
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
		Bootstrap.freeMemory();
	}

	@Override
	public final void send (final MessageComposer composer)
	{
		this.sendUnencrypted(composer);
	}

	@Override
	public final void send (final QueuedComposers composers)
	{
		this.sendUnencrypted(composers);
	}

	@Override
	public final void send (final ServerMessage message)
	{
		this.sendUnencrypted(message);
	}

	@Override
	public final void sendUnencrypted (final MessageComposer composer)
	{
		if (composer.isCanceled())
			return;

		this.lastComposer = composer.getClass();

		try
		{
			final ServerMessage message = composer.compose();
			Bootstrap.getEngine().getLogging().logPacketLine(String.format("<Composer> <%s %d> -> %s", composer.getClass().getSimpleName(), message.getMessageId(), message.getBodyString()), false);
			this.send(message);
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

	@Override
	public final void sendUnencrypted (final QueuedComposers composers)
	{
		try
		{
			this.socket.getChannel().writeAndFlush(composers).sync();
		}
		catch (InterruptedException e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

	@Override
	public final void sendUnencrypted (final ServerMessage message)
	{
		if (message.getMessageId() < 0)
			return;

		message.setReaderIndex(0);

		try
		{
			this.socket.getChannel().writeAndFlush(message).sync();
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

	@Override
	public final void dispose ()
	{
		try
		{
			this.socket.disconnect().sync();

			Bootstrap.getEngine().getLogging().logDebugLine(String.format("Connection dropped for %s.", this.getSocketAddress().toString()));

			if (this.hasHabbo())
			{
				this.getHabbo().leaveRoom();
				this.getHabbo().setConnection(null);
				this.getHabbo().getMessenger().sendUpdate();
				Bootstrap.getEngine().getLogging().log(SocketConnection.class, this.getHabbo().getUsername() + " signed off.");
				Bootstrap.getEngine().getLogging().logDebugLine(String.format("Latest message sended to %s: %s", this.getHabbo().getUsername(), this.lastComposer.getCanonicalName()));
				this.getHabbo().dispose();
				Bootstrap.getEngine().getGame().getHabboManager().disposeHabbo(this.getHabbo());
			}

			this.disposed = true;
		}
		catch (InterruptedException e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

	@Override
	public final boolean isDisposed ()
	{
		return this.disposed;
	}

}
