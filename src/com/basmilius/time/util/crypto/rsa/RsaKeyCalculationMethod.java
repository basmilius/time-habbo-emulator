package com.basmilius.time.util.crypto.rsa;

import java.math.BigInteger;

public interface RsaKeyCalculationMethod
{

	BigInteger calculate(final RsaKey rsa, final BigInteger m);
	
}
