package com.basmilius.time.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

/**
 * String encryption class.
 */
@SuppressWarnings("UnusedDeclaration")
public class StringCrypt
{

	/**
	 * Calculates the SHA-1 hash of the input String.
	 *
	 * @param input String to encrypt.
	 * @return String
	 * @throws NoSuchAlgorithmException
	 */
	public static String getSHA1Hash(final String input) throws NoSuchAlgorithmException
	{
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuilder sb = new StringBuilder();
		for (byte aResult : result)
		{
			sb.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	/**
	 * Calculates the SHA-256 hash of the input String.
	 *
	 * @param input String to encrypt.
	 * @return String
	 * @throws NoSuchAlgorithmException, NoSuchProviderException
	 */
	public static String getSha256(final String input) throws NoSuchAlgorithmException, NoSuchProviderException
	{
		MessageDigest mda = MessageDigest.getInstance("SHA-256");
		byte[] bytes = mda.digest(input.getBytes());

		String out = "";
		for (byte temp : bytes)
		{
			String s = Integer.toHexString(temp);
			while (s.length() < 2)
			{
				s = "0" + s;
			}
			s = s.substring(s.length() - 2);
			out += s;
		}

		return out;
	}

	/**
	 * Calculates the SHA-512 hash of the input String.
	 *
	 * @param input String to encrypt.
	 * @return String
	 * @throws NoSuchAlgorithmException, NoSuchProviderException
	 */
	public static String getSha512(final String input) throws NoSuchAlgorithmException, NoSuchProviderException
	{
		MessageDigest mda = MessageDigest.getInstance("SHA-512");
		byte[] bytes = mda.digest(input.getBytes());

		String out = "";
		for (byte temp : bytes)
		{
			String s = Integer.toHexString(temp);
			while (s.length() < 2)
			{
				s = "0" + s;
			}
			s = s.substring(s.length() - 2);
			out += s;
		}

		return out;
	}

	/**
	 * Chunks a string into parts.
	 *
	 * @param string        String to be chunked.
	 * @param partitionSize Size of one partition.
	 * @return List
	 */
	public static List<String> getParts(final String string, final int partitionSize)
	{
		List<String> parts = new ArrayList<>();
		int len = string.length();
		for (int i = 0; i < len; i += partitionSize)
		{
			parts.add(string.substring(i, Math.min(len, i + partitionSize)));
		}
		return parts;
	}

	/**
	 * Encrypts a String into BAS.
	 *
	 * @param input String to encrypt.
	 * @return int[]
	 */
	public static int[] encodeBasArray(final String input)
	{
		final int[] output = new int[input.length()];
		int x = 0;

		for (int i = 0; i < input.length(); i++)
		{
			output[i] = ((65290 - input.charAt(i)) + x--);
		}

		return output;
	}

	/**
	 * Decrypts an int[] from BAS to a String.
	 *
	 * @param input int array to be decrypted.
	 * @return String
	 */
	public static String decodeBasArray(final int[] input)
	{
		String output = "";
		int x = 0;

		for (int r : input)
		{
			output += Character.toString((char) ((65290 - r) + x--));
		}

		return output;
	}

	public static String unMess(final String data)
	{
		return StringCrypt.unMess(data, 10001);
	}

	public static String unMess(final String data, final int prime)
	{
		String response = "";

		for (int i = 0; i < data.length(); i++)
		{
			response += Character.toString((char) ((data.charAt(i)) - prime));
		}

		return response;
	}

}
