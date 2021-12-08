package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PurchaseNotAllowedComposer extends MessageComposer
{

	public static final int UNKNOWN = 0;
	public static final int HC_NEEDED = 1;

	private final int _errorCode;

	public PurchaseNotAllowedComposer(int _errorCode)
	{
		this._errorCode = _errorCode;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.PurchaseNotAllowed);
		response.appendInt(this._errorCode);
		return response;
	}

}
