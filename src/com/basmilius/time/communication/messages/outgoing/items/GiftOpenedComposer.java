package com.basmilius.time.communication.messages.outgoing.items;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GiftOpenedComposer extends MessageComposer
{

	private final UserItem gift;

	public GiftOpenedComposer(UserItem gift)
	{
		this.gift = gift;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.GiftOpened);
		response.appendString(this.gift.getBaseItem().getType());
		response.appendInt(this.gift.getBaseItem().getSpriteId());
		response.appendString(this.gift.getBaseItem().getItemName());
		response.appendInt(this.gift.getId());
		response.appendString(this.gift.getBaseItem().getType());
		response.appendBoolean(false);
		response.appendString(this.gift.getExtraData());
		return response;
	}

}
