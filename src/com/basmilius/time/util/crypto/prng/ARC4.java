package com.basmilius.time.util.crypto.prng;

public class ARC4
{
	
	public final int poolSize = 0x0100;
	
	private final int[] bytes;
	
	private int i, j;
	
	public ARC4()
	{
		bytes = new int[this.poolSize];
	}

	public final void initialize(final byte[] key)
	{
		while (this.i < this.poolSize)
		{
			this.bytes[this.i] = (this.i & 0xFF);
			this.i++;
		}
		this.i = 0;
		this.j = 0;
		while(this.i < this.poolSize)
		{
			this.j = (((this.j + this.bytes[this.i]) + (key[(this.i % key.length)] & this.poolSize)) % this.poolSize);
			this.swap(this.i, this.j);
			this.i++;
		}
		this.i = 0;
		this.j = 0;
	}
	
	public final byte[] decrypt(final byte[] data)
	{
		return this.encrypt(data);
	}
	
	public final byte[] encrypt(final byte[] data)
	{
		final byte[] d = new byte[data.length];
		
		int i = 0;
		while (i < data.length)
		{
			this.i = (++this.i % this.poolSize);
			this.j = ((this.j + this.bytes[this.i]) % this.poolSize);
			this.swap(this.i, this.j);
			d[i] = (byte)((this.bytes[((this.bytes[this.i] + this.bytes[this.j]) % this.poolSize)] ^ data[i]));
			i++;
		}
		
		return d;
	}
	
	private void swap(final int i, final int j)
	{
		final int t = this.bytes[i];
		this.bytes[i] = this.bytes[j];
		this.bytes[j] = t;
	}
	
}
