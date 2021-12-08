package com.basmilius.time.habbohotel.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class AsyncTask<TResultType> extends FutureTask<TResultType>
{

	public AsyncTask(final Callable<TResultType> callable)
	{
		super(callable);
		
		final ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.execute(this);
	}
	
}
