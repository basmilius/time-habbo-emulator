package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FrontPage extends CataloguePage
{

	public FrontPage(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendString("frontpage4");
		response.appendInt(2);
		response.appendString(this.getPageHeadline());
		response.appendString(this.getPageTeaser());
		response.appendInt(2);
		response.appendString(this.getPageText1());
		response.appendString("Heb je een credit-code? Voer deze dan hier in!");
		response.appendInt(0);
	}

}
