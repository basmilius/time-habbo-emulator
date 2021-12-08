package com.basmilius.time.habbohotel.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.catalogue.GiftWrappingParams;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.youtube.YouTubePlaylist;
import com.basmilius.time.habbohotel.habbo.youtube.YouTubeVideo;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.games.IGame;
import com.basmilius.time.habbohotel.rooms.pathfinding.AffectedTile;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.outgoing.items.YoutubeDisplayPlayVideoComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.*;
import com.basmilius.time.util.ObjectUtils;
import com.basmilius.time.util.TimeUtils;
import com.basmilius.time.util.json.JSONException;
import com.basmilius.time.util.json.JSONObject;
import com.google.common.collect.Lists;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class UserItem extends ItemsManager.ItemsManagerNotifier implements ISerialize
{

	private int id;
	private Habbo habbo;
	private Room room;
	private Item item;
	private CatalogueItem catalogItem;
	private int x;
	private int y;
	private double z;
	private int rot;
	private String wallPos;
	private int itemBought;
	private Habbo itemBoughtBy;
	private BoughtType itemBoughtType;
	private int itemExpire;
	private int itemLinkedWith;
	private String extraData;
	private JSONObject serverData;
	private int isWalkable;
	private double stackHeight;

	private Thread updateThread;

	private YouTubePlaylist youTubePlaylist;
	private YouTubeVideo youTubeVideo;
	private IGame game;

	{
		this.game = null;
	}

	public UserItem(ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this.room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(result.getInt("room_id"));
		this.item = Bootstrap.getEngine().getGame().getItemsManager().getItem(result.getInt("item_id"));
		this.catalogItem = Bootstrap.getEngine().getGame().getCatalogueManager().getItem(result.getInt("catalogue_item_id"));
		this.x = result.getInt("x");
		this.y = result.getInt("y");
		this.z = result.getDouble("z");
		this.rot = result.getInt("rot");
		this.wallPos = result.getString("wall_pos");
		this.itemBought = result.getInt("item_bought");
		this.itemBoughtBy = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("item_bought_by"));
		this.itemBoughtType = Bootstrap.getEngine().getGame().getItemsManager().getBoughtTypeByString(result.getString("item_bought_type"));
		this.itemExpire = result.getInt("item_expire");
		this.itemLinkedWith = result.getInt("item_linked_with");
		this.extraData = result.getString("extra_data");
		try
		{
			this.serverData = new JSONObject(result.getString("server_data"));
		}
		catch (JSONException ignored)
		{
			this.serverData = new JSONObject("{}");
		}

		this.isWalkable = 0;

		if (this.getBaseItem().hasAdjustableHeights() && !this.extraData.isEmpty() && ObjectUtils.isNumeric(this.extraData))
		{
			this.setStackHeight(this.getBaseItem().getAdjustableHeight(Integer.parseInt(this.extraData)));
		}

		this.resolveYouTubeData(false, null);
	}

	public UserItem(int habboId, int roomId, int itemId, int catalogueItemId, int itemBoughtBy, BoughtType itemBoughtType, int itemExpire)
	{
		this.id = Bootstrap.getEngine().getGame().getItemsManager().getLatestId();
		this.habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(habboId);
		this.room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(roomId);
		this.item = Bootstrap.getEngine().getGame().getItemsManager().getItem(itemId);
		this.catalogItem = Bootstrap.getEngine().getGame().getCatalogueManager().getItem(catalogueItemId);
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.rot = 0;
		this.wallPos = "";
		this.itemBought = TimeUtils.getUnixTimestamp();
		this.itemBoughtBy = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(itemBoughtBy);
		this.itemBoughtType = itemBoughtType;
		this.itemExpire = itemExpire;
		this.itemLinkedWith = 0;
		this.extraData = "";
		this.serverData = new JSONObject();

		this.isWalkable = 0;
		this.stackHeight = 0.0;

		this.resolveYouTubeData(false, null);

		this.addItemToUpdateQueue(this);

		if (this.getBaseItem().hasAdjustableHeights())
		{
			this.setStackHeight(this.getBaseItem().getAdjustableHeight(Integer.parseInt((!this.extraData.isEmpty()) ? this.extraData : "0")));
		}

		this.resolveYouTubeData(false, null);
	}

	public final int getId()
	{
		return this.id;
	}

	public final Habbo getHabbo()
	{
		return this.habbo;
	}

	public final void setHabbo(final Habbo habbo)
	{
		this.habbo = habbo;
		this.addItemToUpdateQueue(this);
	}

	public final Room getRoom()
	{
		return this.room;
	}

	public final void setRoom(final Room room)
	{
		if (this.room == null && room == null || (this.room != null && room != null && this.room.getRoomData().getId() == room.getRoomData().getId()))
			return;
		
		this.room = room;
		this.addItemToUpdateQueue(this);
	}

	public final Item getBaseItem()
	{
		return this.item;
	}

	public final CatalogueItem getCatalogItem()
	{
		return this.catalogItem;
	}

	public final int getX()
	{
		return this.x;
	}

	public final void setX(final int x)
	{
		if (this.x == x)
			return;
		this.x = x;
		this.addItemToUpdateQueue(this);
	}

	public final int getY()
	{
		return this.y;
	}

	public final void setY(final int y)
	{
		if (this.y == y)
			return;
		this.y = y;
		this.addItemToUpdateQueue(this);
	}

	public final double getZ()
	{
		return this.z;
	}

	public final void setZ(final double z)
	{
		if (this.z == z)
			return;
		this.z = z;
		this.addItemToUpdateQueue(this);
	}
	
	public final List<Node> getNodes()
	{
		final List<Node> nodes = Lists.newArrayList();
		
		nodes.add(this.getNode());
		nodes.addAll(AffectedTile.getAffectedTilesAt(this).stream().map(tile -> new Node(tile.X, tile.Y)).collect(Collectors.toList()));
		
		return nodes;
	}

	public final double getTotalHeight()
	{
		return this.getZ() + this.getBaseItem().getStackHeight();
	}

	public final int getRot()
	{
		return this.rot;
	}

	public final void setRot(final int rot)
	{
		if (this.rot == rot)
			return;
		this.rot = rot;
		this.addItemToUpdateQueue(this);
	}

	public final String getWallPos()
	{
		return this.wallPos;
	}

	public final void setWallPos(final String wallPos)
	{
		if (this.wallPos.equals(wallPos))
			return;
		this.wallPos = wallPos;
		this.addItemToUpdateQueue(this);
	}

	public final int getItemBought()
	{
		return this.itemBought;
	}

	public final Habbo getItemBoughtBy()
	{
		return this.itemBoughtBy;
	}

	public final BoughtType getItemBoughtType()
	{
		return this.itemBoughtType;
	}

	public final int getItemExpire()
	{
		return this.itemExpire;
	}

	public final UserItem getItemLinkedWith()
	{
		return Bootstrap.getEngine().getGame().getItemsManager().getUserItem(this.itemLinkedWith);
	}

	public final void setItemLinkedWith(final UserItem userItem)
	{
		this.itemLinkedWith = userItem.getId();
		this.addItemToUpdateQueue(this);
	}

	public final String getExtraData()
	{
		return this.extraData;
	}

	public final void setExtraData(final String extraData)
	{
		this.setExtraData(extraData, true);
	}

	public final JSONObject getServerData()
	{
		return this.serverData;
	}

	public final void saveServerData()
	{
		this.addItemToUpdateQueue(this);
	}

	public final int getWidth()
	{
		if (this.isGift())
			return 1;
		return this.getBaseItem().getBaseWidth();
	}

	public final int getLength()
	{
		if (this.isGift())
			return 1;
		return this.getBaseItem().getBaseLength();
	}

	public final boolean getCanStack()
	{
		return this.isGift() || this.getBaseItem().getBaseCanStack();
	}

	public final boolean getCanSit()
	{
		return !this.isGift() && this.getBaseItem().getBaseCanSit();
	}

	public final boolean getCanLay()
	{
		return !this.isGift() && this.getBaseItem().getBaseCanLay();
	}

	public final boolean getIsWallItem()
	{
		return !this.isGift() && (this.getBaseItem() != null && this.getBaseItem().getType().equalsIgnoreCase("I"));
	}

	public final boolean getIsWalkable()
	{
		if (this.isGift())
			return false;
		else if (this.isWalkable == 0)
			return this.getBaseItem().getIsWalkable();
		
		return (this.isWalkable == 1);
	}

	public final void setIsWalkable(final boolean isWalkable)
	{
		this.isWalkable = (isWalkable) ? 1 : 2;
	}

	public final double getStackHeight()
	{
		if (this.isGift())
			return 1;
		else if (this.getBaseItem().hasAdjustableHeights())
			return this.stackHeight;

		return (this.getBaseItem().getStackHeight() - this.stackHeight);
	}

	public final void setStackHeight(final double z)
	{
		this.stackHeight = z;
	}

	public final IGame getGame()
	{
		return this.game;
	}

	public final void setGame(final IGame _game)
	{
		this.game = _game;
	}

	public final void setExtraData(String extraData, boolean save)
	{
		if (this.getBaseItem().getInteractionType().equals(InteractionType.GuildFurni))
		{
			extraData = this.getExtraData().split(Character.toString((char) 1))[0] + Character.toString((char) 1) + extraData;
		}

		if (this.updateThread != null)
		{
			this.updateThread.interrupt();
			this.updateThread = null;
		}
		this.extraData = extraData;
		if (save)
		{
			this.addItemToUpdateQueue(this);
		}
	}

	public final void setOverrideExtraData(final String extraData)
	{
		this.extraData = extraData;
		this.addItemToUpdateQueue(this);
	}

	public final boolean isGift()
	{
		return this.serverData.has("present");
	}

	public final GiftWrappingParams getGift()
	{
		if (this.isGift())
		{
			return new GiftWrappingParams(this.serverData.getJSONObject("present"));
		}
		return null;
	}

	public final boolean changeExtraData()
	{
		if ((this.getBaseItem().getInteractionType().equals(InteractionType.Default) || this.getBaseItem().getInteractionType().equals(InteractionType.Gate) || this.getBaseItem().getInteractionType().equals(InteractionType.GuildFurni)) && (this.getBaseItem().getInteractionModesCount() > 0))
		{

			String data = this.getExtraData();

			if (data.isEmpty() || data.equals(" ") || !ObjectUtils.isNumeric(data))
			{
				this.setExtraData(data = "0");
			}

			int newData = (Integer.parseInt(data) + 1);

			if (newData >= this.getBaseItem().getInteractionModesCount())
			{
				newData = 0;
			}

			if (this.getBaseItem().hasAdjustableHeights())
			{
				this.setStackHeight(this.getBaseItem().getAdjustableHeight(newData));
				this.getRoom().getRoomInteractions().updateTile(this);
			}

			this.setExtraData(Integer.toString(newData));

			return true;
		}

		return false;
	}

	public final void updateStateInRoom()
	{
		getRoom().getRoomUnitsHandler().send(new RoomFloorItemUpdateDataComposer(this));
	}

	public final void updateAllDataInRoom()
	{
		if (this.getIsWallItem())
		{
			getRoom().getRoomUnitsHandler().send(new RoomWallItemUpdateComposer(this));
		}
		else
		{
			getRoom().getRoomUnitsHandler().send(new RoomFloorItemUpdateComposer(this));
		}
	}

	public final RoomUnit getUnitOnIt()
	{
		RoomUnit result = (this.getRoom() != null) ? this.getRoom().getRoomUnitsHandler().getUnitAt(this.getNode()) : null;

		if (result == null && this.getRoom() != null)
		{
			for (final AffectedTile tile : AffectedTile.getAffectedTilesAt(this))
			{
				final RoomUnit hasUnitOnIt = this.getRoom().getRoomUnitsHandler().getUnitAt(tile.getNode());

				if (result != null)
					continue;

				result = hasUnitOnIt;
			}
		}

		return result;
	}

	public final boolean hasUnitOnIt()
	{
		boolean result = (this.getRoom() != null && this.getRoom().getRoomUnitsHandler().getUnitAt(this.getNode()) != null);

		if (!result && this.getRoom() != null)
		{
			for (final AffectedTile tile : AffectedTile.getAffectedTilesAt(this))
			{
				final boolean hasUnitOnIt = (this.getRoom().getRoomUnitsHandler().getUnitAt(tile.getNode()) != null);

				if (result)
					continue;

				result = hasUnitOnIt;
			}
		}

		return result;
	}

	public abstract void onWiredCollision(final RoomUnit unit);

	public abstract void onWiredInteractsWithItem(final WiredUserItem wiredItem);

	public abstract void onUnitInteractsWithItem(final RoomUnit unit, final int param);

	public abstract boolean onUnitSaysSomething(final RoomUnit unit, final String text, final ChatBubble chatBubble, final boolean isShouted);

	public abstract void onUnitWalksOnItem(final RoomUnit unit);

	public abstract void onUnitWalksOffItem(final RoomUnit unit);

	public abstract void serializeData(final ServerMessage response, final boolean isInventory, final boolean isDataOnly);

	public final void serializeLocalData(final ServerMessage response, final boolean isInventory, final boolean isDataOnly)
	{
		if (this.isGift() && this.getGift() != null)
		{
			if (!isDataOnly)
				response.appendInt((this.getGift().getBoxId() * 1000) + this.getGift().getRibbonId());

			response.appendInt(1);
			response.appendInt(this.getGift().getHabboFromData() ? 6 : 4);
			response.appendString("EXTRA_PARAM");
			response.appendString("");
			response.appendString("MESSAGE");
			response.appendString(this.getGift().getGiftWish());
			if (this.getGift().getHabboFromData())
			{
				response.appendString("PURCHASER_NAME");
				response.appendString(this.getGift().getHabboFrom().getUsername());
				response.appendString("PURCHASER_FIGURE");
				response.appendString(this.getGift().getHabboFrom().getLook());
			}
			response.appendString("PRODUCT_CODE");
			response.appendString(this.getBaseItem().getSpriteId());
			response.appendString("state");
			response.appendString(this.getGift().isOpened() ? "1" : "0");
		}
		else
		{
			this.serializeData(response, isInventory, isDataOnly);
		}
	}

	public final UserItemRentData getRentData()
	{
		return new UserItemRentData(this);
	}

	@Override
	public final void serialize(final ServerMessage response)
	{
		response.appendInt(this.id);
		response.appendString(this.isGift() ? "S" : this.getBaseItem().getType().toUpperCase());
		response.appendInt(this.getCatalogItem() != null ? this.getCatalogItem().getId() : 0);
		response.appendInt(this.isGift() && this.getGift() != null ? this.getGift().getPresentSpriteId() : this.getBaseItem().getSpriteId());

		if (!this.isGift() && this.getIsWallItem())
		{
			if (this.getCatalogItem().getName().contains("landscape") || this.getCatalogItem().getName().contains("floor") || this.getCatalogItem().getName().contains("wallpaper"))
			{
				String paperId = this.getCatalogItem().getName();
				paperId = paperId.replace("landscape_single_", "");
				paperId = paperId.replace("wallpaper_single_", "");
				paperId = paperId.replace("floor_single_", "");

				if (this.getCatalogItem().getName().contains("floor"))
				{
					response.appendInt(3);
				}
				else if (this.getCatalogItem().getName().contains("wallpaper"))
				{
					response.appendInt(2);
				}
				else if (this.getCatalogItem().getName().contains("landscape"))
				{
					response.appendInt(4);
				}
				response.appendInt(0);
				response.appendString(paperId);
			}
			else
			{
				response.appendInt(1);
				response.appendInt(0);
				response.appendString(this.extraData);
			}
		}
		else
		{
			this.serializeLocalData(response, true, false);
		}

		response.appendBoolean(!this.isGift() && this.getBaseItem().getAllowRecycle());
		response.appendBoolean(!this.isGift() && this.getBaseItem().getAllowTrade());
		response.appendBoolean(!this.isGift() && this.getBaseItem().getAllowInventoryStack());
		response.appendBoolean(false);

		response.appendInt(this.getRentData().getExipirationSeconds());
		response.appendBoolean(this.getRentData().isRentPeriodStarted());

		response.appendInt(this.getRoom() != null ? this.getRoom().getRoomData().getId() : -1);

		if (!this.getIsWallItem())
		{
			response.appendString("");
			if (this.getCatalogItem() != null && this.getCatalogItem().getSongId() > 0)
			{
				response.appendInt(this.getCatalogItem().getSongId());
			}
			else
			{
				response.appendInt(0);
			}
		}
	}

	public final void serializeFloor(final ServerMessage response)
	{
		response.appendInt(this.getId());
		response.appendInt(this.isGift() && this.getGift() != null ? this.getGift().getPresentSpriteId() : this.getBaseItem().getSpriteId());
		response.appendInt(this.getX());
		response.appendInt(this.getY());
		response.appendInt(this.getRot());
		response.appendString(Double.toString(this.getZ()));
		if (!this.isGift() && this.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.Trophy))
		{
			response.appendString("1.0");
		}
		else if (!this.isGift() && this.getBaseItem().hasAdjustableHeights())
		{
			response.appendString(Double.toString(this.getStackHeight()));
		}
		else
		{
			response.appendString("");
		}

		this.serializeLocalData(response, false, false);

		if (this.getRentData().isRentable())
		{
			response.appendInt(this.getRentData().getExipirationSeconds());
		}
		else
		{
			response.appendInt(-1);
		}
		response.appendInt(this.getBaseItem().getInteractionModesCount() > 1 ? 1 : 0);
		response.appendInt(this.getHabbo().getId());
	}

	public final void serializeWall(final ServerMessage response)
	{
		response.appendString(this.getId());
		response.appendInt(this.getBaseItem().getSpriteId());
		response.appendString(this.getWallPos());

		if (this.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.MoodLight))
		{
			response.appendString(this.getExtraData());
			response.appendInt(-1);
			response.appendInt(1);
		}
		else if (this.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.PostIt))
		{
			String color = this.getExtraData().split(" ")[0];
			if (color.isEmpty())
			{
				color = "FFFF33";
			}
			response.appendString(color);
			response.appendInt(-1);
			response.appendInt(2);
		}
		else
		{
			response.appendString(this.getExtraData());
			response.appendInt(-1);
			response.appendInt(-1);
		}
		response.appendInt(this.getHabbo().getId());
	}

	public final Node getNode()
	{
		return new Node(x, y);
	}

	public final Node getNodeInFront()
	{
		Node coord = new Node(x, y);

		if (rot == 0)
		{
			coord.setCoordinates(coord.getX(), (coord.getY() - 1));
		}
		else if (rot == 2)
		{
			coord.setCoordinates((coord.getX() + 1), coord.getY());
		}
		else if (rot == 4)
		{
			coord.setCoordinates(coord.getX(), (coord.getY() + 1));
		}
		else if (rot == 6)
		{
			coord.setCoordinates((coord.getX() - 1), coord.getY());
		}

		return coord;
	}

	public final Node getNearNode(final Node node)
	{
		return new Node(this.getNode().getX() + node.getX(), this.getNode().getY() + node.getY());
	}

	public final void delete()
	{
		final Room room = this.getRoom();
		this.setRoom(null);
		room.getRoomUnitsHandler().send(new RemoveFloorItemComposer(this, null));
		room.getRoomObjectsHandler().updateGameMap();

		Bootstrap.getEngine().getGame().getItemsManager().delete(this);

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("DELETE FROM items WHERE id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, this.id);
					statement.execute();
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public final YouTubePlaylist getYouTubePlaylist()
	{
		return this.youTubePlaylist;
	}

	public final YouTubeVideo getYouTubeVideo()
	{
		return this.youTubeVideo;
	}

	public final void rollTo(final Node node, final double z, final UserItem rollerItem)
	{
		this.getRoom().getRoomUnitsHandler().send(new RollerMoveFurniComposer(this, node, rollerItem, z));
		this.setX(node.getX());
		this.setY(node.getY());
		this.setZ(z);
	}

	public final void getNearItemsWithSameType(final List<UserItem> items, final boolean merged, final String[] state, final boolean addMultiple, final boolean deepSearch)
	{
		this.getItemsWithSameTypeAt(items, x, y, merged, addMultiple, state, deepSearch);
		this.getItemsWithSameTypeAt(items, x - 1, y - 1, merged, addMultiple, state, deepSearch);
		this.getItemsWithSameTypeAt(items, x + 1, y + 1, merged, addMultiple, state, deepSearch);
		this.getItemsWithSameTypeAt(items, x - 1, y + 1, merged, addMultiple, state, deepSearch);
		this.getItemsWithSameTypeAt(items, x + 1, y - 1, merged, addMultiple, state, deepSearch);
		this.getItemsWithSameTypeAt(items, x + 1, y, merged, addMultiple, state, deepSearch);
		this.getItemsWithSameTypeAt(items, x, y + 1, merged, addMultiple, state, deepSearch);
		this.getItemsWithSameTypeAt(items, x - 1, y, merged, addMultiple, state, deepSearch);
		this.getItemsWithSameTypeAt(items, x, y - 1, merged, addMultiple, state, deepSearch);
	}

	public final boolean getItemsWithSameTypeAt(final List<UserItem> items, final int x, final int y, final boolean merged, final boolean addMultiple, final String[] state, final boolean deepSearch)
	{
		return this.getItemsWithSameTypeAndStateAt(items, x, y, state, merged, addMultiple, deepSearch);
	}

	public final boolean getItemsWithSameTypeAndStateAt(final List<UserItem> items, final int x, final int y, final String[] state, final boolean merged, final boolean addMultiple, final boolean deepSearch)
	{
		final List<UserItem> itemsAtPoint = this.getRoom().getRoomObjectsHandler().getFloorItemsAt(new Node(x, y), 0.00, state);

		int total = itemsAtPoint.size();
		int valid = 0;

		if (this.getRoom().getRoomData().getRoomModel().isValidNode(new Node(x, y)))
		{
			for (UserItem item : itemsAtPoint)
			{
				if (item.getBaseItem().getInteractionType(merged).equalsIgnoreCase(this.getBaseItem().getInteractionType(merged)))
				{
					if (items.contains(item) && !addMultiple)
						continue;

					items.add(item);
					valid++;

					if (deepSearch)
					{
						item.getNearItemsWithSameType(items, merged, state, addMultiple, true);
					}
				}
			}
		}

		return ((total == valid) && total != 0);
	}

	public final Map<Integer, List<UserItem>> getNearItemsWithDistanceAndSameType(final int distance, final String[] states, final boolean horizontal, final boolean vertical)
	{
		final Map<Integer, List<UserItem>> items = new HashMap<>();
		final List<UserItem> firstTile = new ArrayList<>();

		firstTile.add(this);

		items.put(0, firstTile);

		boolean a = true, b = true, c = true, d = true, e = true, f = true, g = true, h = true;

		for (int i = 1; i <= distance; i++)
		{
			final List<UserItem> itemsAtDistance = new ArrayList<>();

			if (horizontal)
			{
				if (a)
					a = this.getItemsWithSameTypeAndStateAt(itemsAtDistance, this.getX() - i, this.getY(), states, false, false, false);
				if (b)
					b = this.getItemsWithSameTypeAndStateAt(itemsAtDistance, this.getX(), this.getY() - i, states, false, false, false);
				if (c)
					c = this.getItemsWithSameTypeAndStateAt(itemsAtDistance, this.getX() + i, this.getY(), states, false, false, false);
				if (d)
					d = this.getItemsWithSameTypeAndStateAt(itemsAtDistance, this.getX(), this.getY() + i, states, false, false, false);
			}

			if (vertical)
			{
				if (e)
					e = this.getItemsWithSameTypeAndStateAt(itemsAtDistance, this.getX() - i, this.getY() - i, states, false, false, false);
				if (f)
					f = this.getItemsWithSameTypeAndStateAt(itemsAtDistance, this.getX() + i, this.getY() + i, states, false, false, false);
				if (g)
					g = this.getItemsWithSameTypeAndStateAt(itemsAtDistance, this.getX() + i, this.getY() - i, states, false, false, false);
				if (h)
					h = this.getItemsWithSameTypeAndStateAt(itemsAtDistance, this.getX() - i, this.getY() + i, states, false, false, false);
			}

			items.put(i, itemsAtDistance);
		}

		return items;
	}

	public final void resolveYouTubeData(final boolean sendUpdate, final Habbo habbo)
	{
		if (!this.getBaseItem().getInteractionType().equals(InteractionType.YouTubeTV))
			return;

		int playlistId = 0;
		final UserItem item = this;

		if (ObjectUtils.isNumeric(this.getExtraData()))
		{
			playlistId = Integer.parseInt(this.getExtraData());
		}

		if (this.youTubePlaylist != null)
		{
			this.youTubePlaylist.stop(this);
		}

		this.getHabbo().loadSettings();

		this.youTubePlaylist = null;
		this.youTubeVideo = null;

		if (playlistId > 0)
		{
			this.youTubePlaylist = this.getHabbo().getSettings().getYouTubeManager().getPlaylist(playlistId);

			if (this.youTubePlaylist != null)
			{
				this.youTubeVideo = this.youTubePlaylist.getCurrentVideo();
				this.youTubePlaylist.start(this, video -> {
					youTubeVideo = video;
					if (getRoom() == null)
						return;

					getRoom().getRoomUnitsHandler().send(new YoutubeDisplayPlayVideoComposer(item, getYouTubePlaylist().getCurrentVideo()));
					updateAllDataInRoom();
				});

				if (sendUpdate && habbo != null && this.getYouTubePlaylist() != null && this.getYouTubePlaylist().getCurrentVideo() != null)
				{
					habbo.getConnection().send(new YoutubeDisplayPlayVideoComposer(this, this.getYouTubePlaylist().getCurrentVideo()));
				}
			}
		}
		else
		{
			if (sendUpdate && habbo != null)
			{
				habbo.getConnection().send(new YoutubeDisplayPlayVideoComposer(this, null));
			}
		}
	}

	@Override
	public final boolean equals(final Object obj)
	{
		return ((obj instanceof UserItem) && ((UserItem) obj).getId() == this.getId());
	}

}