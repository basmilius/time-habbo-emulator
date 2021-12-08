package com.basmilius.time.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ExternalFlashTexts
{

	private final Map<String, String> translations;

	public ExternalFlashTexts(final String eUrl) throws IOException
	{
		this.translations = new HashMap<>();

		final URL url = new URL(eUrl);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

		String line;
		while ((line = reader.readLine()) != null)
		{
			String[] data = line.split("=", 2);
			this.translations.put(data[0], data[1]);
		}

		reader.close();
	}

	public ExternalFlashText getTranslation(final String key)
	{
		return this.getTranslation(key, key);
	}

	public ExternalFlashText getTranslation(final String key, final String defaultValue)
	{
		if (this.translations.containsKey(key))
		{
			return new ExternalFlashText(key, this.translations.get(key));
		}
		return new ExternalFlashText(key, defaultValue);
	}

}
