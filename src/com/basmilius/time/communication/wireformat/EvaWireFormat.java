package com.basmilius.time.communication.wireformat;

import com.basmilius.core.IDisposable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import java.util.List;

// TODO This class isn't used yet. In the future we're using the Habbo Connection Management.
// TODO EvaWireFormat is for formatting messages to the client.
public class EvaWireFormat implements IWireFormat, IDisposable
{

	public static int MaxData = 128 * 1024; // 131072

	private boolean disposed;

	public EvaWireFormat()
	{
		this.disposed = false;
	}

	public final ByteBuf encode(final short messageId, final Object[] messageData)
	{
		final ByteBuf message = new CompositeByteBuf(ByteBufAllocator.DEFAULT, true, MaxData);

		message.writeInt(0);
		message.writeShort(messageId);

		for (final Object data : messageData)
		{
			if (String.class.isInstance(data))
			{
				final String stringData = data.toString();
				message.writeShort(stringData.length());
				message.writeBytes(stringData.getBytes());
			}
			else if (int.class.isInstance(data))
			{
				message.writeInt((int) data);
			}
			else if (boolean.class.isInstance(data))
			{
				message.writeBoolean((boolean) data);
			}
			else if (short.class.isInstance(data))
			{
				message.writeShort((short) data);
			}
			else if (byte[].class.isInstance(data))
			{
				final byte[] bytes = (byte[]) data;
				message.writeInt(bytes.length);
				message.writeBytes(bytes);
			}
		}

		message.readerIndex(0);
		message.writerIndex(0);
		final int length = message.readableBytes();
		message.writeInt(length - 4);
		message.readerIndex(length);
		message.writerIndex(length);

		return message;
	}

	public final List<ByteBuf> splitMessages(final byte[] messageData)
	{
		return null;
	}

	@Override
	public final void dispose ()
	{
		this.disposed = true;
	}

	@Override
	public final boolean isDisposed ()
	{
		return this.disposed;
	}

}
