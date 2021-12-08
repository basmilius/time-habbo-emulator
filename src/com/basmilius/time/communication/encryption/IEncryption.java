package com.basmilius.time.communication.encryption;

import io.netty.buffer.ByteBuf;

public interface IEncryption
{

	void init(final byte[] key);
	ByteBuf encipher(final ByteBuf buffer);
	ByteBuf decipher(final ByteBuf buffer);

}
