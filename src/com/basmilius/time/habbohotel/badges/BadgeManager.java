package com.basmilius.time.habbohotel.badges;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.outgoing.inventory.BadgeReceivedComposer;
import com.basmilius.time.communication.messages.outgoing.wired.WiredRewardComposer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager for Badges.
 */
public class BadgeManager extends IManager
{

	private final Map<Habbo, List<Badge>> habboBadges;
	private int latestId = 0;

	/**
	 * Constructor.
	 */
	public BadgeManager()
	{
		this.habboBadges = new HashMap<>();
	}

	/**
	 * Adds a Badge.
	 *
	 * @param habbo     The Habbo who gets the Badge.
	 * @param badgeCode Badge code.
	 * @param slotId    Slot ID.
	 */
	public void addBadge(final Habbo habbo, final String badgeCode, final int slotId, final boolean system)
	{
		if (!this.habboBadges.containsKey(habbo))
		{
			this.getBadgesForHabbo(habbo);
		}

		if (this.hasBadge(habbo, badgeCode))
			return;

		final Badge badge = new Badge(slotId, habbo, badgeCode);
		this.habboBadges.get(habbo).add(badge);

		if (habbo.isOnline())
		{
			habbo.getConnection().send(new BadgeReceivedComposer(badge));

			if (!system)
			{
				habbo.getConnection().send(new WiredRewardComposer(WiredRewardComposer.SUCCESS_BADGE_RECEIVED));
			}
		}
	}

	public Badge getBadge(final Habbo habbo, final int slotId)
	{
		for (final Badge badge : this.getBadgesForHabbo(habbo))
			if (badge.getSlotId() == slotId)
				return badge;

		return null;
	}

	public Badge getBadge(final Habbo habbo, final String badgeCode)
	{
		for (final Badge badge : this.getBadgesForHabbo(habbo))
			if (badge.getBadgeCode().equalsIgnoreCase(badgeCode))
				return badge;

		return null;
	}

	public void setBadge(final Habbo habbo, final String badgeCode, final int slotId)
	{
		if (!this.hasBadge(habbo, badgeCode))
			return;

		Badge badge = this.getBadge(habbo, badgeCode);

		badge.setSlotId(slotId);
	}

	/**
	 * Gets all the Badges for a Habbo.
	 *
	 * @param habbo Habbo.
	 * @return List
	 */
	public List<Badge> getBadgesForHabbo(final Habbo habbo)
	{
		if (this.habboBadges.containsKey(habbo))
			return this.habboBadges.get(habbo);

		final List<Badge> badges = new ArrayList<>();

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users_badges WHERE user_id = ?");
			{
				statement.setInt(1, habbo.getId());
				final ResultSet result = statement.executeQuery();

				while (result.next())
				{
					badges.add(new Badge(result));
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}

		this.updateLatestId();

		this.habboBadges.put(habbo, badges);
		return badges;
	}

	/**
	 * Gets the latest ID.
	 *
	 * @return int
	 */
	public int getLatestId()
	{
		return this.latestId;
	}

	/**
	 * Gets if the Habbo has a Badge.
	 *
	 * @param habbo     The Habbo.
	 * @param badgeCode Badge code.
	 * @return boolean
	 */
	public boolean hasBadge(Habbo habbo, String badgeCode)
	{
		for (final Badge badge : this.getBadgesForHabbo(habbo))
			if (badge.getBadgeCode().equalsIgnoreCase(badgeCode))
				return true;

		return false;
	}

	/**
	 * Updates the latest ID from the Database.
	 */
	public void updateLatestId()
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT id FROM users_badges ORDER BY id DESC LIMIT 1");
			{
				final ResultSet result = statement.executeQuery();

				while (result.next())
				{
					this.latestId = result.getInt("id");
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	/**
	 * Initializes this Manager.
	 */
	@Override
	public void initialize()
	{
		this.updateLatestId();
	}

	/**
	 * Disposes this Manager.
	 */
	@Override
	public void dispose()
	{
		this.habboBadges.clear();
	}
}
