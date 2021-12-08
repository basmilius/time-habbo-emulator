package com.basmilius.time.habbohotel.algorithm;

/**
 * Base for every Algorithm.
 *
 * @param <T> Type of object to calculate with.
 */
public abstract class IAlgorithm<T>
{

	private T object;

	{
		this.object = null;
	}

	/**
	 * Gets the Object.
	 *
	 * @return T
	 */
	public T getObject()
	{
		return this.object;
	}

	/**
	 * Sets the Object.
	 *
	 * @param object Object to work with.
	 */
	public void setObject(T object)
	{
		this.object = object;
	}

}
