package com.basmilius.time.util.crypto;

public class BitConverter
{
	
	public static String byteArrayToString(byte[] data)
	{
		StringBuilder sb = new StringBuilder();
		for (byte b : data)
		{
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}
	
	public static byte[] StringToByteArray(String data)
	{
		int NumberChars = data.length();
		byte[] bytes = new byte[NumberChars / 2];
		for (int i = 0; i < NumberChars; i += 2)
			bytes[i / 2] = (byte) Integer.valueOf(data.substring(i, i + 2), 16).intValue();
		return bytes;
	}
	
}