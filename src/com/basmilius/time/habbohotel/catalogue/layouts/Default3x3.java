package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Default3x3 extends CataloguePage
{

	public Default3x3(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendString("default_3x3");
		response.appendInt(3);
		response.appendString(this.getPageHeadline());
		response.appendString(this.getPageTeaser());
		response.appendString(this.getPageSpecial());
		response.appendInt(3);
		response.appendString(this.getPageText1());
		response.appendString(this.getTextDetails());
		response.appendString(this.getTextTeaser());

		response.appendInt(this.getItems().size());
		for (final CatalogueItem item : this.getItems())
		{
			item.serialize(response);
		}
	}

}
