package com.basmilius.time.communication.encryption;

import com.basmilius.time.communication.wireformat.EvaWireFormat;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

public class CryptoTools
{

	public static String byteArrayToString (final ByteBuf byteArray)
	{
		byteArray.readerIndex(0);
		String data = "";

		while(byteArray.readableBytes() > 0)
		{
			data = (data + ((char) byteArray.readByte()));
		}

		return data;
	}

	public static ByteBuf stringToByteArray(final String data)
	{
		final ByteBuf byteArray = new CompositeByteBuf(ByteBufAllocator.DEFAULT, true, EvaWireFormat.MaxData);

		int i = 0;
		while (i < data.length())
		{
			byteArray.writeByte(data.charAt(i));
			i++;
		}

		byteArray.writerIndex(0);

		return byteArray;
	}

	public static String byteArrayToHexString(final ByteBuf byteArray, final boolean uppercase)
	{
		byteArray.readerIndex(0);
		String data = "";

		while (byteArray.readableBytes() > 0)
		{
			final short a = byteArray.readUnsignedByte();
			final int b = (a >> 4);
			final int c = (a & 15);
			data = (data + Integer.toString(b, 16));
			data = (data + Integer.toString(c, 16));
		}

		if (uppercase)
			data = data.toUpperCase();

		return data;
	}

	public static ByteBuf hexStringToByteArray(String data)
	{
		final ByteBuf byteArray = new CompositeByteBuf(ByteBufAllocator.DEFAULT, true, EvaWireFormat.MaxData);

		if ((data.length() % 2) != 0)
		{
			data = ("0" + data);
		}

		int i = 0;
		while (i < (data.length() - 1))
		{
			final int a = Integer.parseInt(Character.toString(data.charAt(i)), 16);
			final int b = Integer.parseInt(Character.toString(data.charAt(i + 1)), 16);
			final int c = ((a << 4) | b);
			byteArray.writeByte(c);
			i++;
			i++;
		}

		return byteArray;
	}

	public static String bigIntegerToRadix(final ByteBuf byteArray, final int radix)
	{
		return "";
	}

}
