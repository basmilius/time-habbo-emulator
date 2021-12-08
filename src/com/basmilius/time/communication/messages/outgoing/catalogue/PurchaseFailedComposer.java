package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PurchaseFailedComposer extends MessageComposer
{

	public static final int PURCHASE_FAILED = 0;
	public static final int ALREADY_HAVE_BADGE = 1;

	private final int _errorCode;

	public PurchaseFailedComposer(int _errorCode)
	{
		this._errorCode = _errorCode;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.PurchaseFailed);
		response.appendInt(this._errorCode);
		return response;
	}

}
