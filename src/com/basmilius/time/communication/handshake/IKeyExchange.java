package com.basmilius.time.communication.handshake;

public interface IKeyExchange
{

	boolean init(final String privateKey, final int radix);
	String generateSharedKey(final String clientKey, final int radix);
	String getSharedKey(final int radix);
	String getPublicKey(final int radix);

}
