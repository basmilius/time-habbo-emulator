package com.basmilius.time.core.event;

public interface IEventHandler<T extends IEventArgs>
{

	void handle (T eventArgs);

}
