package com.basmilius.time.habbohotel.rooms.games.freeze;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomFloorItemsUpdateDataComposer;
import com.basmilius.time.core.event.IEventHandler;
import com.basmilius.time.habbohotel.enums.AvatarAction;
import com.basmilius.time.habbohotel.enums.SpecialEffects;
import com.basmilius.time.habbohotel.items.event.UserItemUnitInteractsWithItemEventArgs;
import com.basmilius.time.habbohotel.items.furniture.games.GameUserItem;
import com.basmilius.time.habbohotel.items.furniture.games.freeze.*;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.games.IGame;
import com.basmilius.time.habbohotel.rooms.pathfinding.Pathfinder;
import com.basmilius.time.communication.messages.outgoing.rooms.games.FreezeLifesComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.games.FreezeStatusComposer;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FreezeGame extends IGame implements Runnable
{

	private Thread gameThread;

	private final List<GameUserItem> items;
	private final List<FreezeUser> players;

	private final FreezeGame_UnitInteractsWithItemEventHandler eventHandler_UnitInteractsWithItem;

	public FreezeGame(final Room room)
	{
		super(room);

		this.items = this.getRoom().getRoomObjectsHandler().getItems(GameUserItem.class);
		this.players = new ArrayList<>();

		this.eventHandler_UnitInteractsWithItem = new FreezeGame_UnitInteractsWithItemEventHandler(this);
	}

	public final void addPlayer(final RoomUser user, final FreezeTeam team)
	{
		if (!this.hasPlayer(user))
		{
			final FreezeUser player = new FreezeUser(user, team);
			this.players.add(player);
			player.getRoomUser().applyEffect(this.getSpecialEffect(player, false));
		}
	}

	public final boolean hasPlayer(final RoomUser user)
	{
		return this.players.stream().filter(player -> player.getRoomUser() == user).toArray().length > 0;
	}

	public final void removePlayer(final FreezeUser user)
	{
		user.getRoomUser().applyEffect(SpecialEffects.NONE);
		this.players.remove(user);
	}

	public final FreezeUser getFreezePlayerForRoomUser(final RoomUser user)
	{
		if (this.hasPlayer(user))
		{
			return (FreezeUser) this.players.stream().filter(player -> player.getRoomUser() == user).toArray()[0];
		}
		return null;
	}

	public final List<FreezeUser> getPlayers()
	{
		return this.players;
	}

	public final List<FreezeUser> getPlayers(final FreezeTeam team)
	{
		return this.players.stream().filter(player -> player.getTeam() == team).collect(Collectors.toList());
	}

	public final int getSpecialEffect(final FreezeUser player, final boolean shield)
	{
		if (player.isFrozen())
			return SpecialEffects.FROZEN;

		switch(player.getTeam())
		{
			case BLUE:
				return ((shield) ? SpecialEffects.FREEZE_TEAM_BLUE_SHIELD : SpecialEffects.FREEZE_TEAM_BLUE);
			case GREEN:
				return ((shield) ? SpecialEffects.FREEZE_TEAM_GREEN_SHIELD : SpecialEffects.FREEZE_TEAM_GREEN);
			case RED:
				return ((shield) ? SpecialEffects.FREEZE_TEAM_RED_SHIELD : SpecialEffects.FREEZE_TEAM_RED);
			case YELLOW:
				return ((shield) ? SpecialEffects.FREEZE_TEAM_YELLOW_SHIELD : SpecialEffects.FREEZE_TEAM_YELLOW);
		}

		return 0;
	}

	public final int getSeconds()
	{
		List<FreezeTimerGameUserItem> timers = this.getRoom().getRoomObjectsHandler().getItems(FreezeTimerGameUserItem.class);
		if (timers.size() > 0)
			return timers.get(0).getSeconds();
		return 0;
	}

	public final void updateExitTiles()
	{
		List<FreezeExitTileGameUserItem> exitTiles = this.getRoom().getRoomObjectsHandler().getItems(FreezeExitTileGameUserItem.class);
		exitTiles.forEach(com.basmilius.time.habbohotel.items.furniture.games.freeze.FreezeExitTileGameUserItem::updateStateInRoom);
	}

	public final void updateGates()
	{
		List<GameUserItem> gates = new ArrayList<>();
		gates.addAll(this.getRoom().getRoomObjectsHandler().getItems(FreezeGateBlueGameUserItem.class));
		gates.addAll(this.getRoom().getRoomObjectsHandler().getItems(FreezeGateGreenGameUserItem.class));
		gates.addAll(this.getRoom().getRoomObjectsHandler().getItems(FreezeGateRedGameUserItem.class));
		gates.addAll(this.getRoom().getRoomObjectsHandler().getItems(FreezeGateYellowGameUserItem.class));

		for (final GameUserItem gate : gates)
		{
			gate.setIsWalkable(!this.isStarted());
			gate.updateStateInRoom();
		}
	}

	public final void updateTimerSeconds(final int currentSeconds)
	{
		if (this.isStarted())
		{
			List<FreezeTimerGameUserItem> timers = this.getRoom().getRoomObjectsHandler().getItems(FreezeTimerGameUserItem.class);
			for (final FreezeTimerGameUserItem timer : timers)
			{
				timer.setTime(currentSeconds);
				timer.updateStateInRoom();
			}
			return;
		}

		final int seconds;
		if (currentSeconds >= 600)
			seconds = 0;
		else if (currentSeconds >= 300)
			seconds = 600;
		else if (currentSeconds >= 180)
			seconds = 300;
		else if (currentSeconds >= 60)
			seconds = 180;
		else if (currentSeconds >= 0)
			seconds = 60;
		else
			seconds = 30;

		List<FreezeTimerGameUserItem> timers = this.getRoom().getRoomObjectsHandler().getItems(FreezeTimerGameUserItem.class);
		for (final FreezeTimerGameUserItem timer : timers)
		{
			timer.setTime(seconds);
			timer.updateStateInRoom();
		}
	}

	@Override
	public final void initialize()
	{
		this.items.stream().filter(FreezeTileGameUserItem.class::isInstance).forEach(item -> item.addEventListener(UserItemUnitInteractsWithItemEventArgs.class, this.eventHandler_UnitInteractsWithItem));
	}

	@Override
	protected final void onStart()
	{
		if (this.getSeconds() == 0)
		{
			this.stop();
			return;
		}

		this.updateExitTiles();
		this.updateGates();

		this.getRoom().getRoomObjectsHandler().getItems(FreezeBlockGameUserItem.class).forEach(com.basmilius.time.habbohotel.items.furniture.games.freeze.FreezeBlockGameUserItem::reset);
		this.getRoom().getRoomObjectsHandler().getItems(FreezeTileGameUserItem.class).forEach(com.basmilius.time.habbohotel.items.furniture.games.freeze.FreezeTileGameUserItem::reset);

		this.getRoom().getRoomUnitsHandler().send(new RoomFloorItemsUpdateDataComposer(this.getRoom().getRoomObjectsHandler().getItems(FreezeBlockGameUserItem.class)));
		this.getRoom().getRoomUnitsHandler().send(new RoomFloorItemsUpdateDataComposer(this.getRoom().getRoomObjectsHandler().getItems(FreezeTileGameUserItem.class)));

		for (final FreezeUser player : this.players)
		{
			this.getRoom().getRoomUnitsHandler().send(new FreezeLifesComposer(player.getRoomUser().getUnitId(), player.getLives()));
			player.getRoomUser().getConnection().send(new FreezeStatusComposer(true));
		}

		this.gameThread = new Thread(this);
		this.gameThread.start();
	}

	@Override
	protected final void onStop()
	{
		this.updateTimerSeconds(600);

		this.gameThread.interrupt();

		final Thread t = new Thread(() -> {
			try
			{
				synchronized (this.players)
				{
					for (final FreezeUser player : this.players)
					{
						player.getRoomUser().applyAction(AvatarAction.WAVE);
					}

					Thread.sleep(440 * 4);

					for (final FreezeUser player : this.players)
					{
						this.exitPlayer(player);
						player.getRoomUser().getConnection().send(new FreezeStatusComposer(false));
					}

					this.players.clear();

					this.updateExitTiles();
					this.updateGates();
				}
			}
			catch (InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		});
		t.start();
	}

	public void exitPlayer(final FreezeUser player)
	{
		List<FreezeExitTileGameUserItem> exitTiles = this.getRoom().getRoomObjectsHandler().getItems(FreezeExitTileGameUserItem.class);
		if (exitTiles.size() > 0)
		{
			final FreezeExitTileGameUserItem exitTile = exitTiles.get((new SecureRandom()).nextInt(exitTiles.size()));
			player.getRoomUser().setRotation(exitTile.getRot());
			player.getRoomUser().setPosition(exitTile.getNode());
			player.getRoomUser().setHeight(exitTile.getTotalHeight());
			player.getRoomUser().applyEffect(SpecialEffects.NONE);
			player.getRoomUser().updateStatus();
		}
		else
		{
			player.getRoomUser().setRotation(this.getRoom().getRoomData().getRoomModel().getDoorRotation());
			player.getRoomUser().setPosition(this.getRoom().getRoomData().getRoomModel().getDoorNode());
			player.getRoomUser().setHeight(this.getRoom().getRoomData().getRoomModel().getDoorZ());
			player.getRoomUser().applyEffect(SpecialEffects.NONE);
			player.getRoomUser().updateStatus();
		}
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run()
	{
		int seconds = this.getSeconds();
		while(seconds > 0 && this.isStarted() && this.getRoom().isActive())
		{
			try
			{
				seconds--;
				this.updateTimerSeconds(seconds);

				Thread.sleep(1000);
			}
			catch (InterruptedException ignored)
			{
			}
		}
		this.stop();
	}

	private class FreezeGame_UnitInteractsWithItemEventHandler implements IEventHandler<UserItemUnitInteractsWithItemEventArgs>
	{

		private final FreezeGame game;

		public FreezeGame_UnitInteractsWithItemEventHandler(final FreezeGame game)
		{
			this.game = game;
		}

		@Override
		public void handle(final UserItemUnitInteractsWithItemEventArgs eventArgs)
		{
			if (FreezeTileGameUserItem.class.isInstance(eventArgs.getItem()))
			{
				if (eventArgs.getUnit().getUnitType() != RoomUnitType.USER || !this.game.hasPlayer((RoomUser) eventArgs.getUnit()))
					return;

				final Pathfinder pathfinder = new Pathfinder(eventArgs.getUnit().getRoom(), eventArgs.getUnit());
				if (pathfinder.DistanceBetween(eventArgs.getItem().getNode()) > 2)
					return;

				final FreezeTileGameUserItem tile = (FreezeTileGameUserItem) eventArgs.getItem();
				tile.explode(this.game.getFreezePlayerForRoomUser((RoomUser) eventArgs.getUnit()));
			}
		}

	}

}
