package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.habbohotel.enums.CatalogueMode;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CataloguePageComposer extends MessageComposer
{

	private final CatalogueMode mode;
	private final CataloguePage page;

	public CataloguePageComposer(CataloguePage page, CatalogueMode mode)
	{
		this.mode = mode;
		this.page = page;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.CataloguePage);
		response.appendInt(page.getId());
		response.appendString(this.mode.toString());
		this.page.serialize(response);
		response.appendInt(-1);
		response.appendBoolean(false);
		return response;
	}

}
