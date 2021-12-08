package com.basmilius.time.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class IEventDispatcher
{

	private final Map<Class, List<IEventHandler>> handlers;

	protected IEventDispatcher()
	{
		this.handlers = new HashMap<>();
	}

	public final <T extends IEventArgs> void addEventListener(Class<T> eventType, IEventHandler<T> eventHandler)
	{
		synchronized (this.handlers)
		{
			if (!this.handlers.containsKey(eventType))
			{
				this.handlers.put(eventType, new ArrayList<>());
			}

			this.handlers.get(eventType).add(eventHandler);
		}
	}

	public final <T extends IEventArgs> void removeEventListener(Class<T> eventType, IEventHandler<T> eventHandler)
	{
		if (this.handlers.containsKey(eventType))
		{
			synchronized (this.handlers)
			{
				if (this.handlers.get(eventType).contains(eventHandler))
				{
					this.handlers.get(eventType).remove(eventHandler);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public final <T extends IEventArgs> void dispatchEvent(Class<T> eventType, IEventArgs args)
	{
		if (this.handlers.containsKey(eventType))
		{
			synchronized (this.handlers)
			{
				for (final IEventHandler handler : this.handlers.get(eventType).subList(0, this.handlers.get(eventType).size()))
				{
					if (args.preventedDefault())
						break;

					handler.handle(args);
				}
			}
		}
	}

}
