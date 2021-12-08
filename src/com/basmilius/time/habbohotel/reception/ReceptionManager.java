package com.basmilius.time.habbohotel.reception;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceptionManager extends IManager
{

	private final List<ReceptionSpotlightItem> _spotlightItems;

	public ReceptionManager()
	{
		this._spotlightItems = new ArrayList<>();
	}

	@Override
	public void initialize()
	{
		try
		{
			PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM hotelview_spotlight");
			{
				ResultSet result = statement.executeQuery();

				while (result.next())
				{
					this._spotlightItems.add(new ReceptionSpotlightItem(result));
				}
			}
		}
		catch (SQLException ignored)
		{

		}
	}

	public List<ReceptionSpotlightItem> getSpotlightItems()
	{
		return this._spotlightItems;
	}

	@Override
	public void dispose()
	{
		this._spotlightItems.clear();
	}

}
