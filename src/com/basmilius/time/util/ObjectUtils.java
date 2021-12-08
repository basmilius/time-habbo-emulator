package com.basmilius.time.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ObjectUtils
{

	public static String filterInjectionChars(String str)
	{
		str = str.replace(Character.toString((char) 0), "");
		str = str.replace(Character.toString((char) 1), "");
		str = str.replace(Character.toString((char) 2), "");
		str = str.replace(Character.toString((char) 3), "");
		str = str.replace(Character.toString((char) 9), "");

		return str;
	}

	public static <T> T getRandomObject(List<T> objects)
	{
		if (objects.size() > 1)
		{
			SecureRandom random = new SecureRandom();
			random.nextInt(objects.size());
			random.nextInt(objects.size());
			random.nextInt(objects.size());
			random.nextInt(objects.size());
			random.nextInt(objects.size());
			return objects.get(random.nextInt(objects.size()));
		}
		else if (objects.size() == 1)
		{
			return objects.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getRandomObject(Stream<T> objects)
	{
		return ObjectUtils.getRandomObject((T[]) objects.toArray());
	}

	public static <T> T getRandomObject(T[] objects)
	{
		if (objects.length > 1)
		{
			SecureRandom random = new SecureRandom();
			random.nextInt(objects.length);
			random.nextInt(objects.length);
			random.nextInt(objects.length);
			random.nextInt(objects.length);
			random.nextInt(objects.length);
			return objects[random.nextInt(objects.length)];
		}
		else if (objects.length == 1)
		{
			return objects[0];
		}

		return null;
	}

	public static boolean isNumeric(String string) throws IllegalArgumentException
	{
		boolean isNumeric = false;

		if (string != null && !string.equals(""))
		{
			isNumeric = true;
			char chars[] = string.toCharArray();

			for (char aChar : chars)
			{
				isNumeric = Character.isDigit(aChar);

				if (!isNumeric)
					break;
			}
		}
		return isNumeric;
	}

	public static int[] stringToIntArray(String[] arr)
	{
		int[] result = new int[arr.length];
		int i = 0;

		for (String a : arr)
		{
			result[i++] = Integer.parseInt(a);
		}

		return result;
	}

	public static <T> List<List<T>> getChunkedList(final List<T> list, final int max)
	{
		final List<List<T>> result = new ArrayList<>();
		final int size = list.size();

		for (int i = 0; i < size; i += max)
		{
			result.add(new ArrayList<>(list.subList(i, Math.min(size, i + max))));
		}

		return result;
	}

}
