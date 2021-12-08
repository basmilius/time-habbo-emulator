package com.basmilius.time.communication.messages;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

public class ServerMessage implements Cloneable
{
	private int messageId;
	private ByteBuf messageBody;

	public ServerMessage()
	{
		this.messageId = -1;
		this.messageBody = null;
	}

	public ServerMessage init(final int id)
	{
		this.messageId = id;

		this.messageBody = new CompositeByteBuf(ByteBufAllocator.DEFAULT, true, 99999);
		this.messageBody.writeInt(0);
		this.messageBody.writeShort(this.messageId);

		return this;
	}

	public ServerMessage appendBody(final ISerialize obj)
	{
		obj.serialize(this);

		return this;
	}

	public ServerMessage appendBoolean(final boolean obj)
	{
		this.messageBody.writeBoolean(obj);

		return this;
	}

	public ServerMessage appendByte(final byte b)
	{
		this.messageBody.writeByte(b);

		return this;
	}

	public ServerMessage appendByte(final int b)
	{
		this.appendByte((byte) b);

		return this;
	}

	public ServerMessage appendInt(final int obj)
	{
		this.messageBody.writeInt(obj);

		return this;
	}

	public ServerMessage appendShort(final int obj)
	{
		this.appendShort((short) obj);

		return this;
	}

	public ServerMessage appendShort(final short obj)
	{
		this.messageBody.writeShort(obj);

		return this;
	}

	public ServerMessage appendString(final Object obj)
	{
		this.appendShort(obj.toString().getBytes().length);
		this.messageBody.writeBytes(obj.toString().getBytes());

		return this;
	}

	public String getBodyString() throws Exception
	{
		this.messageBody.readerIndex(0);
		ByteBuf bodeh = this.get().duplicate();

		String consoleText = bodeh.toString(Charsets.US_ASCII);

		for (int i = 0; i < 16; i++)
		{
			consoleText = consoleText.replace(Character.toString((char) i), "{" + i + "}");
		}

		return consoleText;
	}

	public int getMessageId()
	{
		return this.messageId;
	}

	public ByteBuf get() throws Exception
	{
		this.messageBody.readerIndex(0);
		final int messageLength = this.messageBody.readableBytes() - 4;

		this.messageBody.setInt(0, messageLength);

		if (this.messageBody.readableBytes() != (messageLength + 4) && messageLength > 0)
			throw new Exception("Wrong ServerMessage size!");

		return this.messageBody;
	}

	public void setReaderIndex(final int readerIndex)
	{
		this.messageBody.readerIndex(readerIndex);
	}
}
