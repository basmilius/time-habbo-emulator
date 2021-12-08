package com.basmilius.time.habbohotel.items.furniture.wired.condition;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredConditionUserItem;
import com.basmilius.time.habbohotel.rooms.pathfinding.AffectedTile;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class WiredConditionFurniHasFurniOnUserItem extends WiredConditionUserItem
{

	public WiredConditionFurniHasFurniOnUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredConditionFurniHasFurniOnUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public boolean handle(final RoomUnit unit, final UserItem item, final String text)
	{
		boolean result = false;

		for (final UserItem linkedItem : this.getLinkedItems())
		{
			if (result)
				break;

			if (linkedItem.getNode().equals(this.getNode()))
				result = true;
			else if (linkedItem.getWidth() > 1 || linkedItem.getLength() > 1)
				for (final AffectedTile affectedNode : AffectedTile.getAffectedTilesAt(linkedItem))
				{
					if (result)
						break;

					if (affectedNode.X == this.getX() && affectedNode.Y == this.getY())
						result = true;
				}
			else if (this.getWidth() > 1 || this.getLength() > 1)
				for (final AffectedTile affectedNode : AffectedTile.getAffectedTilesAt(this))
				{
					if (result)
						break;

					if (affectedNode.X == linkedItem.getX() && affectedNode.Y == linkedItem.getY())
						result = true;
				}
		}

		return result;
	}

	@Override
	public void save(final ClientMessage packet)
	{
		packet.readInt();
		packet.readString();
		final int[] items = packet.readIntArray();
		this.saveLinkedItems(items);
	}

	@Override
	public void serializeWiredData(final ServerMessage response)
	{
		response.appendBoolean(false);
		response.appendInt(5);
		response.appendInt(this.getLinkedItems().size());
		for (final UserItem linkedItem : this.getLinkedItems())
		{
			response.appendInt(linkedItem.getId());
		}
		response.appendInt(this.getBaseItem().getSpriteId());
		response.appendInt(this.getId());
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(0);
		response.appendBoolean(false);
		response.appendBoolean(true);
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

}
