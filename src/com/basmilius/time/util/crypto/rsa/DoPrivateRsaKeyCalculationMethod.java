package com.basmilius.time.util.crypto.rsa;

import java.math.BigInteger;

public class DoPrivateRsaKeyCalculationMethod implements RsaKeyCalculationMethod
{
	
	@Override
	public BigInteger calculate(final RsaKey rsa, final BigInteger m)
	{
		return m.modPow(rsa.getD(), rsa.getN());
	}
	
}
