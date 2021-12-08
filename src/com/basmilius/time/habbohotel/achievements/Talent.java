package com.basmilius.time.habbohotel.achievements;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A group of Achievements for an Talent Level.
 */
public class Talent
{

	private final String type;
	private final int level;
	private final List<Integer> achievements;
	private final List<Integer> levels;
	private final List<Integer> rewardItems;
	private final List<String> rewardPerks;

	/**
	 * Constructor.
	 *
	 * @param result The result set from the Database.
	 * @throws SQLException
	 */
	public Talent(final ResultSet result) throws SQLException
	{
		this.type = result.getString("type");
		this.level = result.getInt("level");
		this.achievements = new ArrayList<>();
		this.levels = new ArrayList<>();
		this.rewardItems = new ArrayList<>();
		this.rewardPerks = new ArrayList<>();

		if (!result.getString("achievement_ids").isEmpty())
		{
			for (String achievementId : result.getString("achievement_ids").split(","))
			{
				this.achievements.add(Integer.parseInt(achievementId));
			}
		}

		if (!result.getString("achievement_levels").isEmpty())
		{
			for (String achievementLevel : result.getString("achievement_levels").split(","))
			{
				this.levels.add(Integer.parseInt(achievementLevel));
			}
		}

		if (!result.getString("reward_furni").isEmpty())
		{
			for (String itemId : result.getString("reward_furni").split(","))
			{
				this.rewardItems.add(Integer.parseInt(itemId));
			}
		}

		if (!result.getString("reward_perks").isEmpty())
		{
			Collections.addAll(this.rewardPerks, result.getString("reward_perks").split(","));
		}
	}

	/**
	 * Gets the type of this Talent.
	 *
	 * @return String
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Gets the level ID for this Talent.
	 *
	 * @return int
	 */
	public int getLevel()
	{
		return this.level;
	}

	/**
	 * Gets the Achievements in this Talent.
	 *
	 * @return List
	 */
	public List<Achievement> getAchievements()
	{
		return this.achievements.stream().map(achievementId -> Bootstrap.getEngine().getGame().getAchievementsManager().getAchievement(achievementId)).collect(Collectors.toList());
	}

	/**
	 * Gets the levels for the Achievements.
	 *
	 * @return List
	 */
	public List<Integer> getAchievementLevels()
	{
		return this.levels;
	}

	/**
	 * Gets the Items the user gets when the user completes this Talent.
	 *
	 * @return List
	 */
	public List<Item> getRewardItems()
	{
		return this.rewardItems.stream().map(item -> Bootstrap.getEngine().getGame().getItemsManager().getItem(item)).collect(Collectors.toList());
	}

	/**
	 * Gets the Perks the user gets when the user completes this Talent.
	 *
	 * @return List
	 */
	public List<String> getRewardPerks()
	{
		return this.rewardPerks;
	}

	/**
	 * Gets if the user has completed this Talent.
	 *
	 * @param habbo The User.
	 * @return boolean
	 */
	public boolean getUserCompleted(final Habbo habbo)
	{
		boolean completed = true;

		if (this.getType().equals("citizenship"))
		{
			completed = habbo.getSettings().getCitizenLevel() >= this.level;
		}

		for (final Achievement achievement : this.getAchievements())
		{
			if (!completed)
				break;

			completed = this.getUserCompletedAchievement(achievement, habbo);
		}

		return completed;
	}

	/**
	 * Gets if the user has completed an Achievement.
	 *
	 * @param achievement The Achievement.
	 * @param habbo       The User.
	 * @return boolean
	 */
	public boolean getUserCompletedAchievement(final Achievement achievement, final Habbo habbo)
	{
		return achievement.getUserCompleted(habbo, this.levels.get(this.achievements.indexOf(achievement.getId())));
	}

}
