package com.basmilius.time.communication.messages.outgoing.landing;

import com.basmilius.time.habbohotel.reception.ReceptionSpotlightItem;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class HotelViewDataComposer extends MessageComposer
{

	private final List<ReceptionSpotlightItem> _spotlightItems;

	public HotelViewDataComposer(List<ReceptionSpotlightItem> _spotlightItems)
	{
		this._spotlightItems = _spotlightItems;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.HotelViewData);
		response.appendInt(this._spotlightItems.size());
		for (ReceptionSpotlightItem _spotlightItem : this._spotlightItems)
		{
			_spotlightItem.serialize(response);
		}
		return response;
	}

}
