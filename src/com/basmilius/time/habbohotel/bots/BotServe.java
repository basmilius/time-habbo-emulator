package com.basmilius.time.habbohotel.bots;

import com.basmilius.time.util.ObjectUtils;

import java.util.Random;

/**
 * An item that the bot can serve.
 */
public class BotServe
{

	private final int itemId;
	private final String[] keywords;
	private final String[] responses;

	/**
	 * Constructor.
	 *
	 * @param data Serve data.
	 */
	public BotServe(String data)
	{
		final String[] serveData = data.split(";#;");

		if (!(serveData.length == 3 && ObjectUtils.isNumeric(serveData[0])))
		{
			this.itemId = 0;
			this.keywords = new String[]{"OWNER DIDN'T A GOOD JOB"};
			this.responses = new String[]{"Item serve data (bot.serve.*) not valid."};
		}
		else
		{
			this.itemId = Integer.parseInt(serveData[0]);
			this.keywords = serveData[1].split(";");
			this.responses = serveData[2].split(";");
		}
	}

	/**
	 * Gets the item ID.
	 *
	 * @return int
	 */
	public int getItemId()
	{
		return this.itemId;
	}

	/**
	 * Gets a random response.
	 *
	 * @return String
	 */
	public String getRandomResponse()
	{
		return this.responses[(new Random()).nextInt(this.responses.length)];
	}

	/**
	 * Gets if the text contains a keyword.
	 *
	 * @param text The chat from the User.
	 * @return boolean
	 */
	public boolean getContainsKeyword(final String text)
	{
		boolean containsKeyword = false;

		for (final String keyword : this.keywords)
		{
			if (text.toLowerCase().contains(keyword))
			{
				containsKeyword = true;
				break;
			}
		}

		return containsKeyword;
	}

}
