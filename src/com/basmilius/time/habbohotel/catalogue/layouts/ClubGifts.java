package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubGifts extends CataloguePage
{

	public ClubGifts(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendString("club_gifts");
		response.appendInt(1);
		response.appendString(this.getPageHeadline());
		response.appendInt(1);
		response.appendString(this.getPageText1());
		response.appendInt(0);
	}

}
