package com.basmilius.time.core.event;

@SuppressWarnings("UnusedDeclaration")
public class IEventArgs
{

	private boolean cancel;

	public IEventArgs()
	{
		this.cancel = false;
	}

	public final void preventDefault()
	{
		this.cancel = true;
	}

	public final boolean preventedDefault()
	{
		return this.cancel;
	}

}
