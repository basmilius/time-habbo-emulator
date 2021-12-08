package com.basmilius.time.habbohotel.collections;

import com.basmilius.time.habbohotel.items.UserItem;
import com.google.common.collect.ForwardingList;

import java.util.ArrayList;
import java.util.List;

public final class UserItemsList extends ForwardingList<UserItem>
{

	private final List<UserItem> delegate;

	public UserItemsList(List<UserItem> list)
	{
		this.delegate = new ArrayList<>(list);
	}

	@Override
	protected final List<UserItem> delegate()
	{
		return delegate;
	}

}
