package com.basmilius.time;

public class ShutdownHookThread extends Thread
{

	public ShutdownHookThread()
	{
		super(new ShutdownHookThreadRunnable());
		this.setDaemon(true);
	}

	private static final class ShutdownHookThreadRunnable implements Runnable
	{

		/**
		 * When an object implementing interface <code>Runnable</code> is used
		 * to create a thread, starting the thread causes the object's
		 * <code>run</code> method to be called in that separately executing
		 * thread.
		 * <p>
		 * The general contract of the method <code>run</code> is that it may
		 * take any action whatsoever.
		 *
		 * @see Thread#run()
		 */
		@Override
		public void run()
		{
			Bootstrap.getEngine().getLogging().clear();
			Bootstrap.getEngine().getLogging().log(ShutdownHookThreadRunnable.class, "Time Emulator is shutting things down ..");
			Bootstrap.getEngine().getLogging().log(ShutdownHookThreadRunnable.class, "Disposing managers ..");
			Bootstrap.getEngine().dispose();
			Bootstrap.getEngine().getLogging().log(ShutdownHookThreadRunnable.class, "Thanks for using Time Emulator,");
			Bootstrap.getEngine().getLogging().log(ShutdownHookThreadRunnable.class, "Goodbye!");
			Bootstrap.getEngine().getLogging().dispose();
		}

	}

}
