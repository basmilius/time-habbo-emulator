package com.basmilius.time.habbohotel.items.furniture;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.outgoing.notifications.LocalizedNotificationComposer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class KickCannonUserItem extends UserItem
{

	private boolean secondState = false;

	public KickCannonUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public KickCannonUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	public void explode(final List<Node> nodes)
	{
		(new Thread(() -> {
			try
			{
				Thread.sleep(480);
			}
			catch (InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}

			for (final Node node : nodes)
			{
				try
				{
					Thread.sleep(1);

					RoomUnit unitAtNode = this.getRoom().getRoomUnitsHandler().getUnitAt(node);

					if (unitAtNode == null)
						continue;

					if (unitAtNode.getUnitType() != RoomUnitType.USER)
						continue;

					((RoomUser) unitAtNode).kick();
					((RoomUser) unitAtNode).getConnection().send(new LocalizedNotificationComposer.RoomKickCannonballLocalizedNotificationComposer());
				}
				catch (InterruptedException e)
				{
					Bootstrap.getEngine().getLogging().handleException(e);
				}
			}
		})).start();
	}

	public void fire()
	{
		final List<Node> nodes = new ArrayList<>();
		final int start;
		final int maxNodes = 7;

		this.secondState = !this.secondState;
		this.updateStateInRoom();

		switch (this.getRot())
		{
			case 0:
				start = (this.getX() - 1);

				for (int x = start, y = 0; x > (start - maxNodes); x--, y++)
				{
					nodes.add(new Node(x, this.getY()));
				}
				break;
			case 2:
				start = (this.getY() - 1);

				for (int x = start, y = 0; x > (start - maxNodes); x--, y++)
				{
					nodes.add(new Node(this.getX(), x));
				}
				break;
			case 4:
				start = (this.getX() + 1);

				for (int x = start, y = 0; x < (start + maxNodes); x++, y++)
				{
					nodes.add(new Node(x, this.getY()));
				}
				break;
			case 6:
				start = (this.getY() + 1);

				for (int x = start, y = 0; x < (start + maxNodes); x++, y++)
				{
					nodes.add(new Node(this.getX(), x));
				}
				break;
		}

		this.explode(nodes);
	}

	@Override
	public void onWiredCollision(final RoomUnit unit)
	{

	}

	@Override
	public void onWiredInteractsWithItem(final WiredUserItem wiredItem)
	{
		this.fire();
	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{

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
		response.appendString(this.secondState ? "1" : "0");
	}

}
