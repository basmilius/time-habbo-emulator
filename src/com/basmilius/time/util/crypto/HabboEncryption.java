package com.basmilius.time.util.crypto;

import com.basmilius.time.util.crypto.prng.ARC4;
import com.basmilius.time.util.crypto.rsa.RsaKey;

import java.math.BigInteger;

public class HabboEncryption
{

	private boolean initialized;
	
	private final ARC4 arc4;
	private final DiffieHellman diffieHellman;
	private final RsaKey rsa;
	
	public HabboEncryption()
	{
		this.initialized = false;
		this.arc4 = new ARC4();
		this.diffieHellman = new DiffieHellman();
		this.rsa = RsaKey.parsePrivateKey(HabboEncryptionKeys.N, HabboEncryptionKeys.E, HabboEncryptionKeys.D);
	}
	
	private String getRsaStringEncrypted(final String message)
	{
		try
		{
			byte[] m = message.getBytes();
			byte[] c = this.rsa.sign(m);
			
			return BitConverter.byteArrayToString(c);
		}
		catch(final Exception e)
		{
			e.printStackTrace();
			return "0";
		}
	}
	
	public final ARC4 getArc4()
	{
		return this.arc4;
	}
	
	public final String getRsaDHPrime()
	{
		return this.getRsaStringEncrypted(this.diffieHellman.getPrime().toString(10));
	}
	
	public final String getRsaDHGenerator()
	{
		return this.getRsaStringEncrypted(this.diffieHellman.getGenerator().toString(10));
	}
	
	public final String getRsaDHPublicKey()
	{
		return this.getRsaStringEncrypted(this.diffieHellman.getPublicKey().toString(10));
	}
	
	public final BigInteger initialize(final String publicKey)
	{
		final byte[] bytes = BitConverter.StringToByteArray(publicKey);
		final byte[] keyBytes = this.rsa.verify(bytes);
		return this.diffieHellman.calculateSharedKey(new BigInteger(keyBytes));
	}
	
	public final void initializeArc4(final byte[] key)
	{
		this.arc4.initialize(key);
		this.initialized = true;
	}
	
	public final boolean isInitialized()
	{
		return this.initialized;
	}
}
