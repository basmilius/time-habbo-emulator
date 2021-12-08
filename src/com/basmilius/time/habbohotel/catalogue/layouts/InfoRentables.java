package com.basmilius.time.habbohotel.catalogue.layouts;

import com.basmilius.time.habbohotel.catalogue.CataloguePage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoRentables extends CataloguePage
{

	public InfoRentables(final ResultSet result) throws SQLException
	{
		super(result);
	}

	@Override
	public void serialize(final ServerMessage response)
	{
		String[] data = this.getPageText1().split("\\|\\|");
		response.appendString("info_rentables");
		response.appendInt(1);
		response.appendString(this.getPageHeadline());
		response.appendInt(data.length);
		for (final String d : data)
		{
			response.appendString(d);
		}
		response.appendInt(0);
	}

}
