package com.basmilius.time.habbohotel.items.furniture.wired.effect;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.algorithm.NodesAlgorithm;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredEffectUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.trigger.WiredTriggerCollisionUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.habbohotel.rooms.pathfinding.Pathfinder;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Queue;

@SuppressWarnings("unused")
public class WiredEffectFleeUserItem extends WiredEffectUserItem
{

	public WiredEffectFleeUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredEffectFleeUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	private void flee(final UserItem item)
	{
		RoomUnit unit = (new NodesAlgorithm(new NodesAlgorithm.NodesAlgorithmParameter(item.getNode(), item.getRoom()))).getNearestRoomUnit();

		if (unit == null || unit.getUnitType() != RoomUnitType.USER || item.hasUnitOnIt())
			return;

		Pathfinder pathfinder = new Pathfinder(this.getRoom(), unit);

		if (pathfinder.DistanceBetween(item.getNode()) > 10)
			return;

		final Node randomNode = this.getRoom().getRoomData().getRoomModel().getRandomNode();
		final Queue<Node> path = pathfinder.findPath(item, randomNode.getX(), randomNode.getY(), true);
		final Node rollNode = path.poll();

		item.rollTo(rollNode, this.getRoom().getRoomObjectsHandler().getStackHeight(rollNode, item), null);

		if (this.getRoom().getRoomUnitsHandler().getUnitAt(rollNode) != null)
		{
			final RoomUnit unitCollision = this.getRoom().getRoomUnitsHandler().getUnitAt(rollNode);
			item.onWiredCollision(unitCollision);
			
			if (unitCollision == null)
				return;

			if (unitCollision.getUnitType() == RoomUnitType.USER)
			{
				for (final WiredTriggerCollisionUserItem collisionHandler : this.getRoom().getRoomObjectsHandler().getItems(WiredTriggerCollisionUserItem.class))
				{
					collisionHandler.handleStack(unitCollision, item, null);
				}
			}
		}
	}

	@Override
	public boolean handle(final RoomUnit unit, final UserItem item, final String text)
	{
		if (this.getLinkedItems().size() > 0)
		{
			this.toggleState();

			this.getLinkedItems().forEach(this::flee);

			return true;
		}

		return false;
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
		response.appendString("");
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(8);
		response.appendInt(0);
		response.appendInt(0);
		response.appendInt(0);
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
