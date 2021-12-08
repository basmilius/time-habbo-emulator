package com.basmilius.time.collections;

import com.basmilius.time.habbohotel.abstracts.IManager;
import com.google.common.collect.ForwardingList;

import java.util.ArrayList;
import java.util.List;

public final class ManagersList extends ForwardingList<IManager>
{

	private final List<IManager> delegate;

	public ManagersList(List<IManager> list1)
	{
		this.delegate = new ArrayList<>(list1);
	}

	@Override
	protected final List<IManager> delegate()
	{
		return delegate;
	}

}
