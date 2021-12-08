package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class MarketplaceCanOfferComposer extends MessageComposer
{

	public static final int CAN_OFFER = 1;
	public static final int TRADING_LOCK = 2;
	public static final int NO_TRADING_PASS = 3;
	public static final int NO_ADVERTS = 4;

	private int result;

	public MarketplaceCanOfferComposer(int result)
	{
		this.result = result;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.MarketCanOffer);
		response.appendInt(this.result);
		response.appendInt(0);
		return response;
	}

}
