package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.InteractionType;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.pathfinding.AffectedTile;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.outgoing.inventory.FurniListAddOrUpdateComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RemoveFloorItemComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RemoveWallItemComposer;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public final class RoomObjectsHandler
{

	private final Room room;
	
	private final List<UserItem> floorItems;
	private final List<UserItem> wallItems;

	public RoomObjectsHandler(final Room room)
	{
		this.room = room;
		
		this.floorItems = Lists.newLinkedList();
		this.wallItems = Lists.newLinkedList();
	}
	
	public final boolean canPlace(final Node node, final UserItem item)
	{
		return this.canPlace(node, item, false);
	}
	
	public final boolean canPlace(final Node node, final UserItem item, final boolean ignoreHeight)
	{
		boolean result = true;
		
		if (this.room.getRoomUnitsHandler().getUnitAt(node) != null)
			result = false;
		else if (item.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.MagicStack))
			result = true;
		else
			for (final UserItem floorItem : this.getFloorItemsAt(node))
			{
				if (ignoreHeight)
				{
					result = false;
					break;
				}
				else if (floorItem.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.MagicStack))
				{
					result = true;
					break;
				}
				
				result = !(!floorItem.getCanStack() && floorItem.getId() != item.getId());
			}
		
		return result;
	}
	
	public final boolean containsItem(final UserItem item)
	{
		return this.getItems().contains(item);
	}
	
	public final void loadFurniture()
	{
		Bootstrap.getEngine().getGame().getItemsManager().loadUserItemsForRoom(this.room);
		
		this.floorItems.clear();
		this.wallItems.clear();
		
		this.floorItems.addAll(Bootstrap.getEngine().getGame().getItemsManager().getFloorItemsForRoom(this.room));
		this.wallItems.addAll(Bootstrap.getEngine().getGame().getItemsManager().getWallItemsForRoom(this.room));
		
		this.updateGameMap();
	}
	
	public final List<UserItem> getFloorItemsAt(final Node node)
	{
		return this.getFloorItemsAt(node, 0.00, new String[0]);
	}
	
	public final List<UserItem> getFloorItemsAt(final Node node, final double z)
	{
		return this.getFloorItemsAt(node, z, new String[0]);
	}
	
	public final List<UserItem> getFloorItemsAt(final Node node, final double z, final String[] state)
	{
		final List<UserItem> items = Lists.newLinkedList();
		
		for (final UserItem item : this.floorItems)
		{
			if (state.length > 0)
			{
				boolean result = false;
				for (final String aState : state)
				{
					if (item.getExtraData().equalsIgnoreCase(aState))
					{
						result = true;
					}
				}

				if (!result)
					continue;
			}
			
			if (item.getNode().equals(node))
				items.add(item);
			else if (item.getWidth() > 1 || item.getLength() > 1)
			{
				final List<AffectedTile> affectedNodes = AffectedTile.getAffectedTilesAt(item);
				affectedNodes.add(new AffectedTile(item.getX(), item.getY(), 0));
				affectedNodes.stream().filter(tile -> tile.X == node.getX() && tile.Y == node.getY() && !items.contains(item) && item.getZ() >= z).forEach(tile -> items.add(item));
			}
		}
		
		return items;
	}
	
	public synchronized final UserItem getFloorItem(final int id)
	{
		final List<UserItem> items = this.getFloorItems().stream().filter(i -> i.getId() == id).collect(Collectors.toList());
		if (items.size() > 0)
			return items.get(0);
		return null;
	}
	
	public synchronized final List<UserItem> getFloorItems()
	{
		return this.floorItems;
	}
	
	public synchronized final List<UserItem> getItems()
	{
		final List<UserItem> items = Lists.newLinkedList();
		
		items.addAll(this.floorItems);
		items.addAll(this.wallItems);
		
		return items;
	}
	
	@SuppressWarnings("unchecked")
	public synchronized final <T extends UserItem> List<T> getItems(Class<T> itemClass)
	{
		return this.getItems().stream().filter(itemClass::isInstance).map(item -> (T) item).collect(Collectors.toList());
	}
	
	public synchronized final List<UserItem> getItemsWithOwner(final Habbo habbo)
	{
		return this.getItems().stream().filter(i -> i.getHabbo() == habbo).collect(Collectors.toList());
	}

	public synchronized final UserItem getWallItem(final int id)
	{
		final List<UserItem> items = this.getWallItems().stream().filter(i -> i.getId() == id).collect(Collectors.toList());
		if (items.size() > 0)
			return items.get(0);
		return null;
	}
	
	public synchronized final List<UserItem> getWallItems()
	{
		return this.wallItems;
	}
	
	public final double getStackHeight(final Node node, final UserItem item)
	{
		return this.getStackHeight(node, item, false);
	}
	
	public final double getStackHeight(final Node node, final UserItem item, final boolean forHeightmap)
	{
		if (!forHeightmap && item != null && item.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.MagicStack))
			return 0.0;
		
		boolean stackable = true;
		boolean done = false;
		double height = this.room.getRoomData().getRoomModel().getNodeHeights()[node.getX()][node.getY()];
		
		for (final UserItem nodeItem : this.getFloorItemsAt(node))
		{
			if (item != null && item.getId() == nodeItem.getId() || done)
				continue;
			
			stackable = nodeItem.getCanStack();
			
			double newHeight = (nodeItem.getTotalHeight() + ((nodeItem.getCanSit()) ? (0 - nodeItem.getStackHeight()) : 0));
			if (newHeight > height)
				height = newHeight;
			
			if (nodeItem.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.MagicStack))
			{
				height = nodeItem.getZ();
				stackable = true;
				done = true;
			}
			else if (nodeItem.getCanSit() || nodeItem.getCanLay())
			{
				height = nodeItem.getZ();
				done = true;
			}
		}
		
		if (forHeightmap && stackable)
			return (height * 256);
		else if (forHeightmap)
			return 65535;
		else
			return height;
	}
	
	public final boolean isSomethingOnNode(final Node node)
	{
		if (this.room.getRoomUnitsHandler().getUnitAt(node) != null)
			return true;
		
		boolean something = false;
		
		for (final UserItem item : this.getFloorItemsAt(node))
		{
			if (something)
				break;
			
			something = !(item.getIsWalkable() || item.getCanStack());
		}
		
		return something;
	}
	
	public final void pickUp(final UserItem item)
	{
		if (item.getIsWallItem())
		{
			this.room.getRoomUnitsHandler().send(new RemoveWallItemComposer(item, item.getHabbo()));
		}
		else
		{
			this.room.getRoomUnitsHandler().send(new RemoveFloorItemComposer(item, item.getHabbo()));
			this.room.getRoomInteractions().updateTile(item);
		}
		
		item.setRoom(null);
		if (this.floorItems.contains(item))
			this.floorItems.remove(item);
		else if(this.wallItems.contains(item))
			this.wallItems.remove(item);
		
		if (item.getHabbo().isOnline())
		{
			if (!item.getHabbo().getHabboCache().getInventoryOpened())
				return;
			item.getHabbo().getConnection().send(new FurniListAddOrUpdateComposer(item));
		}
		
		this.updateGameMap();
	}
	
	public final void updateGameMap()
	{
		for (final Node node : this.room.getGameMap().getNodes())
		{
			boolean walkable = (this.room.getRoomData().getRoomModel().getNodeStates()[node.getX()][node.getY()] == RoomTileState.VALID);
			double lastHeight = 0.00;
			
			for (final UserItem item : this.getFloorItemsAt(node))
			{
				if (!walkable && item.getTotalHeight() < lastHeight)
					continue;
				
				lastHeight = item.getTotalHeight();
				walkable = item.getIsWalkable();
			}
			
			this.room.getGameMap().setWalkable(node.getX(), node.getY(), walkable);
		}
	}
	
}
