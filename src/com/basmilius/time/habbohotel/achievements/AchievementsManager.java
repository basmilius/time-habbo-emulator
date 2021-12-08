package com.basmilius.time.habbohotel.achievements;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manager for Achievements.
 */
public class AchievementsManager extends IManager
{

	private final List<Achievement> achievements;
	private final List<Talent> talents;

	/**
	 * Constructor.
	 */
	public AchievementsManager()
	{
		this.achievements = new ArrayList<>();
		this.talents = new ArrayList<>();
	}

	/**
	 * Initializes this manager.
	 */
	@Override
	public void initialize()
	{
		this.loadAchievements();
		this.loadTalents();
	}

	/**
	 * Loads all the achievements.
	 */
	private void loadAchievements()
	{
		this.achievements.clear();

		Bootstrap.getEngine().getLogging().logNoNewLine(AchievementsManager.class, "Loading achievements .. ");

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM achievements");
			{
				final ResultSet result = statement.executeQuery();

				while (result.next())
				{
					this.achievements.add(new Achievement(result));
				}
			}

			Bootstrap.getEngine().getLogging().logOK();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			Bootstrap.getEngine().onLaunchFail();
		}
	}

	/**
	 * Loads all the talents.
	 */
	private void loadTalents()
	{
		this.talents.clear();

		Bootstrap.getEngine().getLogging().logNoNewLine(AchievementsManager.class, "Loading talents .. ");

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM achievements_talents");
			{
				final ResultSet result = statement.executeQuery();

				while (result.next())
				{
					this.talents.add(new Talent(result));
				}
			}

			Bootstrap.getEngine().getLogging().logOK();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			Bootstrap.getEngine().onLaunchFail();
		}
	}

	/**
	 * Gets all the Achievements.
	 *
	 * @return List
	 */
	public List<Achievement> getAchievements()
	{
		return this.achievements;
	}

	/**
	 * Gets an Achievement.
	 *
	 * @param id Achievement ID.
	 * @return Achievement
	 */
	public Achievement getAchievement(final int id)
	{
		for (final Achievement achievement : this.achievements)
			if (achievement.getId() == id)
				return achievement;

		return null;
	}

	/**
	 * Gets an Achievement.
	 *
	 * @param name Achievement Name.
	 * @return Achievement
	 */
	public Achievement getAchievement(final String name)
	{
		for (final Achievement achievement : this.achievements)
			if (achievement.getBadge(0).startsWith(name))
				return achievement;

		return null;
	}

	/**
	 * Gets all the Talents for a Talent Track type.
	 *
	 * @param type Type of the TalentTrack.
	 * @return List
	 */
	public List<Talent> getTalents(final String type)
	{
		return this.talents.stream().filter(talent -> talent.getType().equals(type)).collect(Collectors.toList());
	}

	/**
	 * Disposes this manager.
	 */
	@Override
	public void dispose()
	{
		this.achievements.clear();
		this.talents.clear();
	}

}
