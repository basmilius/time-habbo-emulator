package com.basmilius.time.core;

@SuppressWarnings("UnusedDeclaration")
public class ExternalFlashText
{

	private final String key;

	private String translation;

	public ExternalFlashText(final String key, final String translation)
	{
		this.key = key;
		this.translation = translation;
	}

	public final String getKey()
	{
		return this.key;
	}

	public final String getTranslation()
	{
		return this.translation;
	}

	public final ExternalFlashText registerParameter(final String key, final String value)
	{
		this.translation = this.translation.replace(String.format("%%%s%%", key), value);

		return this;
	}

	public final String toString()
	{
		return this.translation;
	}

}
