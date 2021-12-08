package com.basmilius.time.habbohotel.catalogue;

/**
 * Exception for Purchase purposes.
 */
public class PurchaseException extends Exception
{

	private final String message;

	/**
	 * Constructor.
	 *
	 * @param parent Parent exception.
	 */
	public PurchaseException(final Exception parent)
	{
		this.message = parent.getMessage();
	}

	/**
	 * Constructor.
	 *
	 * @param message Exception message.
	 */
	public PurchaseException(final String message)
	{
		this.message = message;
	}

	/**
	 * Gets the exception message.
	 *
	 * @return String
	 */
	@Override
	public String getMessage()
	{
		return this.message;
	}

}

