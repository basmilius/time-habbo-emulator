package com.basmilius.time.communication.wireformat;

import io.netty.buffer.ByteBuf;

import java.util.List;

public interface IWireFormat
{

	ByteBuf encode(final short messageId, final Object[] messageData);
	List<ByteBuf> splitMessages(final byte[] messageData);

}
