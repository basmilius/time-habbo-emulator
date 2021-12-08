package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.habbohotel.catalogue.TargetedOffer;
import com.basmilius.time.habbohotel.items.Item;
import com.basmilius.time.util.TimeUtils;

public class TargetedOfferDataComposer extends MessageComposer
{

	private final TargetedOffer targetedOffer;

	public TargetedOfferDataComposer(final TargetedOffer targetedOffer)
	{
		this.targetedOffer = targetedOffer;
	}

	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.TargetedOfferData);
		response.appendInt(4); // 4 = minimized
		response.appendInt(this.targetedOffer.getId());
		response.appendString("identifier");
		response.appendString("_-3Mk");
		response.appendInt(this.targetedOffer.getCost());
		response.appendInt(0); // _-OVc
		response.appendInt(this.targetedOffer.getCurrencyId());
		response.appendInt(1); // purchaseLimit
		response.appendInt(this.targetedOffer.getOfferExpire() - TimeUtils.getUnixTimestamp());
		response.appendString(this.targetedOffer.getTitle());
		response.appendString(this.targetedOffer.getDescription());
		response.appendString(this.targetedOffer.getImage());
		response.appendInt(this.targetedOffer.getItems().size());
		for (final Item item : this.targetedOffer.getItems())
		{
			response.appendString(item.getItemName().split("\\*")[0]);
		}
		return response;
	}

}
