package com.basmilius.time.communication.encryption;

import com.basmilius.time.communication.wireformat.EvaWireFormat;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

public class RC4 implements IEncryption
{

	private int i;
	private int j;
	private int[] sbox;

	public RC4()
	{
		this.i = 0;
		this.j = 0;
		this.sbox = new int[256];
	}

	@Override
	public void init (final byte[] key)
	{
		final int keyLength = key.length;

		this.i = 0;
		while (this.i < 256)
		{
			this.sbox[this.i] = this.i;
			this.i++;
		}

		this.i = 0;
		this.j = 0;
		while (this.i < 256)
		{
			this.j = (((this.j + this.sbox[this.i]) + key[(this.i % keyLength)]) % 256);
			final int t = this.sbox[this.i];
			this.sbox[this.i] = this.sbox[this.j];
			this.sbox[this.j] = t;
			this.i++;
		}

		this.i = 0;
		this.j = 0;
	}

	@Override
	public ByteBuf encipher (final ByteBuf buffer)
	{
		final ByteBuf byteArray = new CompositeByteBuf(ByteBufAllocator.DEFAULT, true, EvaWireFormat.MaxData);

		buffer.readerIndex(0);

		while(buffer.readableBytes() > 0)
		{
			this.i = ((this.i + 1) % 256);
			this.j = ((this.j + this.sbox[this.i]) % 256);
			final int t = this.sbox[this.i];
			this.sbox[this.i] = this.sbox[this.j];
			this.sbox[this.j] = t;
			final int x = ((this.sbox[this.i] + this.sbox[this.j]) % 256);
			byteArray.writeByte((this.sbox[x] ^ buffer.readByte()));
		}

		byteArray.writerIndex(0);
		return byteArray;
	}

	@Override
	public ByteBuf decipher (final ByteBuf buffer)
	{
		final ByteBuf byteArray = new CompositeByteBuf(ByteBufAllocator.DEFAULT, true, EvaWireFormat.MaxData);

		buffer.readerIndex(0);

		while(buffer.readableBytes() > 0)
		{
			this.i = ((this.i + 1) % 255);
			this.j = ((this.j + this.sbox[this.i]) % 255);
			final int t = this.sbox[this.i];
			this.sbox[this.i] = this.sbox[this.j];
			this.sbox[this.j] = t;
			final int x = ((this.sbox[this.i] + this.sbox[this.j]) % 255);
			byteArray.writeByte((this.sbox[x] ^ buffer.readByte()));
		}

		byteArray.writerIndex(0);
		return byteArray;
	}

}
