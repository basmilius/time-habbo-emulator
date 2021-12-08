package com.basmilius.time.communication.messages;

import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public class QueuedComposers
{

	private final List<MessageComposer> composers;

	public QueuedComposers()
	{
		this.composers = new ArrayList<>();
	}

	public final void appendComposer(final MessageComposer composer)
	{
		this.composers.add(composer);
	}

	public final byte[] getBytes() throws Exception
	{
		final List<Byte> bytes = new ArrayList<>();

		for (final MessageComposer composer : this.composers)
		{
			final ByteBuf buffer = composer.compose().get();
			final byte[] bufferBytes = new byte[buffer.readableBytes()];
			buffer.getBytes(0, bufferBytes);
			for (final byte bufferByte : bufferBytes)
			{
				bytes.add(bufferByte);
			}
		}

		final byte[] b = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); i++)
		{
			b[i] = bytes.get(i);
		}
		return b;
	}

	public final void dispose()
	{
		this.composers.clear();
	}

}
