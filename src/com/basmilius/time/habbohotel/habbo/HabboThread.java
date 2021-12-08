package com.basmilius.time.habbohotel.habbo;

class HabboThread implements Runnable
{

	private final Habbo habbo;
	private Thread thread;

	public HabboThread(Habbo habbo)
	{
		this.habbo = habbo;
	}

	boolean isActive()
	{
		return (this.thread != null);
	}

	public void start()
	{
		if (this.isActive())
			return;

		this.thread = new Thread(this);
		this.thread.start();
	}

	public void stop()
	{
		if (!this.isActive())
			return;

		this.thread.interrupt();
		this.thread = null;
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				if (this.habbo.getSettings() != null)
				{
					this.habbo.getSettings().updateSecondsOnline();
				}

				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				return;
			}
		}
	}

}
