package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuildForum extends CataloguePage
{

	public GuildForum(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendString("guild_forum");
		response.appendInt(0);
		response.appendInt(2);
		response.appendString(this.getPageText1());
		response.appendString(this.getPageText2());

		response.appendInt(this.getItems().size());
		for (final CatalogueItem item : this.getItems())
		{
			item.serialize(response);
		}
	}

}
