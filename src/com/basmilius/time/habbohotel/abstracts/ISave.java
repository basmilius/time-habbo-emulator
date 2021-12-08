package com.basmilius.time.habbohotel.abstracts;

import com.basmilius.time.Bootstrap;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Savable object.
 */
public abstract class ISave extends TimerTask
{

	private Timer timer;
	private int saveTime;
	private boolean needsDelete;
	private boolean needsInsert;
	private boolean needsUpdate;

	{
		this.saveTime = 60000;
		this.needsDelete = false;
		this.needsInsert = false;
		this.needsUpdate = false;
	}

	/**
	 * Starts the save timer.
	 */
	private void startSaveTimer()
	{
		if (this.timer != null)
			return;

		this.timer = new Timer();
		this.timer.scheduleAtFixedRate(this, saveTime, saveTime);
	}

	/**
	 * Sets the save delay.
	 *
	 * @param time delay.
	 */
	protected void setSaveTime(final int time)
	{
		this.saveTime = time;
	}

	/**
	 * Gets if this ISave object needs a delete.
	 *
	 * @return boolean
	 */
	public boolean getNeedsDelete()
	{
		return this.needsDelete;
	}

	/**
	 * Sets if this ISave object needs a delete.
	 *
	 * @param need Needs delete.
	 */
	public void setNeedsDelete(final boolean need)
	{
		this.needsDelete = need;
		this.startSaveTimer();
	}

	/**
	 * Gets if this ISave object needs an insert.
	 *
	 * @return boolean
	 */
	public boolean getNeedsInsert()
	{
		return this.needsInsert;
	}

	/**
	 * Sets if this ISave object needs an insert.
	 *
	 * @param need Needs insert.
	 */
	public void setNeedsInsert(final boolean need)
	{
		this.needsInsert = need;
		this.startSaveTimer();
	}

	/**
	 * Gets if this ISave object needs an update.
	 *
	 * @return boolean
	 */
	public boolean getNeedsUpdate()
	{
		return this.needsUpdate;
	}

	/**
	 * Sets if this ISave object needs an update.
	 *
	 * @param need Needs update.
	 */
	public void setNeedsUpdate(final boolean need)
	{
		this.needsUpdate = need;
		this.startSaveTimer();
	}

	/**
	 * Executes when needs save.
	 *
	 * @throws SQLException
	 */
	protected abstract void save() throws SQLException;

	/**
	 * Background tasks.
	 */
	@Override
	public void run()
	{
		if (!Bootstrap.getEngine().isReady())
			return;

		if (!this.getNeedsUpdate() && !this.getNeedsDelete() && !this.getNeedsInsert())
			return;

		try
		{
			this.save();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
	}

	/**
	 * Disposes.
	 */
	public void dispose()
	{
		try
		{
			this.save();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		this.timer.cancel();
	}

}
