package com.basmilius.time.habbohotel.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.core.event.IEventDispatcher;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.abstracts.ISave;
import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.collections.UserItemsList;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.furniture.*;
import com.basmilius.time.habbohotel.items.furniture.games.freeze.*;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredBlobUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.condition.WiredConditionFurniHasFurniOnUserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.effect.*;
import com.basmilius.time.habbohotel.items.furniture.wired.trigger.*;
import com.basmilius.time.habbohotel.rooms.Room;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class ItemsManager extends IManager
{

	private final List<Item> items;
	private final UserItemsList userItems;
	private final Queue<UserItem> queueUpdateItems;
	private final ItemsManagerSaver saver;
	private int latestId;

	public ItemsManager()
	{
		this.latestId = 0;
		this.items = Lists.newLinkedList();
		this.userItems = new UserItemsList(Lists.newArrayList());
		this.queueUpdateItems = Queues.newConcurrentLinkedQueue();
		this.saver = new ItemsManagerSaver();
	}

	public void reloadItems()
	{
		Bootstrap.getEngine().getLogging().logNoNewLine(ItemsManager.class, "Reloading items .. ");

		try
		{
			this.loadItems(true);
			Bootstrap.getEngine().getLogging().logOK();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			Bootstrap.getEngine().getLogging().logFailed();
		}
	}

	public int getLatestId()
	{
		this.latestId++;
		return this.latestId;
	}

	public synchronized final UserItem createItem(final Habbo habbo, final Room room, final Item item, final CatalogueItem catalogueItem, final Habbo itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		synchronized (this.userItems)
		{
			try
			{
				final UserItem newItem = this.getUserItemClassFromInteractionType(item.getInteractionType(true)).getConstructor(int.class, int.class, int.class, int.class, int.class, BoughtType.class, int.class).newInstance(habbo.getId(), ((room != null) ? room.getRoomData().getId() : 0), item.getId(), catalogueItem.getId(), itemBoughtBy.getId(), itemBoughtType, itemExpire);
				this.userItems.add(newItem);
				return newItem;
			}
			catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}

			return null;
		}
	}

	public final synchronized void loadItems(final boolean update) throws SQLException
	{
		synchronized (this.items)
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM items_base");
			{
				if (statement != null)
				{
					final ResultSet result = statement.executeQuery();
					while (result.next())
					{
						final int id = result.getInt("id");
						if (update && this.hasItem(id))
						{
							//noinspection ConstantConditions
							this.getItem(id).update(result);
						}
						else if (!this.hasItem(id))
						{
							this.items.add(new Item(result));
						}
					}
				}
			}
		}
	}

	public final synchronized void loadUserItemsForHabbo(final Habbo habbo)
	{
		synchronized (this.userItems)
		{
			try
			{
				final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM items WHERE user_id = ?");
				{
					if (statement != null)
					{
						statement.setInt(1, habbo.getId());
						final ResultSet result = statement.executeQuery();

						while (result.next())
						{
							if (!this.hasUserItem(result.getInt("id")))
							{
								synchronized (this.userItems)
								{
									//noinspection ConstantConditions
									this.userItems.add(this.getUserItemClassFromResultSet(result).getConstructor(ResultSet.class).newInstance(result));
								}
							}
						}
					}
				}
			}
			catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		}
	}

	public final synchronized void loadUserItemsForRoom(final Room room)
	{
		synchronized (this.userItems)
		{
			try
			{
				final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM items WHERE room_id = ?");
				{
					if (statement != null)
					{
						statement.setInt(1, room.getRoomData().getId());
						final ResultSet result = statement.executeQuery();

						while (result.next())
						{
							if (!this.hasUserItem(result.getInt("id")))
							{
								synchronized (this.userItems)
								{
									//noinspection ConstantConditions
									this.userItems.add(this.getUserItemClassFromResultSet(result).getConstructor(ResultSet.class).newInstance(result));
								}
							}
						}
					}
				}
			}
			catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		}
	}

	public final synchronized Item getItem(final int id)
	{
		synchronized (this.items)
		{
			final List<Item> results = this.items.stream().filter(i -> i.getId() == id).collect(Collectors.toList());
			if (results.size() > 0)
				return results.get(0);
			return null;
		}
	}

	public final synchronized Item getItem(final String itemName)
	{
		synchronized (this.items)
		{
			final List<Item> results = this.items.stream().filter(i -> i.getItemName().equalsIgnoreCase(itemName)).collect(Collectors.toList());
			if (results.size() > 0)
				return results.get(0);
			return null;
		}
	}
	
	public final boolean hasItem(final int itemId)
	{
		synchronized (this.items)
		{
			return this.items.stream().filter(i -> i.getId() == itemId).toArray().length > 0;
		}
	}

	public final synchronized UserItem getUserItem(final int id)
	{
		synchronized (this.userItems)
		{
			final List<UserItem> results = this.userItems.stream().filter(i -> i.getId() == id).collect(Collectors.toList());
			if (results.size() > 0)
				return results.get(0);
			return null;
		}
	}

	@SuppressWarnings ("unchecked")
	public final <TUserItem extends UserItem> TUserItem getUserItemWithType(final int id, Class<TUserItem> furnitureType)
	{
		final UserItem userItem = this.getUserItem(id);

		if (furnitureType.isInstance(userItem))
		{
			return (TUserItem) userItem;
		}

		return null;
	}

	public final synchronized boolean hasUserItem(final int id)
	{
		synchronized (this.userItems)
		{
			return this.userItems.stream().filter(i -> i.getId() == id).toArray().length > 0;
		}
	}

	public final synchronized UserItemsList getItemsForUser(final Habbo habbo)
	{
		synchronized (this.userItems)
		{
			return new UserItemsList(this.userItems.stream().filter(item -> item.getHabbo() != null && item.getHabbo().getId() == habbo.getId() && item.getRoom() == null).collect(Collectors.toList()));
		}
	}

	public final synchronized UserItemsList getFloorItemsForRoom(final Room room)
	{
		synchronized (this.userItems)
		{
			return new UserItemsList(this.userItems.stream().filter(item -> item.getRoom() != null && !item.getIsWallItem() && item.getRoom().getRoomData().getId() == room.getRoomData().getId()).collect(Collectors.toList()));
		}
	}

	public final synchronized UserItemsList getWallItemsForRoom(final Room room)
	{
		synchronized (this.userItems)
		{
			return new UserItemsList(this.userItems.stream().filter(item -> item.getRoom() != null && item.getIsWallItem() && item.getRoom().getRoomData().getId() == room.getRoomData().getId() && !(item.getCatalogItem().getName().contains("wallpaper") || item.getCatalogItem().getName().contains("floor") || item.getCatalogItem().getName().contains("landscape"))).collect(Collectors.toList()));
		}
	}

	public final BoughtType getBoughtTypeByString(final String type)
	{
		if (type.equals("builders_club"))
			return BoughtType.BUILDERS_CLUB;
		else if (type.equals("rentable"))
			return BoughtType.RENTABLE;

		return BoughtType.NORMAL;
	}

	public final Class<? extends UserItem> getUserItemClassFromResultSet(final ResultSet resultSet) throws SQLException
	{
		final Item item = this.getItem(resultSet.getInt("item_id"));
		
		if (item == null)
			return null;
		
		return this.getUserItemClassFromInteractionType(item.getInteractionType());
	}

	public final Class<? extends UserItem> getUserItemClassFromInteractionType(final String type)
	{
		switch (type)
		{
			case InteractionType.AdsBackground:
				return AdsBackgroundUserItem.class;
			case InteractionType.BadgeDisplay:
				return BadgeDisplayUserItem.class;
			case InteractionType.BattleBanzai:
				return BattleBanzaiUserItem.class;
			case InteractionType.ExternalImage:
				return ExternalImageUserItem.class;
			case InteractionType.FloorSwitch:
				return FloorSwitchUserItem.class;
			case InteractionType.FxActivator:
				return FxActivatorUserItem.class;
			case InteractionType.Gate:
				return GateUserItem.class;
			case InteractionType.GuildFurni:
				return GuildFurniUserItem.class;
			case InteractionType.KickCannon:
				return KickCannonUserItem.class;
			case InteractionType.LoveLock:
				return LoveLockUserItem.class;
			case InteractionType.MagicStack:
				return MagicStackUserItem.class;
			case InteractionType.Mannequin:
				return MannequinUserItem.class;
			case InteractionType.MoodLight:
				return MoodLightUserItem.class;
			case InteractionType.Roller:
				return RollerUserItem.class;
			case InteractionType.RoomBg:
				return RoomBackgroundUserItem.class;
			case InteractionType.SoundFx:
				return SoundFxUserItem.class;
			case InteractionType.SoundMachine:
				return SoundMachineUserItem.class;
			case InteractionType.Teleport:
				return TeleportUserItem.class;
			case InteractionType.VendingMachine:
				return VendingMachineUserItem.class;
			case InteractionType.YouTubeTV:
				return YouTubeTelevisionUserItem.class;

			case InteractionType.Freeze_Block:
				return FreezeBlockGameUserItem.class;
			case InteractionType.Freeze_Exit:
				return FreezeExitTileGameUserItem.class;
			case InteractionType.Freeze_GateBlue:
				return FreezeGateBlueGameUserItem.class;
			case InteractionType.Freeze_GateGreen:
				return FreezeGateGreenGameUserItem.class;
			case InteractionType.Freeze_GateRed:
				return FreezeGateRedGameUserItem.class;
			case InteractionType.Freeze_GateYellow:
				return FreezeGateYellowGameUserItem.class;
			case InteractionType.Freeze_Tile:
				return FreezeTileGameUserItem.class;
			case InteractionType.Freeze_Timer:
				return FreezeTimerGameUserItem.class;

			case InteractionType.Wired_Addon_Blob:
				return WiredBlobUserItem.class;

			case InteractionType.Wired_Condition_HasFurniOn:
				return WiredConditionFurniHasFurniOnUserItem.class;

			case InteractionType.Wired_Effect_Chase:
				return WiredEffectChaseUserItem.class;
			case InteractionType.Wired_Effect_Flee:
				return WiredEffectFleeUserItem.class;
			case InteractionType.Wired_Effect_MatchToShot:
				return WiredEffectMatchToShotUserItem.class;
			case InteractionType.Wired_Effect_MoveRotate:
				return WiredEffectMoveRotateUserItem.class;
			case InteractionType.Wired_Effect_ShowMessage:
				return WiredEffectShowMessageUserItem.class;
			case InteractionType.Wired_Effect_TeleportTo:
				return WiredEffectTeleportToFurniUserItem.class;
			case InteractionType.Wired_Effect_ToggleState:
				return WiredEffectToggleFurniStateUserItem.class;

			case InteractionType.Wired_Trigger_Collision:
				return WiredTriggerCollisionUserItem.class;
			case InteractionType.Wired_Trigger_EnterRoom:
				return WiredTriggerUserEntersRoomUserItem.class;
			case InteractionType.Wired_Trigger_PeriodiCally:
				return WiredTriggerPeriodiCallyUserItem.class;
			case InteractionType.Wired_Trigger_PeriodLong:
				return WiredTriggerPeriodLongUserItem.class;
			case InteractionType.Wired_Trigger_SaysSomething:
				return WiredTriggerUserSaysSomethingUserItem.class;
			case InteractionType.Wired_Trigger_StateChanged:
				return WiredTriggerFurniStateChangedUserItem.class;
			case InteractionType.Wired_Trigger_WalksOffFurni:
				return WiredTriggerUserWalksOffFurniUserItem.class;
			case InteractionType.Wired_Trigger_WalksOnFurni:
				return WiredTriggerUserWalksOnFurniUserItem.class;

			case InteractionType.Default:
			default:
				return DefaultUserItem.class;
		}
	}

	@Override
	public final void initialize()
	{
		Bootstrap.getEngine().getLogging().logNoNewLine(ItemsManager.class, "Loading items .. ");

		this.saver.initialize();

		try
		{
			this.loadItems(false);
			try
			{
				this.latestId = Bootstrap.getEngine().getDatabase().readInt32("SELECT id FROM items ORDER BY id DESC LIMIT 1");
			}
			catch (Exception e)
			{
				this.latestId = 0;
			}

			Bootstrap.getEngine().getLogging().logOK();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			Bootstrap.getEngine().onLaunchFail();
		}
	}

	@Override
	public final void dispose()
	{
		try
		{
			this.saver.save();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}

		this.items.clear();
		this.latestId = -1;
		this.userItems.clear();
	}

	public final void delete(final UserItem userItem)
	{
		synchronized (this.userItems)
		{
			this.userItems.remove(userItem);
		}
	}

	static public abstract class ItemsManagerNotifier extends IEventDispatcher
	{

		public final void addItemToUpdateQueue(final UserItem item)
		{
			if (this.containsUpdateQueue(item))
				return;

			Bootstrap.getEngine().getGame().getItemsManager().queueUpdateItems.add(item);
			Bootstrap.getEngine().getGame().getItemsManager().saver.setNeedsUpdate(Bootstrap.getEngine().getGame().getItemsManager().queueUpdateItems.size() > 0);
		}

		public final boolean containsUpdateQueue(final UserItem item)
		{
			return Bootstrap.getEngine().getGame().getItemsManager().queueUpdateItems.contains(item);
		}

		@SuppressWarnings("UnusedDeclaration")
		public final void removeItemFromUpdateQueue(final UserItem item)
		{
			if (!this.containsUpdateQueue(item))
				return;

			Bootstrap.getEngine().getGame().getItemsManager().queueUpdateItems.remove(item);
			Bootstrap.getEngine().getGame().getItemsManager().saver.setNeedsUpdate(Bootstrap.getEngine().getGame().getItemsManager().queueUpdateItems.size() > 0);
		}

	}

	static private final class ItemsManagerSaver extends ISave
	{

		public final void initialize()
		{
			this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.items", 30000));
		}

		/**
		 * Executes when needs save.
		 *
		 * @throws java.sql.SQLException
		 */
		@Override
		protected void save() throws SQLException
		{
			int updateItemsCount = Bootstrap.getEngine().getGame().getItemsManager().queueUpdateItems.size();

			while (updateItemsCount > 0)
			{
				final UserItemsList updateItems = new UserItemsList(new ArrayList<>());
				String queryBuilder = "INSERT INTO `items` (id, user_id, room_id, item_id, catalogue_item_id, wall_pos, x, y, z, rot, item_bought_by, item_bought, item_linked_with, extra_data, server_data) VALUES";
				for (int i = 0; i < 50 && Bootstrap.getEngine().getGame().getItemsManager().queueUpdateItems.peek() != null; i++)
				{
					queryBuilder += " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?),";
					updateItemsCount--;
					updateItems.add(Bootstrap.getEngine().getGame().getItemsManager().queueUpdateItems.poll());
				}
				queryBuilder = queryBuilder.substring(0, queryBuilder.length() - 1);
				queryBuilder += " ON DUPLICATE KEY UPDATE id = VALUES(id), user_id = VALUES(user_id), room_id = VALUES(room_id), wall_pos = VALUES(wall_pos), x = VALUES(x), y = VALUES(y), z = VALUES(z), rot = VALUES(rot), extra_data = VALUES(extra_data), server_data = VALUES(server_data), item_linked_with = VALUES(item_linked_with)";

				try
				{
					final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare(queryBuilder);
					
					if (statement == null)
						return;
					
					int i = 0;
					for (final UserItem item : updateItems)
					{
						statement.setInt(++i, item.getId());
						statement.setInt(++i, item.getHabbo().getId());
						statement.setInt(++i, (item.getRoom() != null) ? item.getRoom().getRoomData().getId() : 0);
						statement.setInt(++i, item.getBaseItem().getId());
						statement.setInt(++i, (item.getCatalogItem() != null ? item.getCatalogItem().getId() : 0));
						statement.setString(++i, item.getWallPos());
						statement.setInt(++i, item.getX());
						statement.setInt(++i, item.getY());
						statement.setDouble(++i, item.getZ());
						statement.setInt(++i, item.getRot());
						statement.setInt(++i, (item.getItemBoughtBy() != null) ? item.getItemBoughtBy().getId() : 0);
						statement.setInt(++i, item.getItemBought());
						statement.setInt(++i, item.getItemLinkedWith() != null ? item.getItemLinkedWith().getId() : 0);
						statement.setString(++i, item.getExtraData());
						statement.setString(++i, item.getServerData().toString());
					}
					statement.execute();
				}
				catch (SQLException e)
				{
					Bootstrap.getEngine().getLogging().handleSQLException(e);
				}
			}
		}

	}

}
