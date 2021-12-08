package com.basmilius.time.habbohotel.habbo;

import java.util.ArrayList;
import java.util.List;

public class HabboIgnores
{

	private final Habbo habbo;
	private final List<Habbo> habbosToIgnore;

	public HabboIgnores(Habbo habbo)
	{
		this.habbo = habbo;
		this.habbosToIgnore = new ArrayList<>();
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public void addHabboToIgnore(Habbo habbo)
	{
		this.habbosToIgnore.add(habbo);
	}

	public void removeHabboToIgnore(Habbo habbo)
	{
		this.habbosToIgnore.remove(habbo);
	}

	public List<Habbo> getIgnores()
	{
		return this.habbosToIgnore;
	}

}
