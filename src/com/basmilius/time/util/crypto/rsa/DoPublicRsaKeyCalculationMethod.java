package com.basmilius.time.util.crypto.rsa;

import java.math.BigInteger;

public class DoPublicRsaKeyCalculationMethod implements RsaKeyCalculationMethod
{
	
	@Override
	public BigInteger calculate(final RsaKey rsa, final BigInteger m)
	{
		return m.modPow(BigInteger.valueOf(rsa.getE()), rsa.getN());
	}
	
}
