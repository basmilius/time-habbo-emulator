package com.basmilius.time.habbohotel.items.furniture.games.freeze;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.furniture.games.GameUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.games.freeze.FreezeGame;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class FreezeTimerGameUserItem extends GameUserItem
{

	private int seconds;

	public FreezeTimerGameUserItem(final ResultSet result) throws SQLException
	{
		super(result);
		this.seconds = 0;
	}

	public FreezeTimerGameUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
		this.seconds = 0;
	}

	public final int getSeconds()
	{
		return this.seconds;
	}

	public final void setTime(final int seconds)
	{
		this.seconds = seconds;
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

		final FreezeGame game = this.getGame(FreezeGame.class);

		if (game == null)
			return;

		if (param == 1)
		{
			if (game.isStarted())
			{
				game.stop();
			}
			else
			{
				game.start();
			}
		}
		else if (param == 2 && !game.isStarted())
		{
			game.updateTimerSeconds(this.seconds);
		}
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
		response.appendString(Integer.toString(this.seconds));
	}

}
