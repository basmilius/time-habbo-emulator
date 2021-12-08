package com.basmilius.time.habbohotel.items.furniture;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IPixelException;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.habbohotel.rooms.pathfinding.Rotation;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.outgoing.handshake.GenericErrorMessageComposer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

@SuppressWarnings("unused")
public class VendingMachineUserItem extends UserItem implements RoomUnit.WalkToCallback
{

	public VendingMachineUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public VendingMachineUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public void onWiredCollision(final RoomUnit unit)
	{

	}

	@Override
	public void onWiredInteractsWithItem(final WiredUserItem wiredUserItem)
	{

	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{
		try
		{
			unit.walkTo(this.getNodeInFront(), -1, this);
		}
		catch (IPixelException e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);

			if (unit.getUnitType() == RoomUnitType.USER)
			{
				((RoomUser) unit).getConnection().send(new GenericErrorMessageComposer(e));
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
		response.appendString(this.getExtraData());
	}

	@Override
	public boolean onStep(final RoomUnit unit, final Node node)
	{
		return true;
	}

	@Override
	public void onWalkToSuccess(final RoomUnit unit)
	{
		if (this.getRoom() != unit.getRoom())
			return;

		final Thread t = new Thread(() -> {
			try
			{
				int itemId = VendingMachineUserItem.this.getBaseItem().getVendingIds()[(new Random()).nextInt(VendingMachineUserItem.this.getBaseItem().getVendingIds().length)];

				int rotation = Rotation.calculate(unit.getNode(), VendingMachineUserItem.this.getNode());
				unit.setRotation(rotation);
				unit.updateStatus();

				unit.carryItem(itemId);

				VendingMachineUserItem.this.setExtraData("1");
				VendingMachineUserItem.this.updateStateInRoom();

				Thread.sleep(430 * 2);

				VendingMachineUserItem.this.setExtraData("0");
				VendingMachineUserItem.this.updateStateInRoom();
			}
			catch (InterruptedException ignored)
			{

			}
		});
		t.start();
	}

	@Override
	public void onWalkToFailed(final RoomUnit unit)
	{

	}
}
