package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildersClubFrontPage extends CataloguePage
{

	public BuildersClubFrontPage(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendString("builders_club_frontpage");
		response.appendInt(0);
		response.appendInt(1);
		response.appendString(this.getPageText1());
		response.appendInt(0);
	}

}
