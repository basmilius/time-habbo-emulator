package com.basmilius.time.util.crypto.rsa;

import com.basmilius.time.Bootstrap;

import java.math.BigInteger;

public class RsaKey
{
	
	private boolean canDecrypt;
	private boolean canEncrypt;
	
	private final BigInteger n;
	private final int e;
	private final BigInteger d;
	private final BigInteger p;
	private final BigInteger q;
	private final BigInteger dmp1;
	private final BigInteger dmq1;
	private final BigInteger coeff;

	public RsaKey(final BigInteger n, final int e, final BigInteger d)
	{
		this.n = n;
		this.e = e;
		this.d = d;
		this.p = BigInteger.ZERO;
		this.q = BigInteger.ZERO;
		this.dmp1 = BigInteger.ZERO;
		this.dmq1 = BigInteger.ZERO;
		this.coeff = BigInteger.ZERO;
		
		this.canEncrypt = (!this.n.equals(BigInteger.ZERO) && this.e != 0);
		this.canDecrypt = (this.canEncrypt && !this.d.equals(BigInteger.ZERO));
	}
	
	public static RsaKey parsePrivateKey(final String n, final String e, final String d)
	{
		return new RsaKey(new BigInteger(n, 16), Integer.parseInt(e), new BigInteger(d, 16));
	}
	
	public final byte[] encrypt(final byte[] data)
	{
		return this.doEncrypt(new DoPublicRsaKeyCalculationMethod(), data);
	}

	public final byte[] decrypt(final byte[] data)
	{
		return this.doDecrypt(new DoPublicRsaKeyCalculationMethod(), data);
	}

	public final byte[] sign(final byte[] data)
	{
		return this.doEncrypt(new DoPrivateRsaKeyCalculationMethod(), data);
	}

	public final byte[] verify(final byte[] data)
	{
		return this.doDecrypt(new DoPrivateRsaKeyCalculationMethod(), data);
	}
	
	private byte[] doDecrypt(final RsaKeyCalculationMethod calculationMethod, final byte[] data)
	{
		final BigInteger c = new BigInteger(data);
		final BigInteger m = calculationMethod.calculate(this, c);
		
		if (m.equals(BigInteger.ZERO))
			return null;
		
		final int blockSize = this.getBlockSize();
		return this.pkcs1unpad(m.toByteArray(), blockSize);
	}
	
	private byte[] doEncrypt(final RsaKeyCalculationMethod calculationMethod, final byte[] data)
	{
		final int blockSize = this.getBlockSize();
		final byte[] paddedBytes = this.pkcs1pad(data, blockSize);
		final BigInteger m = new BigInteger(paddedBytes);
		
		if (m.equals(BigInteger.ZERO))
			return null;
		
		final BigInteger c = calculationMethod.calculate(this, m);
		if (c.equals(BigInteger.ZERO))
			return null;
		
		return c.toByteArray();
	}
	
	private byte[] pkcs1pad(final byte[] data, int blockSize)
	{
		final byte[] bytes = new byte[blockSize];
		int i = data.length - 1;
		
		while (i >= 0 && blockSize > 11)
		{
			bytes[--blockSize] = data[i--];
		}
		
		bytes[--blockSize] = 0;
		
		while(blockSize > 2)
		{
			byte x = (byte)0xFF;
			bytes[--blockSize] = x;
		}
		
		bytes[--blockSize] = (byte) 1;
		bytes[--blockSize] = (byte) 0;
		
		return bytes;
	}
	
	private byte[] pkcs1unpad(final byte[] data, int blockSize)
	{
		int i = 0;
		while (i < data.length && data[i] == 0)
			++i;
		
		if (data.length - i != blockSize - 1 || data[i] > 2)
			return null;
		
		++i;
		
		while (data[i] != 0)
		{
			if (++i >= data.length)
			{
				Bootstrap.getEngine().getLogging().logErrorLine(String.format("PKCS#1 unpad: i=%d, data[i-1]!=0 (=%s)", i, Byte.toString(data[i - 1])));
			}
		}
		
		final byte[] bytes = new byte[data.length - i - 1];
		for (int p = 0; ++i < data.length; p++)
		{
			bytes[p] = data[i];
		}
		
		return bytes;
	}
	
	public final int getBlockSize()
	{
		return this.n.toByteArray().length - 1;
	}
	
	public final boolean isCanDecrypt()
	{
		return this.canDecrypt;
	}
	
	public final boolean isCanEncrypt()
	{
		return this.canEncrypt;
	}
	
	public final BigInteger getN()
	{
		return this.n;
	}
	
	public final int getE()
	{
		return this.e;
	}
	
	public final BigInteger getD()
	{
		return this.d;
	}
	
	public final BigInteger getP()
	{
		return this.p;
	}
	
	public final BigInteger getQ()
	{
		return this.q;
	}
	
	public final BigInteger getDmp1()
	{
		return this.dmp1;
	}
	
	public final BigInteger getDmq1()
	{
		return this.dmq1;
	}
	
	public final BigInteger getCoeff()
	{
		return this.coeff;
	}
}
