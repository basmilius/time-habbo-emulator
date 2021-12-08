package com.basmilius.time.communication.messages.outgoing.landing;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.Item;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class HotelViewBonusRareComposer extends MessageComposer
{

	private Item bonusRare;
	private int requiredCredits;
	private int currentCredits;

	public HotelViewBonusRareComposer(Item bonusRare, int requiredCredits, int currentCredits)
	{
		this.bonusRare = bonusRare;
		this.requiredCredits = requiredCredits;
		this.currentCredits = currentCredits;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HotelViewBonusRare);
		response.appendString(this.bonusRare.getItemName());
		response.appendInt(Bootstrap.getEngine().getGame().getCatalogueManager().getItemByBase(this.bonusRare).getId());
		response.appendInt(this.requiredCredits);
		response.appendInt(this.currentCredits);
		return response;
	}

}
