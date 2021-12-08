package com.basmilius.time.habbohotel.achievements;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.Item;
import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;

import java.util.List;

/**
 * Gets the TalentTrack for being a Citizen.
 */
public class TalentTrackCitizenship implements ISerialize
{

	private final Habbo habbo;

	/**
	 * Constructor.
	 *
	 * @param habbo The user.
	 */
	public TalentTrackCitizenship(final Habbo habbo)
	{
		this.habbo = habbo;
	}

	/**
	 * Serializes this TalentTrack for a Server Response.
	 *
	 * @param response Server Response.
	 */
	@Override
	public void serialize(final ServerMessage response)
	{
		final List<Talent> talents = Bootstrap.getEngine().getGame().getAchievementsManager().getTalents("citizenship");

		boolean previousCompleted = true;
		int statusValue;

		response.appendString("citizenship");
		response.appendInt(talents.size());

		for (final Talent talent : talents)
		{
			response.appendInt(talent.getLevel());
			response.appendInt((previousCompleted) ? 1 : 0);
			response.appendInt(talent.getAchievements().size());

			int i;
			for (final Achievement _achievement : talent.getAchievements())
			{
				i = talent.getAchievementLevels().get(talent.getAchievements().indexOf(_achievement));

				statusValue = (!previousCompleted) ? 0 : (talent.getUserCompletedAchievement(_achievement, this.habbo)) ? 2 : 1;

				response.appendInt(_achievement.getId());
				response.appendInt(i);
				response.appendString(_achievement.getBadge(i));
				response.appendInt(statusValue);
				response.appendInt(_achievement.getUserProgress(this.habbo));
				response.appendInt(_achievement.getCalculatedProgress(i));
			}

			previousCompleted = talent.getUserCompleted(this.habbo);

			response.appendInt(talent.getRewardPerks().size());
			talent.getRewardPerks().forEach(response::appendString);

			response.appendInt(talent.getRewardItems().size());
			for (final Item item : talent.getRewardItems())
			{
				response.appendString((item.getPublicName().isEmpty()) ? "" : item.getPublicName());
				response.appendInt(0); // days HC
			}
		}
	}

}
