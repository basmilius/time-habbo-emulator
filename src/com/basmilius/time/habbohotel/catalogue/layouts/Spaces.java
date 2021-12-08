package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class Spaces extends CataloguePage
{

	public Spaces(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendString("spaces_new");
		response.appendInt(1);
		response.appendString(this.getPageHeadline());
		response.appendInt(1);
		response.appendString(this.getPageText1());

		response.appendInt(this.getItems().size());

		Collections.sort(this.getItems(), (o1, o2) -> o1.getName().compareTo(o2.getName()));

		for (final CatalogueItem item : this.getItems())
		{
			item.serialize(response);
		}
	}

}
