package com.basmilius.time.util.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class DiffieHellman
{
	
	public final int bitLength = 32;
	
	private BigInteger prime;
	private BigInteger generator;
	
	public BigInteger publicKey;
	public BigInteger privateKey;
	
	public DiffieHellman()
	{
		this.publicKey = BigInteger.ZERO;
		
		final SecureRandom random = new SecureRandom();
		
		while (this.publicKey.equals(BigInteger.ZERO))
		{
			this.prime = new BigInteger(this.bitLength, 10, random);
			this.generator = new BigInteger(this.bitLength, 10, random);
			
			final byte[] bytes = new byte[this.bitLength / 8];
			random.nextBytes(bytes);
			this.privateKey = new BigInteger(bytes);
			
			if (this.generator.intValue() > this.prime.intValue())
			{
				final BigInteger t = this.prime;
				this.prime = this.generator;
				this.generator = t;
			}
			
			this.publicKey = this.generator.modPow(this.privateKey, this.prime);
		}
	}
	
	public final BigInteger calculateSharedKey(final BigInteger m)
	{
		return m.modPow(this.privateKey, this.prime);
	}
	
	public final int getBitLength()
	{
		return this.bitLength;
	}
	
	public final BigInteger getPrime()
	{
		return this.prime;
	}
	
	public final BigInteger getGenerator()
	{
		return this.generator;
	}
	
	public final BigInteger getPublicKey()
	{
		return this.publicKey;
	}
	
	public final BigInteger getPrivateKey()
	{
		return this.privateKey;
	}
	
}
