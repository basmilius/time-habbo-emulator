package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabboWardrobe
{

	private final Habbo habbo;
	private final List<HabboWardrobeLook> looks;

	public HabboWardrobe(final Habbo habbo) throws SQLException
	{
		this.habbo = habbo;
		this.looks = new ArrayList<>();

		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users_wardrobe WHERE user_id = ? AND look != '' ORDER BY slot_id");
		{
			statement.setInt(1, this.habbo.getId());
			final ResultSet result = statement.executeQuery();

			while (result.next())
			{
				this.looks.add(new HabboWardrobeLook(result));
			}
		}
	}

	public final Habbo getHabbo()
	{
		return this.habbo;
	}

	public final List<HabboWardrobeLook> getLooks()
	{
		return this.looks;
	}

	public final HabboWardrobeLook getSlot(Integer slotId)
	{
		for (final HabboWardrobeLook look : this.looks)
		{
			if (look.getSlotId() == slotId)
				return look;
		}
		return null;
	}

	public final boolean getSlotExists(Integer slotId)
	{
		for (final HabboWardrobeLook look : this.looks)
		{
			if (look.getSlotId() == slotId)
				return true;
		}
		return false;
	}

	public final void setSlotData(final int slotId, final String figure, final String gender)
	{
		if (this.getSlotExists(slotId))
		{
			this.getSlot(slotId).updateData(figure, gender);
		}
		else
		{
			this.looks.add(new HabboWardrobeLook(this.getHabbo(), slotId, figure, gender));
		}
	}

}
