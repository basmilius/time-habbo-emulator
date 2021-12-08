package com.basmilius.time.habbohotel.rooms.games.freeze;

import com.basmilius.time.habbohotel.enums.SpecialEffects;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.rooms.games.FreezeLifesComposer;

import java.util.concurrent.ConcurrentLinkedQueue;

public class FreezeUser
{

	private final RoomUser roomUser;

	private final FreezeTeam team;

	private int distance;
	private boolean hasShield;
	private boolean isFrozen;
	private int lives;
	private int snowballs;
	private int points;

	private final ConcurrentLinkedQueue<FreezePowerUp> powerUps;

	public FreezeUser(final RoomUser roomUser, final FreezeTeam team)
	{
		this.roomUser = roomUser;
		this.team = team;

		this.distance = 10;
		this.hasShield = false;
		this.isFrozen = false;
		this.lives = 3;
		this.snowballs = 1;
		this.points = 0;

		this.powerUps = new ConcurrentLinkedQueue<>();
	}

	public final boolean canThrowSnowball()
	{
		return this.snowballs > 0 && !this.isFrozen;
	}

	public final RoomUser getRoomUser()
	{
		return this.roomUser;
	}

	public final boolean hasShield()
	{
		return this.hasShield;
	}

	public final boolean isFrozen()
	{
		return this.isFrozen;
	}

	public final int getDistance()
	{
		return this.distance;
	}

	public final int getLives()
	{
		return this.lives;
	}

	public final FreezeTeam getTeam()
	{
		return this.team;
	}

	public final void giveAdditionalDistance()
	{
		if (this.distance >= 8)
			return;

		this.distance++;
	}

	public final void giveAdditionalLive()
	{
		if (this.lives >= 3)
			return;

		this.lives++;

		this.getRoomUser().getRoom().getRoomUnitsHandler().send(new FreezeLifesComposer(this.roomUser.getUnitId(), this.lives));
	}

	public final void giveAdditionalSnowball()
	{
		if (this.snowballs >= 5)
			return;

		this.snowballs++;
	}

	public final void freeze()
	{
		if (!this.isFrozen && !this.hasShield)
		{
			this.lives--;

			if (this.lives <= 0)
			{
				this.getRoomUser().getRoom().getGame(FreezeGame.class).exitPlayer(this);
				this.getRoomUser().getRoom().getGame(FreezeGame.class).removePlayer(this);
				return;
			}

			this.getRoomUser().getRoom().getRoomUnitsHandler().send(new FreezeLifesComposer(this.roomUser.getUnitId(), this.lives));

			Thread t = new Thread(() -> {
				try
				{
					this.isFrozen = true;
					this.getRoomUser().applyEffect(SpecialEffects.FROZEN);
					this.getRoomUser().setCanWalk(false);

					Thread.sleep(5000);

					this.isFrozen = false;
					this.getRoomUser().applyEffect(this.roomUser.getRoom().getGame(FreezeGame.class).getSpecialEffect(this, false));
					this.getRoomUser().setCanWalk(true);

					this.shield();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			});
			t.start();
		}
	}

	public final void shield()
	{
		if (!this.hasShield)
		{
			final Thread t = new Thread(() ->
			{
				try
				{
					this.hasShield = true;
					this.getRoomUser().applyEffect(this.roomUser.getRoom().getGame(FreezeGame.class).getSpecialEffect(this, true));

					Thread.sleep(5000);

					this.hasShield = false;
					this.getRoomUser().applyEffect(this.roomUser.getRoom().getGame(FreezeGame.class).getSpecialEffect(this, false));
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			});
			t.start();
		}
	}

	public final void givePoints(final int points)
	{
		this.points = (this.points + points);
	}

	public final ConcurrentLinkedQueue<FreezePowerUp> getPowerUps()
	{
		return this.powerUps;
	}

	public final void throwSnowball()
	{
		this.snowballs--;
	}

	public final void throwSnowballDone()
	{
		this.snowballs++;
	}

}
