package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.habbohotel.enums.CatalogueMode;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class CataloguePagesComposer extends MessageComposer
{

	private final CatalogueMode mode;
	private final Habbo habbo;

	public CataloguePagesComposer(Habbo habbo, CatalogueMode mode)
	{
		this.habbo = habbo;
		this.mode = mode;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.CataloguePages);
		response.appendBoolean(true);
		response.appendInt(0);
		response.appendInt(0);
		response.appendString("root");
		response.appendString("");
		response.appendInt(0);
		response.appendInt(Bootstrap.getEngine().getGame().getCatalogueManager().getPages(-1, this.habbo, this.mode).size());

		for (CataloguePage category : Bootstrap.getEngine().getGame().getCatalogueManager().getPages(-1, this.habbo, this.mode))
		{
			response.appendBoolean(category.getIsVisible());
			response.appendInt(category.getIconImage());
			response.appendInt(category.getId());
			response.appendString((category.getCaptionSave().isEmpty()) ? category.getCaption().toLowerCase().replace(" ", "_") : category.getCaptionSave());
			response.appendString(category.getCaption());
			response.appendInt(category.getOfferItems().size());
			for (final CatalogueItem item : category.getOfferItems())
			{
				response.appendInt(item.getBaseItems().get(0).getOfferId());
			}
			response.appendInt(Bootstrap.getEngine().getGame().getCatalogueManager().getPages(category.getId(), this.habbo, this.mode).size());

			for (CataloguePage page : Bootstrap.getEngine().getGame().getCatalogueManager().getPages(category.getId(), this.habbo, this.mode))
			{
				response.appendBoolean(page.getIsVisible());
				response.appendInt(page.getIconImage());
				response.appendInt(page.getId());
				response.appendString((page.getCaptionSave().isEmpty()) ? page.getCaption().toLowerCase().replace(" ", "_") : page.getCaptionSave());
				response.appendString(page.getCaption());
				response.appendInt(page.getOfferItems().size());
				for (final CatalogueItem item : page.getOfferItems())
				{
					response.appendInt(item.getBaseItems().get(0).getOfferId());
				}
				response.appendInt(0);
			}
		}
		response.appendBoolean(false);
		response.appendString(this.mode.toString());
		return response;
	}

}
