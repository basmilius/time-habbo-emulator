package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClubBuy extends CataloguePage
{

	public ClubBuy(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		response.appendString("vip_buy");
		response.appendInt(2);
		response.appendString(this.getPageHeadline());
		response.appendString(this.getPageTeaser());
		response.appendInt(0);
		response.appendInt(0);
	}

}
