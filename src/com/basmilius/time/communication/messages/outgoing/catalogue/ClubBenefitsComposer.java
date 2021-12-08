package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ClubBenefitsComposer extends MessageComposer
{

	private final CataloguePage _page;

	public ClubBenefitsComposer(CataloguePage _page)
	{
		this._page = _page;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.ClubBenefits);
		response.appendInt(1); // available in x days
		response.appendInt(4); // you can choose x bennefits
		response.appendInt(this._page.getItems().size());
		for (CatalogueItem item : this._page.getItems())
		{
			response.appendInt(item.getId());
			response.appendString(item.getBaseItems().get(0).getItemName());
			response.appendBoolean(false);
			response.appendInt(1); // ?
			response.appendInt(0); // ?
			response.appendInt(0); // ?
			response.appendBoolean(true); // ?
			item.serializeData(response);
			response.appendInt(0);
			response.appendBoolean(true);
		}
		response.appendInt(this._page.getItems().size());
		for (CatalogueItem item : this._page.getItems())
		{
			response.appendInt(item.getId());
			response.appendBoolean(true);
			response.appendInt(15);
			response.appendBoolean(false);
		}
		return response;
	}

}
