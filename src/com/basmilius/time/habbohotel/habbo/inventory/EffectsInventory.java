package com.basmilius.time.habbohotel.habbo.inventory;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EffectsInventory
{

	private final Habbo habbo;
	private final List<HabboEffect> effects;

	public EffectsInventory(Habbo habbo)
	{
		this.habbo = habbo;
		this.effects = new ArrayList<>();

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users_effects WHERE user_id = ? AND time_left > 0");
			{
				if (statement != null)
				{
					statement.setInt(1, this.habbo.getId());
					ResultSet result = statement.executeQuery();

					while (result.next())
					{
						this.addEffect(new HabboEffect(this.habbo, result));
					}
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public List<HabboEffect> getEffects()
	{
		return this.effects;
	}

	public boolean containsEffect(int effectId)
	{
		for (HabboEffect e : this.effects)
		{
			if (e.getEffectId() == effectId)
				return true;
		}
		return false;
	}

	public HabboEffect getEffect(int effectId)
	{
		for (HabboEffect e : this.effects)
		{
			if (e.getEffectId() == effectId)
				return e;
		}
		return null;
	}

	public void addEffect(HabboEffect effect)
	{
		if (this.containsEffect(effect.getEffectId()))
		{
			this.getEffect(effect.getEffectId()).addEffect(effect);
		}
		else
		{
			this.effects.add(effect);
		}
	}

	public void dispose()
	{
		this.effects.clear();
	}

}
