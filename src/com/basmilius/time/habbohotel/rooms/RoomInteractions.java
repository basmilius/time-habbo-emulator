package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.FxActivatorUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.trigger.WiredTriggerUserEntersRoomUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.trigger.WiredTriggerUserSaysSomethingUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.trigger.WiredTriggerUserWalksOffFurniUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.trigger.WiredTriggerUserWalksOnFurniUserItem;
import com.basmilius.time.habbohotel.rooms.pathfinding.AffectedTile;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.outgoing.rooms.UpdateHeightmapComposer;

import java.util.ArrayList;
import java.util.List;

public class RoomInteractions
{

	private final Room room;

	public RoomInteractions(final Room room)
	{
		this.room = room;
	}

	public Room getRoom()
	{
		return this.room;
	}

	public void updateTile(final UserItem item)
	{
		this.updateTile(item.getX(), item.getY(), item.getRot(), item.getWidth(), item.getLength());
	}

	public void updateTile(final int x, final int y, final int r, final int w, final int l)
	{
		final List<AffectedTile> affectedTiles = AffectedTile.getAffectedTilesAt(l, w, x, y, r);
		final List<Node> nodesToUpdate = new ArrayList<>();

		final AffectedTile selfTile = new AffectedTile(x, y, r);
		if (!affectedTiles.contains(selfTile))
		{
			affectedTiles.add(selfTile);
		}

		for (final AffectedTile tile : affectedTiles)
		{
			final List<UserItem> tileItems = this.getRoom().getRoomObjectsHandler().getFloorItemsAt(new Node(tile.X, tile.Y));
			final RoomUnit unit = this.getRoom().getRoomUnitsHandler().getUnitAt(new Node(tile.X, tile.Y));

			if (unit != null)
			{
				unit.getStatuses().remove("sit");
				unit.getStatuses().remove("lay");

				for (final UserItem item : tileItems)
				{
					if (item.getCanSit())
					{
						this.userWalkedOnSeat(unit, item);
					}
					else if (item.getCanLay())
					{
						this.userWalkedOnBed(unit, item);
					}
				}

				unit.setHeight(this.getRoom().getRoomObjectsHandler().getStackHeight(unit.getNode(), null));
				unit.updateStatus();
			}

			nodesToUpdate.add(new Node(tile.X, tile.Y, (int) (this.getRoom().getRoomObjectsHandler().getStackHeight(new Node(tile.X, tile.Y), null, false) * 256)));
		}

		this.getRoom().getRoomUnitsHandler().send(new UpdateHeightmapComposer(nodesToUpdate));
	}

	public void userEntersRoom(final RoomUser user)
	{
		if (user == null)
			return;

		user.getHabbo().userEntersRoom();

		for (final RoomBot bot : this.getRoom().getRoomUnitsHandler().getBots())
		{
			bot.onUserEntersRoom(user.getHabbo());
		}

		(new Thread(() -> {
			try
			{
				Thread.sleep(215);

				for (WiredUserItem wiredTriggerUserEntersRoom : this.getRoom().getRoomObjectsHandler().getItems(WiredTriggerUserEntersRoomUserItem.class))
				{
					wiredTriggerUserEntersRoom.handleStack(user, null, null);
				}
			}
			catch (InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		})).start();
	}

	public void unitInteractsWithItem(final RoomUnit unit, final UserItem item, final int param)
	{
		item.onUnitInteractsWithItem(unit, param);
	}

	public boolean userSaysSomething(final RoomUnit unit, final String text, final ChatBubble chatBubble, final boolean isShouted)
	{
		boolean result = false;

		for (final UserItem item : this.getRoom().getRoomObjectsHandler().getItems())
		{
			if (result)
				break;

			result = item.onUnitSaysSomething(unit, text, chatBubble, isShouted);
		}

		for (WiredUserItem wiredTriggerUserSaysSomething : this.getRoom().getRoomObjectsHandler().getItems(WiredTriggerUserSaysSomethingUserItem.class))
		{
			if (result)
				break;

			result = wiredTriggerUserSaysSomething.handleStack(unit, null, text);
		}

		return result;
	}

	public void userWalkedOnItem(final RoomUnit unit, final UserItem item)
	{
		Thread t = new Thread(() -> {
			try
			{
				item.onUnitWalksOnItem(unit);

				for (WiredUserItem wiredTriggerUserWalksOnFurni : this.getRoom().getRoomObjectsHandler().getItems(WiredTriggerUserWalksOnFurniUserItem.class))
				{
					wiredTriggerUserWalksOnFurni.handleStack(unit, item, null);
				}

				if (!FxActivatorUserItem.class.isInstance(item))
				{
					if (item.getBaseItem().getEffectMale() > 0 && unit.getGender().equalsIgnoreCase("M"))
					{
						unit.applyEffect(item.getBaseItem().getEffectMale());
					}
					if (item.getBaseItem().getEffectFemale() > 0 && unit.getGender().equalsIgnoreCase("F"))
					{
						unit.applyEffect(item.getBaseItem().getEffectFemale());
					}
				}
			}
			catch (Exception ignored)
			{

			}
		});
		t.start();
	}

	public void userWalkedOffItem(final RoomUnit unit, final UserItem item)
	{
		Thread t = new Thread(() -> {
			try
			{
				item.onUnitWalksOffItem(unit);

				for (WiredUserItem wiredTriggerUserWalksOffFurni : this.getRoom().getRoomObjectsHandler().getItems(WiredTriggerUserWalksOffFurniUserItem.class))
				{
					wiredTriggerUserWalksOffFurni.handleStack(unit, item, null);
				}

				boolean nextTileHasEffect = !this.getRoom().getRoomData().getRoomModel().isValidNode(unit.getNodeInFront());

				for (final UserItem nItem : this.getRoom().getRoomObjectsHandler().getFloorItemsAt(unit.getNodeInFront()))
				{
					if (nextTileHasEffect)
						break;

					nextTileHasEffect = (nItem.getBaseItem().getEffectMale() > 0 || nItem.getBaseItem().getEffectFemale() > 0);
				}

				if (!FxActivatorUserItem.class.isInstance(item) && !nextTileHasEffect && (unit.getEffectId() > 0 && item.getBaseItem().getEffectMale() > 0 || item.getBaseItem().getEffectFemale() > 0))
				{
					unit.applyEffect(0);
				}
			}
			catch (Exception ignored)
			{

			}
		});
		t.start();
	}

	public void userWalkedOnSeat(final RoomUnit user, final UserItem seat)
	{
		user.setRotation(seat.getRot());
		user.getStatuses().put("sit", Double.toString(seat.getBaseItem().getStackHeight()));
		user.updateStatus();
	}

	public void userWalkedOnBed(final RoomUnit user, final UserItem bed)
	{
		user.setRotation((bed.getRot() == 2) ? ((bed.getWidth() > bed.getLength()) ? 0 : 2) : ((bed.getWidth() > bed.getLength()) ? 2 : 0));
		user.getStatuses().put("lay", Double.toString(bed.getBaseItem().getStackHeight() + 1.00));
		user.updateStatus();
	}

	public void updateFloorItemState(final UserItem item, final RoomUnit unit, final int param)
	{
		item.getRoom().getRoomInteractions().unitInteractsWithItem(unit, item, param);
	}

	public void updateWallItemState(final UserItem item, final RoomUnit unit, final int param)
	{
		item.getRoom().getRoomInteractions().unitInteractsWithItem(unit, item, param);
	}

}
