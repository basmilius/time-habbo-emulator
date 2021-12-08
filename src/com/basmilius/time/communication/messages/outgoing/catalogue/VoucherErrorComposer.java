package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class VoucherErrorComposer extends MessageComposer
{

	private final String errorCode;

	public VoucherErrorComposer(String errorCode)
	{
		this.errorCode = errorCode;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.VoucherError);
		response.appendString(this.errorCode);
		return response;
	}

}
