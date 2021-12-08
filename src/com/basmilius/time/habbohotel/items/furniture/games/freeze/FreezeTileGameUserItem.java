package com.basmilius.time.habbohotel.items.furniture.games.freeze;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.InteractionType;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.games.GameUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.games.freeze.FreezeFurnitureData;
import com.basmilius.time.habbohotel.rooms.games.freeze.FreezeGame;
import com.basmilius.time.habbohotel.rooms.games.freeze.FreezePowerUp;
import com.basmilius.time.habbohotel.rooms.games.freeze.FreezeUser;
import com.basmilius.time.communication.messages.ServerMessage;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class FreezeTileGameUserItem extends GameUserItem
{

	private String tileData;

	public FreezeTileGameUserItem(final ResultSet result) throws SQLException
	{
		super(result);
		this.tileData = "0";
	}

	public FreezeTileGameUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
		this.tileData = "0";
	}

	public final void reset()
	{
		this.tileData = FreezeFurnitureData.Tile.None;
	}

	public final String getTileDataByDistance(final int distance, final FreezePowerUp powerUp)
	{
		if (powerUp == FreezePowerUp.Bomb)
			return FreezeFurnitureData.Tile.ThrowDistanceBomb;
		else if (distance <= 2)
			return FreezeFurnitureData.Tile.ThrowDistanceSmall;
		else if (distance <= 4)
			return FreezeFurnitureData.Tile.ThrowDistanceMedium;
		else if (distance <= 6)
			return FreezeFurnitureData.Tile.ThrowDistanceLarge;
		else if (distance <= 8)
			return FreezeFurnitureData.Tile.ThrowDistanceFar;
		else if (distance <= 10)
			return FreezeFurnitureData.Tile.ThrowDistanceBomb;
		else
			return FreezeFurnitureData.Tile.None;
	}

	public final String getRandomBlockState()
	{
		SecureRandom rnd = new SecureRandom();

		switch (rnd.nextInt(100) % 10)
		{
			case 0:
				return FreezeFurnitureData.Block.None;
			case 1:
				return FreezeFurnitureData.Block.PowerUpAdditionalDistance;
			case 2:
				return FreezeFurnitureData.Block.PowerUpNextThrowDiagonal;
			case 3:
				return FreezeFurnitureData.Block.PowerUpNextThrowBomb;
			case 4:
				return FreezeFurnitureData.Block.PowerUpAdditionalSnowball;
			case 5:
				return FreezeFurnitureData.Block.PowerUpLife;
			case 6:
				return FreezeFurnitureData.Block.PowerUpShield;
			case 7:
				return FreezeFurnitureData.Block.PowerUpAdditionalDistance;
			case 8:
				return FreezeFurnitureData.Block.PowerUpNextThrowDiagonal;
			case 9:
				return FreezeFurnitureData.Block.PowerUpNextThrowBomb;
		}

		return FreezeFurnitureData.Block.None;
	}

	public final void explode(final FreezeUser player)
	{
		final int distance;

		final FreezePowerUp powerUp;
		if (player.getPowerUps().peek() == null)
			powerUp = FreezePowerUp.None;
		else
			powerUp = player.getPowerUps().poll();

		if (powerUp == FreezePowerUp.Bomb)
			distance = 10;
		else
			distance = player.getDistance();

		final FreezeGame game = this.getGame(FreezeGame.class);
		
		if (game == null)
			return;
		
		final String[] allowedTiles = new String[]{"", FreezeFurnitureData.Tile.None, FreezeFurnitureData.Tile.Explode};
		final boolean horizontal = (powerUp == FreezePowerUp.Bomb || powerUp != FreezePowerUp.Diagonal);
		final boolean vertical = (powerUp == FreezePowerUp.Bomb || powerUp == FreezePowerUp.Diagonal);
		final Map<Integer, List<UserItem>> tiles = this.getNearItemsWithDistanceAndSameType(distance, allowedTiles, horizontal, vertical);

		final Thread t = new Thread(() -> {
			try
			{
				if (!game.isStarted() || !player.canThrowSnowball())
					return;

				player.throwSnowball();
				this.tileData = this.getTileDataByDistance(distance, powerUp);
				this.updateStateInRoom();

				Thread.sleep(440 * 5);

				for (int i = 0; i < tiles.size(); i++)
				{
					if (!game.isStarted())
						return;

					for (final UserItem tileUserItem : tiles.get(i))
					{
						if (!FreezeTileGameUserItem.class.isInstance(tileUserItem))
							continue;

						FreezeTileGameUserItem tile = (FreezeTileGameUserItem) tileUserItem;
						if ((tile.tileData.equalsIgnoreCase(FreezeFurnitureData.Tile.ThrowDistanceSmall) || tile.tileData.equalsIgnoreCase(FreezeFurnitureData.Tile.ThrowDistanceMedium) || tile.tileData.equalsIgnoreCase(FreezeFurnitureData.Tile.ThrowDistanceLarge) || tile.tileData.equalsIgnoreCase(FreezeFurnitureData.Tile.ThrowDistanceFar) || tile.tileData.equalsIgnoreCase(FreezeFurnitureData.Tile.ThrowDistanceBomb)) && tile.getId() != this.getId())
							continue;

						tile.tileData = FreezeFurnitureData.Tile.None;
						tile.updateStateInRoom();
						this.handleExplosion(tile, player);
						tile.tileData = FreezeFurnitureData.Tile.Explode;
						tile.updateStateInRoom();
					}

					if (i == 0)
					{
						player.throwSnowballDone();
					}

					Thread.sleep(100);
				}

				if (game.getPlayers().size() == 0)
				{
					game.stop();
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		});
		t.start();
	}

	private void handleExplosion(final FreezeTileGameUserItem tile, final FreezeUser player)
	{
		final FreezeGame game = this.getGame(FreezeGame.class);
		
		if (game == null)
			return;
		
		final List<UserItem> itemsOnTile = this.getRoom().getRoomObjectsHandler().getFloorItems().stream().filter(block -> block.getNode().equals(tile.getNode()) && block.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.Freeze_Block) && (block.getExtraData().isEmpty() || block.getExtraData().equalsIgnoreCase(FreezeFurnitureData.Block.None))).collect(Collectors.toList());
		final RoomUnit unitOnTile = this.getRoom().getRoomUnitsHandler().getUnitAt(tile.getNode());

		int points = 0;

		for (final UserItem itemOnTile : itemsOnTile)
		{
			itemOnTile.setStackHeight(0.00);
			itemOnTile.setIsWalkable(true);
			itemOnTile.setExtraData(this.getRandomBlockState(), false);
			itemOnTile.updateStateInRoom();
			itemOnTile.getRoom().getRoomObjectsHandler().updateGameMap();

			points += 5;
		}

		if (RoomUser.class.isInstance(unitOnTile))
		{
			final RoomUser userOnTile = (RoomUser) unitOnTile;

			if (game.hasPlayer(userOnTile))
			{
				final FreezeUser playerOnTile = game.getFreezePlayerForRoomUser(userOnTile);
				if (playerOnTile != null && !playerOnTile.isFrozen())
				{
					if (playerOnTile.getTeam() == player.getTeam())
					{
						points -= 10;
					}
					else
					{
						points += 10;
					}
					playerOnTile.freeze();
				}
			}
		}

		player.givePoints(points);
	}

	@Override
	public final void onWiredCollision(final RoomUnit unit)
	{
	}

	@Override
	public final void onWiredInteractsWithItem(final WiredUserItem wiredItem)
	{
	}

	@Override
	public final void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{
		super.onUnitInteractsWithItem(unit, param);
	}

	@Override
	public final boolean onUnitSaysSomething(final RoomUnit unit, final String text, final ChatBubble chatBubble, final boolean isShouted)
	{
		return false;
	}

	@Override
	public final void onUnitWalksOnItem(final RoomUnit unit)
	{
		super.onUnitWalksOnItem(unit);
	}

	@Override
	public final void onUnitWalksOffItem(final RoomUnit unit)
	{
		super.onUnitWalksOffItem(unit);
	}

	@Override
	public final void serializeData(final ServerMessage response, final boolean isInventory, final boolean isDataOnly)
	{
		if (!isDataOnly)
			response.appendInt(1);

		response.appendInt(0);
		response.appendString(this.tileData);
	}

}