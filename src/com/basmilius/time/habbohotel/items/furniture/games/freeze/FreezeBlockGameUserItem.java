package com.basmilius.time.habbohotel.items.furniture.games.freeze;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.furniture.games.GameUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.games.freeze.FreezeFurnitureData;
import com.basmilius.time.habbohotel.rooms.games.freeze.FreezeGame;
import com.basmilius.time.habbohotel.rooms.games.freeze.FreezePowerUp;
import com.basmilius.time.habbohotel.rooms.games.freeze.FreezeUser;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class FreezeBlockGameUserItem extends GameUserItem
{

	public FreezeBlockGameUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public FreezeBlockGameUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	public final void reset()
	{
		this.setExtraData(FreezeFurnitureData.Block.None, false);
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

		if (unit.getUnitType() != RoomUnitType.USER)
			return;

		final FreezeGame freeze = this.getGame(FreezeGame.class);
		if (freeze == null)
			return;
		final FreezeUser player = freeze.getFreezePlayerForRoomUser((RoomUser) unit);
		
		if (player == null)
			return;

		if (this.getExtraData().equalsIgnoreCase(FreezeFurnitureData.Block.PowerUpAdditionalDistance))
		{
			player.giveAdditionalDistance();
			this.setExtraData(FreezeFurnitureData.Block.PowerUpAdditionalDistancePicked);
		}
		else if (this.getExtraData().equalsIgnoreCase(FreezeFurnitureData.Block.PowerUpAdditionalSnowball))
		{
			player.giveAdditionalSnowball();
			this.setExtraData(FreezeFurnitureData.Block.PowerUpAdditionalSnowballPicked);
		}
		else if (this.getExtraData().equalsIgnoreCase(FreezeFurnitureData.Block.PowerUpLife))
		{
			player.giveAdditionalLive();
			this.setExtraData(FreezeFurnitureData.Block.PowerUpLifePicked);
		}
		else if (this.getExtraData().equalsIgnoreCase(FreezeFurnitureData.Block.PowerUpShield))
		{
			player.shield();
			this.setExtraData(FreezeFurnitureData.Block.PowerUpShieldPicked);
		}
		else if (this.getExtraData().equalsIgnoreCase(FreezeFurnitureData.Block.PowerUpNextThrowBomb))
		{
			player.getPowerUps().add(FreezePowerUp.Bomb);
			this.setExtraData(FreezeFurnitureData.Block.PowerUpNextThrowBombPicked);
		}
		else if (this.getExtraData().equalsIgnoreCase(FreezeFurnitureData.Block.PowerUpNextThrowDiagonal))
		{
			player.getPowerUps().add(FreezePowerUp.Diagonal);
			this.setExtraData(FreezeFurnitureData.Block.PowerUpNextThrowDiagonalPicked);
		}
		this.updateStateInRoom();
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
		response.appendString(this.getExtraData());
	}

}