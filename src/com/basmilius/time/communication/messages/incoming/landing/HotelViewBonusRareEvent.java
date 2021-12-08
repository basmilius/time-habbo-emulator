package com.basmilius.time.communication.messages.incoming.landing;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.Item;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.landing.HotelViewBonusRareComposer;

@Event(id = Incoming.HotelViewBonusRare)
public class HotelViewBonusRareEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = Bootstrap.getEngine().getConfig().getInt("hotel.view.bonus.rare", 0);

		if (itemId == 0)
			return;

		final Item bonusRare = Bootstrap.getEngine().getGame().getItemsManager().getItem(itemId);

		connection.send(new HotelViewBonusRareComposer(bonusRare, (int) Math.ceil(Bootstrap.getEngine().getGame().getCatalogueManager().getItemByBase(bonusRare).getCostsCredits() * 1.5), (int) Math.ceil(Bootstrap.getEngine().getGame().getCatalogueManager().getItemByBase(bonusRare).getCostsCredits() * 1.5)));
	}

}
