package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Guilds extends CataloguePage
{

	public Guilds(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendString("guild_frontpage");
		response.appendInt(2);
		response.appendString(this.getPageHeadline());
		response.appendString(this.getPageTeaser());
		response.appendInt(3);
		response.appendString(this.getPageText1());
		response.appendString(this.getTextDetails());
		response.appendString(this.getTextTeaser());
		response.appendInt(0);
	}

}
