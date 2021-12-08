package com.basmilius.time.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils
{

	public static String implode(final String glue, String[] array)
	{
		return Arrays.stream(array).collect(Collectors.joining(glue));
	}
	
	public static boolean isAlphaNum(final String input)
	{
		for (int i = 0; i < input.length(); i++)
		{
			final char c = input.charAt(i);
			if (!Character.isDigit(c) && !Character.isLetter(c))
				return false;
		}
		return true;
	}
	
}
