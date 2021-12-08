package com.basmilius.time.communication.messages.outgoing.handshake;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.habbohotel.abstracts.IPixelException;
import com.basmilius.time.util.TimeUtils;

import java.text.SimpleDateFormat;

public class GenericErrorMessageComposer extends MessageComposer
{

	private final int messageId;
	private final int errorCode;

	public GenericErrorMessageComposer (IPixelException exception)
	{
		this.errorCode = exception.getErrorCode();
		this.messageId = this.errorCode;
	}

	public GenericErrorMessageComposer (int errorCode, int messageId)
	{
		this.errorCode = errorCode;
		this.messageId = messageId;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.ServerError);
		response.appendInt(this.messageId);
		response.appendInt(this.errorCode);
		response.appendString(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(TimeUtils.getUnixTimestamp()));
		return response;
	}

}
