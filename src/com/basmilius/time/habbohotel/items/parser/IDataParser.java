package com.basmilius.time.habbohotel.items.parser;

import com.basmilius.time.habbohotel.items.UserItem;

import java.util.Map;

public abstract class IDataParser
{

	private UserItem item;

	public IDataParser(UserItem item)
	{
		this.item = item;
	}

	public abstract Map<String, String> parse();

	public UserItem getItem()
	{
		return this.item;
	}

}
