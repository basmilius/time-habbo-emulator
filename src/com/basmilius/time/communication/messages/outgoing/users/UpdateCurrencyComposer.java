package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class UpdateCurrencyComposer extends MessageComposer
{

	private final int total;
	private final int amount;
	private final int type;

	public UpdateCurrencyComposer(Integer total, Integer amount, Integer type)
	{
		this.total = total;
		this.amount = amount;
		this.type = type;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UpdateCurrency);
		response.appendInt(this.total);
		response.appendInt(this.amount);
		response.appendInt(this.type);
		return response;
	}

}
