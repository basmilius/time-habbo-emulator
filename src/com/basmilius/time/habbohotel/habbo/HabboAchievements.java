package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.achievements.Achievement;
import com.basmilius.time.habbohotel.achievements.Talent;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.Currencies;
import com.basmilius.time.habbohotel.items.Item;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.outgoing.achievements.AchievementUnlockedComposer;
import com.basmilius.time.communication.messages.outgoing.achievements.CitizenshipComposer;
import com.basmilius.time.communication.messages.outgoing.achievements.TalentTrackLeveledComposer;
import com.basmilius.time.communication.messages.outgoing.achievements.UpdateAchievementStatusComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.AddItemComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.FurniListAddOrUpdateComposer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabboAchievements
{

	private final Habbo habbo;
	private final List<HabboAchievement> achievements;

	public HabboAchievements(Habbo habbo)
	{
		this.habbo = habbo;
		this.achievements = new ArrayList<>();
	}

	public void loadAchievements()
	{
		this.achievements.clear();

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users_achievements WHERE user_id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, this.habbo.getId());
					final ResultSet result = statement.executeQuery();

					while (result.next())
					{
						this.achievements.add(new HabboAchievement(result));
					}
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			e.printStackTrace();
		}
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public List<HabboAchievement> getAchievements()
	{
		return this.achievements;
	}

	public HabboAchievement getAchievement(Achievement achievement)
	{
		for (HabboAchievement hAchievement : this.achievements)
			if (hAchievement.getAchievement().getId() == achievement.getId())
				return hAchievement;

		return null;
	}

	public void unlockAchievement(String achievement, int userProgress)
	{
		this.unlockAchievement(Bootstrap.getEngine().getGame().getAchievementsManager().getAchievement(achievement), userProgress, false);
	}

	public void unlockAchievement(String achievement, int userProgress, boolean updateNoAdd)
	{
		this.unlockAchievement(Bootstrap.getEngine().getGame().getAchievementsManager().getAchievement(achievement), userProgress, updateNoAdd);
	}

	void unlockAchievement(Achievement achievement, int userProgress, boolean updateNoAdd)
	{
		if (achievement == null)
			return;

		HabboAchievement hAchievement = this.getAchievement(achievement);

		if (hAchievement == null)
		{
			hAchievement = new HabboAchievement(achievement, this.habbo);

			this.achievements.add(hAchievement);
		}

		int userLevel = hAchievement.getUserLevel();

		if (userLevel >= achievement.getTotalLevels())
			return;

		hAchievement.setUserProgress(userProgress, updateNoAdd);

		if (this.habbo.isOnline())
		{
			this.habbo.getConnection().send(new UpdateAchievementStatusComposer(achievement, this.habbo));

			if (hAchievement.getUserProgress() >= achievement.getCalculatedProgress(userLevel + 1))
			{
				this.habbo.getSettings().getCurrencies().updateBalance(Currencies.DUCKETS, achievement.getCalculatedDuckets(userLevel + 1));
				this.habbo.getConnection().send(new AchievementUnlockedComposer(achievement, hAchievement));
			}

			Talent talent = Bootstrap.getEngine().getGame().getAchievementsManager().getTalents("citizenship").get(this.habbo.getSettings().getCitizenLevel());

			if (talent != null)
			{
				if (talent.getUserCompleted(this.habbo))
				{
					this.habbo.getSettings().setCitizenLevel((talent.getLevel() + 1 < 5) ? talent.getLevel() + 1 : 4);

					if (talent.getLevel() < 4)
					{
						talent = Bootstrap.getEngine().getGame().getAchievementsManager().getTalents("citizenship").get(this.habbo.getSettings().getCitizenLevel());
					}

					this.habbo.getConnection().send(new TalentTrackLeveledComposer(this.habbo, talent));
					this.habbo.getConnection().send(new CitizenshipComposer("citizenship", this.habbo));

					for (final Item item : talent.getRewardItems())
					{
						final UserItem userItem = Bootstrap.getEngine().getGame().getItemsManager().createItem(this.habbo, null, item, Bootstrap.getEngine().getGame().getCatalogueManager().getItemByBase(item), this.habbo, BoughtType.NORMAL, 0);
						this.habbo.getConnection().send(new AddItemComposer(userItem));
						this.habbo.getConnection().send(new FurniListAddOrUpdateComposer(userItem));
					}

					for (final String perk : talent.getRewardPerks())
					{
						if (perk.equalsIgnoreCase("trade"))
						{
							this.habbo.getSettings().setCanTrade(true);
						}

						if (perk.equalsIgnoreCase("citizen"))
						{
							this.habbo.getSettings().setIsCitizen(true);
						}
					}
				}
			}
		}
	}

	public void dispose()
	{
		this.achievements.clear();
	}

}
