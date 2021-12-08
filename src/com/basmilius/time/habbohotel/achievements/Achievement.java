package com.basmilius.time.habbohotel.achievements;

import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.HabboAchievement;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An in-game Achievement.
 */
public class Achievement
{

	private final int id;
	private final int totalLevels;
	private final int ducketsReward;
	private final int scoreReward;
	private final double scoreMultiplier;
	private final int progress;
	private final double progressMultiplier;
	private final boolean progressEnabled;
	private final String type;
	private final String badgeBase;

	/**
	 * Constructor.
	 *
	 * @param result ResultSet from the Database.
	 * @throws SQLException
	 */
	public Achievement(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.totalLevels = result.getInt("levels");
		this.ducketsReward = result.getInt("duckets");
		this.scoreReward = result.getInt("score");
		this.scoreMultiplier = result.getDouble("score_multiplier");
		this.progress = result.getInt("progress");
		this.progressMultiplier = result.getDouble("progress_multiplier");
		this.progressEnabled = (result.getString("progress_enabled").equals("1"));
		this.type = result.getString("type");
		this.badgeBase = result.getString("badge_base");
	}

	/**
	 * Gets the ID for this Achievement.
	 *
	 * @return int
	 */
	public int getId()
	{
		return this.id;
	}

	/**
	 * Gets the count of levels for this Achievement.
	 *
	 * @return int
	 */
	public int getTotalLevels()
	{
		return this.totalLevels;
	}

	/**
	 * Gets the duckets you get when you achieve this Achievement.
	 *
	 * @return int
	 */
	public int getRewardDuckets()
	{
		return this.ducketsReward;
	}

	/**
	 * Gets the base for score you get when you achieve this Achievement.
	 *
	 * @return int
	 */
	public int getRewardScore()
	{
		return this.scoreReward;
	}

	/**
	 * Gets the multiplier for score.
	 *
	 * @return double
	 */
	public double getScoreMultiplier()
	{
		return this.scoreMultiplier;
	}

	/**
	 * Gets the multiplier for progress.
	 *
	 * @return double
	 */
	public double getProgressMultiplier()
	{
		return this.progressMultiplier;
	}

	/**
	 * Gets the type of this Achievement.
	 *
	 * @return String
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Gets the name of this Achievement.
	 *
	 * @return String
	 */
	public String getAchievementName()
	{
		return this.badgeBase.replace("ACH_", "");
	}

	/**
	 * Gets the badge for this Achievement and the user's level.
	 *
	 * @param userLevel Level of the user.
	 * @return String
	 */
	public String getBadge(final int userLevel)
	{
		if (this.totalLevels == 1)
			return this.badgeBase + "1";

		return (this.badgeBase + userLevel);
	}

	/**
	 * Gets the calculated score the user gets for the new level.
	 *
	 * @param level User's level.
	 * @return int
	 */
	public int getCalculatedScore(final int level)
	{
		int score = this.scoreReward;
		int i = 0;
		while (i < level)
		{
			i++;
			score = (int) (score * this.scoreMultiplier);
		}
		return score;
	}

	/**
	 * Gets the calculated duckets the user gets for the new level.
	 *
	 * @param level User's level.
	 * @return int
	 */
	public int getCalculatedDuckets(final int level)
	{
		int duckets = this.ducketsReward;
		int i = 0;
		while (i < level)
		{
			i++;
			duckets = (int) Math.floor(duckets * this.scoreMultiplier);
		}
		return duckets;
	}

	/**
	 * Gets the calculated progress for the new level.
	 *
	 * @param level new level.
	 * @return int
	 */
	public int getCalculatedProgress(final int level)
	{
		int progress = this.progress;
		int i = 1;
		while (i < level)
		{
			i++;
			progress = (int) Math.floor(progress * this.progressMultiplier);
		}

		return progress;
	}

	/**
	 * Gets if the user has achieved this Achievement.
	 *
	 * @param habbo The user.
	 * @param level The specified level to calculate.
	 * @return boolean
	 */
	public boolean getUserCompleted(final Habbo habbo, final int level)
	{
		final HabboAchievement hAchievement = habbo.getAchievements().getAchievement(this);
		return hAchievement != null && (hAchievement.getUserLevel() >= level);

	}

	/**
	 * Gets the progress for the specifier user.
	 *
	 * @param habbo The user.
	 * @return int
	 */
	public int getUserProgress(final Habbo habbo)
	{
		final HabboAchievement hAchievement = habbo.getAchievements().getAchievement(this);

		if (hAchievement == null)
			return 0;

		return hAchievement.getUserProgress();
	}

	/**
	 * Serialize this Achievement for the ServerResponse.
	 *
	 * @param response Server Response.
	 * @param habbo    User.
	 */
	public void serialize(final ServerMessage response, final Habbo habbo)
	{
		final HabboAchievement hAchievement = habbo.getAchievements().getAchievement(this);

		response.appendInt(this.id);
		response.appendInt(((hAchievement != null) ? (((hAchievement.getUserLevel() + 1) > this.totalLevels) ? this.totalLevels : (hAchievement.getUserLevel() + 1)) : 1));
		response.appendString(this.getBadge(((hAchievement != null) ? hAchievement.getUserLevel() : 0) + 1));
		response.appendInt(15);
		response.appendInt(this.getCalculatedProgress(((hAchievement != null) ? (hAchievement.getUserLevel() + 1) : 1)));
		response.appendInt(this.getCalculatedDuckets((hAchievement != null) ? hAchievement.getUserLevel() : 1));
		response.appendInt(0);
		response.appendInt((hAchievement != null) ? hAchievement.getUserProgress() : 0);
		response.appendBoolean(((hAchievement != null) ? hAchievement.getUserLevel() : 0) == this.totalLevels);
		response.appendString(this.type);
		response.appendString("");
		response.appendInt(this.totalLevels);
		response.appendInt((this.progressEnabled) ? 0 : 1);
	}

}
