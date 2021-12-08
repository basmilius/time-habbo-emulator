package com.basmilius.time.habbohotel.rooms.competition;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomCompetition
{

	private int id;
	private boolean enabled;
	private String name;
	private String[] requiredItems;

	public RoomCompetition(ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.enabled = result.getString("enabled").equals("1");
		this.name = result.getString("name");
		this.requiredItems = result.getString("required_items").split(",");
	}

	public int getId()
	{
		return this.id;
	}

	public boolean isEnabled()
	{
		return this.enabled;
	}

	public String getName()
	{
		return this.name;
	}

	public String[] getRequiredItems()
	{
		return this.requiredItems;
	}

	public List<Item> getRequiredUserItems()
	{
		final List<Item> items = new ArrayList<>();

		for (final String item : this.requiredItems)
		{
			items.add(Bootstrap.getEngine().getGame().getItemsManager().getItem(item));
		}

		return items;
	}
}
