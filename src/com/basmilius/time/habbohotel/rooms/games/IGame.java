package com.basmilius.time.habbohotel.rooms.games;

import com.basmilius.time.habbohotel.rooms.Room;

/**
 * @author Bas Milius
 */
public abstract class IGame
{

	private boolean isStarted;
	private final Room room;

	/**
	 * Constructor.
	 *
	 * @param room Room
	 */
	public IGame(final Room room)
	{
		this.room = room;
	}

	/**
	 * Gets the room where this IGame instance is loaded.
	 *
	 * @return Room
	 */
	public final Room getRoom()
	{
		return this.room;
	}

	/**
	 * Gets if the game is started.
	 *
	 * @return boolean
	 */
	public final boolean isStarted()
	{
		return this.isStarted;
	}

	/**
	 * Starts the game is it isn't already started.
	 */
	public final void start()
	{
		if (this.isStarted)
			return;

		this.isStarted = true;
		this.onStart();
	}

	/**
	 * Stops the game if it isn't already stopped.
	 */
	public final void stop()
	{
		if (!this.isStarted)
			return;

		this.isStarted = false;
		this.onStop();
	}

	/**
	 * Initializes the game.
	 */
	public abstract void initialize();

	/**
	 * Occurs when the game starts.
	 */
	protected abstract void onStart();

	/**
	 * Occurs when the game stops.
	 */
	protected abstract void onStop();

}
