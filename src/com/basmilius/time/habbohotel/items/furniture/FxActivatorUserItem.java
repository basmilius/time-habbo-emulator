package com.basmilius.time.habbohotel.items.furniture;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IPixelException;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.habbohotel.rooms.pathfinding.Rotation;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class FxActivatorUserItem extends UserItem
{

	private boolean isActivating = false;

	public FxActivatorUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public FxActivatorUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public void onWiredCollision(final RoomUnit unit)
	{

	}

	@Override
	public void onWiredInteractsWithItem(final WiredUserItem wiredItem)
	{

	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{
		if (!this.getBaseItem().getIsWalkable())
		{
			final int effectId = (unit.getGender().equalsIgnoreCase("m") ? this.getBaseItem().getEffectMale() : this.getBaseItem().getEffectFemale());

			unit.setRotation(Rotation.calculate(unit.getNode(), this.getNode()));
			unit.updateStatus();

			if (unit.getNodeInFront().equals(this.getNode()))
			{
				(new Thread(() -> {
					try
					{
						this.isActivating = true;
						this.updateStateInRoom();

						Thread.sleep(210);

						unit.applyEffect(effectId);
						this.delete();
					}
					catch (InterruptedException e)
					{
						Bootstrap.getEngine().getLogging().handleException(e);
					}
				})).start();
			}
			else
			{
				try
				{
					unit.walkTo(this.getNodeInFront(), 100, new RoomUnit.WalkToCallback()
					{
						@Override
						public boolean onStep(final RoomUnit unit, final Node node)
						{
							return true;
						}

						@Override
						public void onWalkToSuccess(final RoomUnit unit)
						{
							FxActivatorUserItem.this.onUnitInteractsWithItem(unit, param);
						}

						@Override
						public void onWalkToFailed(final RoomUnit unit)
						{

						}
					});
				}
				catch (IPixelException e)
				{
					Bootstrap.getEngine().getLogging().handleException(e);
				}
			}
		}
	}

	@Override
	public boolean onUnitSaysSomething(final RoomUnit unit, final String text, final ChatBubble chatBubble, final boolean isShouted)
	{
		return false;
	}

	@Override
	public void onUnitWalksOnItem(final RoomUnit unit)
	{
		(new Thread(() -> {
			try
			{
				this.isActivating = true;
				this.updateStateInRoom();

				Thread.sleep(210);

				if (unit.getGender().equalsIgnoreCase("m"))
				{
					unit.applyEffect(this.getBaseItem().getEffectMale());
				}
				else
				{
					unit.applyEffect(this.getBaseItem().getEffectFemale());
				}

				Thread.sleep(210);

				this.isActivating = false;
				this.updateStateInRoom();
			}
			catch (InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		})).start();
	}

	@Override
	public void onUnitWalksOffItem(final RoomUnit unit)
	{

	}

	@Override
	public void serializeData(final ServerMessage response, final boolean isInventory, final boolean isDataOnly)
	{
		if (!isDataOnly)
			response.appendInt(1);

		response.appendInt(0);
		response.appendString(this.isActivating ? "1" : "0");
	}

}
