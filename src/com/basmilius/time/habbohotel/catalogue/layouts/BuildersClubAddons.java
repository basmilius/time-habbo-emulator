package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildersClubAddons extends CataloguePage
{

	public BuildersClubAddons(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendString("builders_club_addons");
		response.appendInt(0);
		response.appendInt(1);
		response.appendString(this.getPageText1());

		response.appendInt(this.getItems().size());
		for (final CatalogueItem item : this.getItems())
		{
			response.appendInt(item.getId());
			response.appendString(item.getName());
			response.appendBoolean(false);
			response.appendInt(item.getCostsCredits());
			response.appendInt(item.getCostsCurrency());
			response.appendInt(item.getCurrencyId());
			response.appendInt(0);
			response.appendInt(0);
			response.appendString("");
		}
	}

}
