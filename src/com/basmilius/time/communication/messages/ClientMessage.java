package com.basmilius.time.communication.messages;

import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

@SuppressWarnings("unused")
public class ClientMessage
{

	private final short messageId;
	private final ByteBuf messageBody;

	public ClientMessage(final short messageId, final ByteBuf buffer)
	{
		this.messageId = messageId;
		this.messageBody = buffer.retain();
	}

	public final ByteBuf getMessageBuffer()
	{
		return this.messageBody;
	}

	public final String getMessageBody()
	{
		ByteBuf buffer = this.messageBody.duplicate().retain().readerIndex(2);
		String consoleText = new String(buffer.readBytes(buffer.readableBytes()).array(), Charsets.US_ASCII);

		for (int i = 0; i <= 16; i++)
		{
			consoleText = consoleText.replace(Character.toString((char) i), "{" + i + "}");
		}

		return consoleText;
	}

	public final short getMessageId()
	{
		return this.messageId;
	}

	public final boolean readBoolean()
	{
		return this.messageBody.readBoolean();

	}

	public final byte[] readByteArray()
	{
		final int length = this.readInt();
		return this.messageBody.readBytes(length).array();
	}

	public final Node readNodeVector2()
	{
		return new Node(this.readInt(), this.readInt());
	}

	public final Node readNodeVector3()
	{
		return new Node(this.readInt(), this.readInt(), this.readInt());
	}

	public final int readInt()
	{
		return this.messageBody.readInt();
	}
	
	public final int[] readIntArray()
	{
		final int length = this.readInt();
		final int[] arr = new int[length];
		
		int x = 0;
		while(x < length)
		{
			arr[x] = this.readInt();
			x++;
		}
		
		return arr;
	}

	public final int readShort()
	{
		return this.messageBody.readShort();
	}

	public final String readString()
	{
		if (this.messageBody.readableBytes() >= 2)
		{
			final int length = this.readShort();

			if (this.messageBody.readableBytes() >= length)
			{
				byte[] data = this.messageBody.readBytes((length)).array();

				return new String(data, Charsets.UTF_8);
			}
		}

		return ":unknown:";
	}

	public final int readableBytes()
	{
		return this.messageBody.readableBytes();
	}
}