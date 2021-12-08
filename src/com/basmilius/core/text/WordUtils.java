package com.basmilius.core.text;

public class WordUtils
{

	public static String wrap (String str, int wrapLength)
	{
		return wrap(str, wrapLength, null, false);
	}

	public static String wrap (String str, int wrapLength, String newLineStr, boolean wrapLongWords)
	{
		if (str == null)
		{
			return null;
		}
		if (newLineStr == null)
		{
			newLineStr = System.lineSeparator();
		}
		if (wrapLength < 1)
		{
			wrapLength = 1;
		}
		int inputLineLength = str.length();
		int offset = 0;
		StringBuilder wrappedLine = new StringBuilder(inputLineLength + 32);

		while ((inputLineLength - offset) > wrapLength)
		{
			if (str.charAt(offset) == ' ')
			{
				offset++;
				continue;
			}
			int spaceToWrapAt = str.lastIndexOf(' ', wrapLength + offset);

			if (spaceToWrapAt >= offset)
			{
				// normal case
				wrappedLine.append(str.substring(offset, spaceToWrapAt));
				wrappedLine.append(newLineStr);
				offset = spaceToWrapAt + 1;

			}
			else
			{
				// really long word or URL
				if (wrapLongWords)
				{
					// wrap really long word one line at a time
					wrappedLine.append(str.substring(offset, wrapLength + offset));
					wrappedLine.append(newLineStr);
					offset += wrapLength;
				}
				else
				{
					// do not wrap really long word, just extend beyond limit
					spaceToWrapAt = str.indexOf(' ', wrapLength + offset);
					if (spaceToWrapAt >= 0)
					{
						wrappedLine.append(str.substring(offset, spaceToWrapAt));
						wrappedLine.append(newLineStr);
						offset = spaceToWrapAt + 1;
					}
					else
					{
						wrappedLine.append(str.substring(offset));
						offset = inputLineLength;
					}
				}
			}
		}

		// Whatever is left in line is short enough to just pass through
		wrappedLine.append(str.substring(offset));

		return wrappedLine.toString();
	}

	public static String capitalize (String str)
	{
		return capitalize(str, null);
	}

	public static String capitalize (String str, char[] delimiters)
	{
		if (str == null || str.length() == 0)
		{
			return str;
		}
		int strLen = str.length();
		StringBuilder buffer = new StringBuilder(strLen);

		int delimitersLen = 0;
		if (delimiters != null)
		{
			delimitersLen = delimiters.length;
		}

		boolean capitalizeNext = true;
		for (int i = 0; i < strLen; i++)
		{
			char ch = str.charAt(i);

			boolean isDelimiter = false;
			if (delimiters == null)
			{
				isDelimiter = Character.isWhitespace(ch);
			}
			else
			{
				for (int j = 0; j < delimitersLen; j++)
				{
					if (ch == delimiters[j])
					{
						isDelimiter = true;
						break;
					}
				}
			}

			if (isDelimiter)
			{
				buffer.append(ch);
				capitalizeNext = true;
			}
			else if (capitalizeNext)
			{
				buffer.append(Character.toTitleCase(ch));
				capitalizeNext = false;
			}
			else
			{
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	public static String capitalizeFully (String str)
	{
		return capitalizeFully(str, null);
	}

	public static String capitalizeFully (String str, char[] delimiters)
	{
		if (str == null || str.length() == 0)
		{
			return str;
		}
		str = str.toLowerCase();
		return capitalize(str, delimiters);
	}

	public static String uncapitalize (String str)
	{
		return uncapitalize(str, null);
	}

	public static String uncapitalize (String str, char[] delimiters)
	{
		if (str == null || str.length() == 0)
		{
			return str;
		}
		int strLen = str.length();

		int delimitersLen = 0;
		if (delimiters != null)
		{
			delimitersLen = delimiters.length;
		}

		StringBuilder buffer = new StringBuilder(strLen);
		boolean uncapitalizeNext = true;
		for (int i = 0; i < strLen; i++)
		{
			char ch = str.charAt(i);

			boolean isDelimiter = false;
			if (delimiters == null)
			{
				isDelimiter = Character.isWhitespace(ch);
			}
			else
			{
				for (int j = 0; j < delimitersLen; j++)
				{
					if (ch == delimiters[j])
					{
						isDelimiter = true;
						break;
					}
				}
			}

			if (isDelimiter)
			{
				buffer.append(ch);
				uncapitalizeNext = true;
			}
			else if (uncapitalizeNext)
			{
				buffer.append(Character.toLowerCase(ch));
				uncapitalizeNext = false;
			}
			else
			{
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	public static String swapCase (String str)
	{
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
		{
			return str;
		}
		StringBuilder buffer = new StringBuilder(strLen);

		boolean whitespace = true;
		char ch;
		char tmp;

		for (int i = 0; i < strLen; i++)
		{
			ch = str.charAt(i);
			if (Character.isUpperCase(ch))
			{
				tmp = Character.toLowerCase(ch);
			}
			else if (Character.isTitleCase(ch))
			{
				tmp = Character.toLowerCase(ch);
			}
			else if (Character.isLowerCase(ch))
			{
				if (whitespace)
				{
					tmp = Character.toTitleCase(ch);
				}
				else
				{
					tmp = Character.toUpperCase(ch);
				}
			}
			else
			{
				tmp = ch;
			}
			buffer.append(tmp);
			whitespace = Character.isWhitespace(ch);
		}
		return buffer.toString();
	}

}