package com.basmilius.time.habbohotel.habbo.inventory;

import com.basmilius.time.habbohotel.habbo.Habbo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HabboEffect
{

	private final Habbo habbo;
	private int id;
	private int effectId;
	private int count;
	private int timeTotal;
	private int timeLeft;
	private boolean isActivated;

	public HabboEffect(Habbo habbo, ResultSet result) throws SQLException
	{
		this.habbo = habbo;
		this.id = result.getInt("id");
		this.effectId = result.getInt("effect_id");
		this.count = 1;
		this.timeTotal = result.getInt("time_total");
		this.timeLeft = result.getInt("time_left");
		this.isActivated = (result.getString("activated").equals("1"));
	}

	public int getId()
	{
		return this.id;
	}

	public int getEffectId()
	{
		return this.effectId;
	}

	public int getCount()
	{
		return this.count;
	}

	public int getTimeTotal()
	{
		return this.timeTotal;
	}

	public int getTimeLeft()
	{
		return this.timeLeft;
	}

	public boolean getIsActivated()
	{
		return this.isActivated;
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public void activate()
	{
		this.isActivated = true;
	}

	public void addEffect(HabboEffect effect)
	{
		this.count++;
		this.isActivated = (this.isActivated || effect.isActivated);
		this.timeLeft += effect.timeLeft;
		this.timeTotal += effect.timeTotal;
	}

}
