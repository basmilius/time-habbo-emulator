package com.basmilius.time.habbohotel.abstracts;

/**
 * PixelException.
 * <p>
 * PixelExceptions have unique error ID's.
 */
public class IPixelException extends Exception
{

	protected int errorCode;

	/**
	 * The error code.
	 *
	 * @return int
	 */
	public int getErrorCode()
	{
		return this.errorCode;
	}

}
