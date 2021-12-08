package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ISave;
import com.basmilius.time.habbohotel.achievements.Achievement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HabboAchievement extends ISave
{

	private int _id;
	private Achievement _achievement;
	private Habbo _habbo;
	private int _userProgress;
	private int _userLevel;

	{
		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.achievements", 30000));
	}

	public HabboAchievement(ResultSet result) throws SQLException
	{
		this._id = result.getInt("id");
		this._achievement = Bootstrap.getEngine().getGame().getAchievementsManager().getAchievement(result.getInt("achievement_id"));
		this._habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this._userProgress = result.getInt("user_progress");
		this._userLevel = result.getInt("user_level");

		this.checkBadge();
	}

	public HabboAchievement(Achievement _achievement, Habbo _habbo)
	{
		this._achievement = _achievement;
		this._habbo = _habbo;
		this._userProgress = 0;
		this._userLevel = 0;
		this.setNeedsUpdate(true);
		this.setNeedsInsert(true);

		this.checkBadge();
	}

	private void checkBadge()
	{
		if (this.getUserLevel() > 0 && !Bootstrap.getEngine().getGame().getBadgeManager().hasBadge(this._habbo, "ACH_" + this._achievement.getAchievementName()))
		{
			Bootstrap.getEngine().getGame().getBadgeManager().addBadge(this._habbo, "ACH_" + this._achievement.getAchievementName(), 0, true);
		}
	}

	public int getId()
	{
		return this._id;
	}

	public Achievement getAchievement()
	{
		return this._achievement;
	}

	public Habbo getHabbo()
	{
		return this._habbo;
	}

	public int getUserProgress()
	{
		return this._userProgress;
	}

	public int getUserLevel()
	{
		return this._userLevel;
	}

	public void setUserProgress(int newProgress, boolean updateNoAdd)
	{
		if (updateNoAdd)
		{
			this._userProgress = newProgress;
		}
		else
		{
			this._userProgress += newProgress;
		}

		if (this.getAchievement().getCalculatedProgress(this._userLevel + 1) <= this._userProgress)
		{
			this._userLevel++;
		}

		this.setNeedsUpdate(true);
	}

	@Override
	public void save() throws SQLException
	{
		if (this.getNeedsInsert())
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO users_achievements (user_id, achievement_id, user_level, user_progress) VALUES (?, ?, ?, ?)");
			{
				if (statement != null)
				{
					statement.setInt(1, this._habbo.getId());
					statement.setInt(2, this._achievement.getId());
					statement.setInt(3, this._userLevel);
					statement.setInt(4, this._userProgress);
					statement.execute();

					ResultSet keys = statement.getGeneratedKeys();
					keys.next();
					this._id = keys.getInt(1);
				}
			}
		}
		else if (this.getNeedsUpdate())
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE users_achievements SET user_level = ?, user_progress = ? WHERE user_id = ? AND achievement_id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, this._userLevel);
					statement.setInt(2, this._userProgress);
					statement.setInt(3, this._habbo.getId());
					statement.setInt(4, this._achievement.getId());
					statement.execute();
				}
			}
		}

		this.setNeedsUpdate(false);
		this.setNeedsInsert(false);
	}

}
