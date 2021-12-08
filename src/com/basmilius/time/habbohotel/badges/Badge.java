package com.basmilius.time.habbohotel.badges;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ISave;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.HabboAchievement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A in-game user Badge.
 */
public class Badge extends ISave
{

	private final Habbo habbo;
	private final String badgeCode;

	{
		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.users.badges", 240000));
	}

	private int id;
	private int slotId;

	/**
	 * Constructor.
	 *
	 * @param result Data from the database/
	 * @throws SQLException
	 */
	public Badge(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.slotId = result.getInt("slot_id");
		this.habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this.badgeCode = result.getString("badge_code");
	}

	/**
	 * Constructor.
	 *
	 * @param slotId    Slot ID.
	 * @param habbo     The Habbo.
	 * @param badgeCode The Badge code.
	 */
	public Badge(final int slotId, final Habbo habbo, final String badgeCode)
	{
		this.id = Bootstrap.getEngine().getGame().getBadgeManager().getLatestId();
		this.slotId = slotId;
		this.habbo = habbo;
		this.badgeCode = badgeCode;

		this.setNeedsUpdate(true);
		this.setNeedsInsert(true);
	}

	/**
	 * Gets the ID for this Badge.
	 *
	 * @return int
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * Gets the slot for this Badge.
	 *
	 * @return int
	 */
	public int getSlotId()
	{
		return this.slotId;
	}

	/**
	 * Sets the slot id of this badge.
	 *
	 * @param slotId New slot ID.
	 */
	public void setSlotId(final int slotId)
	{
		this.slotId = slotId;

		this.setNeedsUpdate(true);
	}

	/**
	 * Gets the Habbo that has this Badge.
	 *
	 * @return Habbo
	 */
	public Habbo getHabbo()
	{
		return this.habbo;
	}

	/**
	 * Gets the Badge code.
	 *
	 * @return String
	 */
	public String getBadgeCode()
	{
		if (this.isAchievement() && this.getAchievement() != null && this.getAchievement().getAchievement() != null && this.getAchievement().getAchievement().getTotalLevels() > 1)
			return this.badgeCode + this.getAchievement().getUserLevel();
		else if (this.isAchievement() && this.getAchievement() != null && this.getAchievement().getAchievement() != null)
		{
			return this.badgeCode + "1";
		}
		return this.badgeCode;
	}

	/**
	 * Gets if this Badge is an Achievement Badge.
	 *
	 * @return boolean
	 */
	public boolean isAchievement()
	{
		return Bootstrap.getEngine().getGame().getAchievementsManager().getAchievement(this.badgeCode) != null;
	}

	/**
	 * Gets the Achievement.
	 *
	 * @return HabboAchievement
	 */
	private HabboAchievement getAchievement()
	{
		if (this.isAchievement())
			return this.habbo.getAchievements().getAchievement(Bootstrap.getEngine().getGame().getAchievementsManager().getAchievement(this.badgeCode));
		return null;
	}

	/**
	 * Executes when needs save.
	 *
	 * @throws SQLException
	 */
	@Override
	protected void save() throws SQLException
	{
		int i = 0;
		PreparedStatement statement;

		if (this.getNeedsInsert())
		{
			statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO users_badges (user_id, slot_id, badge_code) VALUES (?, ?, ?)");
			statement.setInt(++i, this.habbo.getId());
			statement.setInt(++i, this.slotId);
			statement.setString(++i, this.badgeCode);
			statement.execute();

			ResultSet keys = statement.getGeneratedKeys();
			keys.next();
			this.id = keys.getInt(1);
		}
		else if (this.getNeedsUpdate())
		{
			statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE users_badges SET slot_id = ? WHERE badge_code = ? AND user_id = ?");
			statement.setInt(++i, this.slotId);
			statement.setString(++i, this.badgeCode);
			statement.setInt(++i, this.habbo.getId());
			statement.executeUpdate();
		}

		this.setNeedsInsert(false);
		this.setNeedsUpdate(false);
	}
}
