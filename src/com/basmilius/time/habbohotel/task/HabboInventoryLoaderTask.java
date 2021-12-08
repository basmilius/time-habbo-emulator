package com.basmilius.time.habbohotel.task;

import com.basmilius.time.habbohotel.collections.UserItemsList;
import com.basmilius.time.habbohotel.habbo.Habbo;

import java.util.concurrent.Callable;

public class HabboInventoryLoaderTask extends AsyncTask<UserItemsList>
{

	private static HabboInventoryLoaderTaskCallable callable;

	public static HabboInventoryLoaderTask newInstance(final IOnHabboInventoryLoaderCompleted callback, final Habbo habbo)
	{
		HabboInventoryLoaderTask.callable = new HabboInventoryLoaderTaskCallable(callback, habbo);
		return new HabboInventoryLoaderTask(callable);
	}

	private HabboInventoryLoaderTask(final HabboInventoryLoaderTaskCallable callable)
	{
		super(callable);
	}

	private static class HabboInventoryLoaderTaskCallable implements Callable<UserItemsList>
	{

		private final Habbo habbo;
		private final IOnHabboInventoryLoaderCompleted callback;

		public HabboInventoryLoaderTaskCallable(final IOnHabboInventoryLoaderCompleted callback, final Habbo habbo)
		{
			this.callback = callback;
			this.habbo = habbo;
		}

		@Override
		public UserItemsList call() throws Exception
		{
			final UserItemsList userItems = habbo.getInventory().getUserItems();
			callback.OnHabboInventoryLoaderCompleted(userItems);
			return userItems;
		}

	}

	public interface IOnHabboInventoryLoaderCompleted
	{

		void OnHabboInventoryLoaderCompleted(UserItemsList userItems);

	}

}