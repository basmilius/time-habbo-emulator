package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomAds extends CataloguePage
{

	public RoomAds(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendString("roomads");
		response.appendInt(2);
		response.appendString(this.getPageHeadline());
		response.appendString("");
		response.appendInt(2);
		response.appendString("");
		response.appendString("");

		response.appendInt(this.getItems().size());
		for (final CatalogueItem item : this.getItems())
		{
			item.serialize(response);
		}
	}

}
