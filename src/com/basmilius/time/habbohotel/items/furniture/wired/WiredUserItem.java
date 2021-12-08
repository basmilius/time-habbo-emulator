package com.basmilius.time.habbohotel.items.furniture.wired;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.collections.UserItemsList;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.json.JSONArray;
import com.basmilius.time.util.json.JSONObject;
import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class WiredUserItem extends UserItem
{

	private static final Comparator<UserItem> ORDER_FROM_BOTTOM_TO_TOP = (a, b) -> (int) ((a.getZ() - b.getZ()) * 100);

	public WiredUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@SuppressWarnings("unchecked")
	public final <TReturnType> TReturnType getSetting(final String setting, final TReturnType defaultValue)
	{
		this.hasWiredServerData();

		if (this.getServerData().getJSONObject("wired").has("settings"))
		{
			if (this.getServerData().getJSONObject("wired").getJSONObject("settings").has(setting))
			{
				return (TReturnType) this.getServerData().getJSONObject("wired").getJSONObject("settings").get(setting);
			}
		}
		return defaultValue;
	}
	
	@SuppressWarnings("unchecked")
	public final <TReturnType> TReturnType getValue(final String key, final TReturnType defaultValue)
	{
		this.hasWiredServerData();
		
		if (this.getServerData().getJSONObject("wired").has(key))
		{
			return (TReturnType) this.getServerData().getJSONObject("wired").get(key);
		}
		
		return defaultValue;
	}
	
	public final <TReturnType> void setSetting(final String setting, final TReturnType value)
	{
		this.hasWiredServerData();
		
		if (!this.getServerData().getJSONObject("wired").has("settings"))
		{
			this.getServerData().getJSONObject("wired").put("settings", new JSONObject());
		}

		if (this.getServerData().getJSONObject("wired").getJSONObject("settings").has(setting))
		{
			this.getServerData().getJSONObject("wired").getJSONObject("settings").remove(setting);
		}

		this.getServerData().getJSONObject("wired").getJSONObject("settings").put(setting, value);
		
		this.saveServerData();
	}

	public final <TReturnType> void setValue(final String key, final TReturnType value)
	{
		this.hasWiredServerData();
		
		if (this.getServerData().getJSONObject("wired").has(key))
		{
			this.getServerData().getJSONObject("wired").remove(key);
		}
		this.getServerData().getJSONObject("wired").put(key, value);
		this.saveServerData();
	}
	
	public void hasWiredServerData()
	{
		if (!this.getServerData().has("wired"))
		{
			this.getServerData().put("wired", new JSONObject());
			this.saveServerData();
		}
	}
	
	public final UserItemsList getLinkedItems()
	{
		this.hasWiredServerData();
		
		final UserItemsList items = new UserItemsList(Lists.newArrayList());
		
		if (this.getServerData().getJSONObject("wired").has("linked_items"))
		{
			final JSONArray linkedItems = this.getServerData().getJSONObject("wired").getJSONArray("linked_items");
			for (int i = 0; i < linkedItems.length(); i++)
			{
				final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(linkedItems.optInt(i));
				if (item == null)
					continue;
				if (item.getRoom() != this.getRoom())
					continue;
				items.add(item);
			}
		}
		
		return items;
	}
	
	public final boolean isLinkedItem(final UserItem item)
	{
		return this.getLinkedItems().stream().filter(i -> i.getId() == item.getId()).toArray().length > 0;
	}

	public final List<WiredUserItem> getWiredStack()
	{
		final List<UserItem> items = this.getRoom().getRoomObjectsHandler().getFloorItemsAt(this.getNode()).stream().filter(WiredUserItem.class::isInstance).collect(Collectors.toList());
		final List<WiredUserItem> wiredItems = items.stream().filter(WiredUserItem.class::isInstance).map(item -> (WiredUserItem) item).collect(Collectors.toList());

		Collections.sort(wiredItems, ORDER_FROM_BOTTOM_TO_TOP);

		return wiredItems;
	}
	
	public final void saveLinkedItems(final int[] items)
	{
		this.hasWiredServerData();
		
		if (this.getServerData().getJSONObject("wired").has("linked_items"))
		{
			this.getServerData().getJSONObject("wired").remove("linked_items");
		}
		
		this.getServerData().getJSONObject("wired").put("linked_items", new JSONArray());
		
		for (final int item : items)
		{
			this.getServerData().getJSONObject("wired").getJSONArray("linked_items").put(item);
		}
		
		this.saveServerData();
	}

	public abstract boolean handle(final RoomUnit unit, final UserItem item, final String text);

	public final boolean handleStack(final RoomUnit unit, final UserItem item, final String text)
	{
		List<WiredUserItem> handledItems = new ArrayList<>();
		boolean canHandle = false;
		boolean result = true;

		for (WiredUserItem wiredItem : this.getWiredStack())
		{
			if (!canHandle && !this.getClass().isInstance(wiredItem))
				continue;
			else if (!canHandle)
				canHandle = true;

			if (handledItems.contains(wiredItem))
				break;

			handledItems.add(wiredItem);

			if (unit == null)
			{
				for (RoomUnit _unit : wiredItem.getRoom().getRoomUnitsHandler().getUnits())
				{
					wiredItem.handle(_unit, item, text);
				}
			}
			else
			{
				result = wiredItem.handle(unit, item, text);
			}

			if (!result)
				break;
		}

		return result;
	}

	@Override
	public final void onWiredCollision(final RoomUnit unit)
	{

	}

	@Override
	public final void onWiredInteractsWithItem(final WiredUserItem wiredUserItem)
	{

	}

	@Override
	public final void serializeData(final ServerMessage response, final boolean isInventory, final boolean isDataOnly)
	{
		if (!isDataOnly)
			response.appendInt(1);

		response.appendInt(0);
		response.appendString(this.getExtraData());
	}

	public abstract void save(final ClientMessage packet);

	public abstract void serializeWiredData(final ServerMessage response);

	public final void toggleState()
	{
		if (this.getExtraData().equals("0"))
		{
			this.setExtraData("1");
		}
		else
		{
			this.setExtraData("0");
		}

		this.updateStateInRoom();
	}

}
