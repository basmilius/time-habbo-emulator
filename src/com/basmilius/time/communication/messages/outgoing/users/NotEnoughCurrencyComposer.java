package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class NotEnoughCurrencyComposer extends MessageComposer
{

	private boolean b1;
	private boolean customCurrency;
	private int currencyId;

	public NotEnoughCurrencyComposer(boolean b1, boolean customCurrency)
	{
		this.b1 = b1;
		this.customCurrency = customCurrency;
		this.currencyId = 0;
	}

	public NotEnoughCurrencyComposer(boolean b1, boolean customCurrency, int currencyId)
	{
		this.b1 = b1;
		this.customCurrency = customCurrency;
		this.currencyId = currencyId;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.NotEnoughCurrency);
		response.appendBoolean(this.b1);
		response.appendBoolean(this.customCurrency);
		if (this.currencyId > 0)
		{
			response.appendInt(this.currencyId);
		}
		return response;
	}

}
