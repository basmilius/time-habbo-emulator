package com.basmilius.time.communication.messages.outgoing;

import com.basmilius.time.communication.messages.ServerMessage;

public abstract class MessageComposer implements Cloneable
{

	protected final ServerMessage response;
	private boolean cancel;

	protected MessageComposer ()
	{
		this.cancel = false;
		this.response = new ServerMessage();
	}

	public abstract ServerMessage compose() throws Exception;

	public boolean isCanceled()
	{
		return this.cancel;
	}

	public void preventDefault()
	{
		this.cancel = true;
	}

	public MessageComposer clone() throws CloneNotSupportedException
	{
		return (MessageComposer) super.clone();
	}

}
