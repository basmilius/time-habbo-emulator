package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.HabboCurrency;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CurrencyComposer extends MessageComposer
{

	private final Habbo _habbo;

	public CurrencyComposer(Habbo client)
	{
		this._habbo = client;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.Currencies);
		response.appendInt(this._habbo.getSettings().getCurrencies().getCurrencies().size());

		for (HabboCurrency currency : this._habbo.getSettings().getCurrencies().getCurrencies())
		{
			response.appendInt(currency.getCurrencyId());
			response.appendInt(currency.getCurrencyBalance());
		}
		return response;
	}

}
