package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ISave;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HabboWardrobeLook extends ISave
{

	private int id;
	private Habbo habbo;
	private int slotId;
	private String figure;
	private String gender;

	{
		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.wardrobe", 30000));
	}

	public HabboWardrobeLook(ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this.slotId = result.getInt("slot_id");
		this.figure = result.getString("look");
		this.gender = result.getString("gender");
	}

	public HabboWardrobeLook(Habbo habbo, Integer slotId, String figure, String gender)
	{
		this.habbo = habbo;
		this.slotId = slotId;
		this.figure = figure;
		this.gender = gender;
		this.setNeedsInsert(true);
		this.setNeedsUpdate(true);
	}

	public int getId()
	{
		return this.id;
	}

	Habbo getHabbo()
	{
		return this.habbo;
	}

	public int getSlotId()
	{
		return this.slotId;
	}

	public String getFigure()
	{
		return this.figure;
	}

	public String getGender()
	{
		return this.gender;
	}

	public void updateData(String figure, String gender)
	{
		if (this.figure.equals(figure) && this.gender.equals(gender))
			return; // Same data.
		this.figure = figure;
		this.gender = gender;
		this.setNeedsUpdate(true);
	}

	@Override
	public void save() throws SQLException
	{
		if (this.getNeedsInsert())
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO users_wardrobe (user_id, slot_id, look, gender) VALUES (?, ?, ?, ?)");
			{
				statement.setInt(1, this.getHabbo().getId());
				statement.setInt(2, this.getSlotId());
				statement.setString(3, this.getFigure());
				statement.setString(4, this.getGender());
				statement.execute();
			}
			this.setNeedsInsert(false);
			this.setNeedsUpdate(false);
		}
		else if (this.getNeedsUpdate())
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE users_wardrobe SET look = ?, gender = ? WHERE user_id = ? AND slot_id = ?");
			{
				statement.setString(1, this.getFigure());
				statement.setString(2, this.getGender());
				statement.setInt(3, this.getHabbo().getId());
				statement.setInt(4, this.getSlotId());
				statement.execute();
			}
			this.setNeedsUpdate(false);
		}
	}

}
