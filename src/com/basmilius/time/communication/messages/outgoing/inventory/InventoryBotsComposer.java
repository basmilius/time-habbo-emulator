package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.habbohotel.bots.Bot;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class InventoryBotsComposer extends MessageComposer
{

	private final List<Bot> bots;

	public InventoryBotsComposer(final List<Bot> bots)
	{
		this.bots = bots;
	}

	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.InventoryBots);
		response.appendInt(this.bots.size());
		for (final Bot bot : this.bots)
		{
			response.appendInt(bot.getId());
			response.appendString(bot.getName());
			response.appendString(bot.getMotto());
			response.appendString(bot.getGender().toLowerCase());
			response.appendString(bot.getLook());
		}
		return response;
	}

}
