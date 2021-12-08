package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class MarketPlaceResultComposer extends MessageComposer
{

	public static final int OFFER_PLACED = 1;
	public static final int OFFER_ERROR = 2;
	public static final int MARKETPLACE_CLOSED = 3;
	public static final int FURNI_CANT_BE_IN_CATALOG = 4;
	public static final int LIMITED_EDITION_CANT = 6;

	private final int _result;

	public MarketPlaceResultComposer(int _result)
	{
		this._result = _result;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.MarketPlaceResult);
		response.appendInt(this._result);
		return response;
	}

}
