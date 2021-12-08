package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.bots.Bot;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.chat.RoomChat;
import com.basmilius.time.habbohotel.rooms.cycles.ICycle;
import com.basmilius.time.habbohotel.rooms.cycles.RollerCycle;
import com.basmilius.time.habbohotel.rooms.cycles.SoundMachineCycle;
import com.basmilius.time.habbohotel.rooms.cycles.WalkCycle;
import com.basmilius.time.habbohotel.rooms.games.IGame;
import com.basmilius.time.habbohotel.rooms.games.freeze.FreezeGame;
import com.basmilius.time.habbohotel.rooms.pathfinding.Factory;
import com.basmilius.time.habbohotel.rooms.pathfinding.Map;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.habbohotel.rooms.trading.TradingEngine;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericAlertComposer;
import com.google.common.collect.Lists;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public final class Room
{
	
	private boolean active;
	
	private List<ICycle> cycles;
	private List<IGame> games;
	private Map<Node> gameMap;

	private final RoomChat roomChat;
	private final RoomData roomData;
	private final RoomInteractions roomInteractions;
	private final RoomObjectsHandler roomObjectsHandler;
	private final RoomUnitsHandler roomUnitsHandler;
	private final TradingEngine tradingEngine;
	
	private final SecureRandom randomizer;
	
	private boolean deleted;
	
	public Room(final ResultSet result) throws RoomException
	{
		try
		{
			this.roomChat = new RoomChat(this);
			this.roomData = new RoomData(this, result);
			this.roomInteractions = new RoomInteractions(this);
			this.roomObjectsHandler = new RoomObjectsHandler(this);
			this.roomUnitsHandler = new RoomUnitsHandler(this);
			this.tradingEngine = new TradingEngine(this);

			this.randomizer = new SecureRandom();
			
			this.deleted = false;
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			throw new RoomException(this, "Failed loading room because of a SQLException.");
		}
		catch (NoSuchAlgorithmException e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
			throw new RoomException(this, "Failed loading room because of a NoSuchAlgoritmException");
		}
	}

	public final void load() throws RoomException
	{
		if (this.active)
			return;
		
		try
		{
			this.active = true;
			
			this.cycles = Lists.newLinkedList();
			this.games = Lists.newLinkedList();
			this.gameMap = new Map<>(this.getRoomData().getRoomModel().getMapSizeX(), this.getRoomData().getRoomModel().getMapSizeY(), new Factory());
			
			this.getRoomObjectsHandler().loadFurniture();

			this.cycles.add(new RollerCycle(this));
			this.cycles.add(new SoundMachineCycle(this));
			this.cycles.add(new WalkCycle(this));
			this.cycles.stream().forEach(ICycle::start);

			this.games.add(new FreezeGame(this));

			final List<Bot> bots = Bootstrap.getEngine().getGame().getBotManager().getBotsForRoom(this);
			for (final Bot bot : bots)
			{
				this.getRoomUnitsHandler().addRoomUnit(new RoomBot(this, bot), true);
			}

			//final List<Pet> pets = Bootstrap.getEngine().getGame().getPetManager().loadPetsForRoom(this);
			//for (final Pet pet : pets)
			//{
			//	this.getRoomUnitsHandler().addRoomUnit(new RoomPet(this, pet), true);
			//}
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
			throw new RoomException(this, "Failed loading room, Exception occurred.");
		}
	}
	
	public final boolean isActive()
	{
		return this.active;
	}
	
	@SuppressWarnings("unchecked")
	public final <TCycle extends ICycle> TCycle getCycle(Class<TCycle> cycleClass)
	{
		return (TCycle) this.cycles.stream().filter(cycleClass::isInstance).collect(Collectors.toList()).get(0);
	}
	
	@SuppressWarnings("unchecked")
	public final <TGame extends IGame> TGame getGame(Class<TGame> gameClass)
	{
		return (TGame) this.games.stream().filter(gameClass::isInstance).collect(Collectors.toList()).get(0);
	}
	
	public final Map<Node> getGameMap()
	{
		return this.gameMap;
	}
	
	public final Map<Node> getGameMap(final Node node)
	{
		final Map<Node> map = new Map<>(this.getRoomData().getRoomModel().getMapSizeX(), this.getRoomData().getRoomModel().getMapSizeY(), new Factory());
		
		for (final Node n : this.gameMap.getNodes())
		{
			map.setWalkable(n.getX(), n.getY(), n.isWalkable());
		}
		
		if (!this.getRoomData().getPermissions().isWalkthroughAllowed())
		{
			for (final RoomUnit unit : this.getRoomUnitsHandler().getUnits())
			{
				map.setWalkable(unit.getX(), unit.getY(), false);
			}
		}
		
		final List<UserItem> itemsAtNode = this.getRoomObjectsHandler().getFloorItemsAt(node);
		itemsAtNode.stream().filter(item -> item.getCanSit() || item.getCanLay()).forEach(item -> map.setWalkable(node.getX(), node.getY(), true));
		
		final RoomUnit unitAtNode = this.getRoomUnitsHandler().getUnitAt(node);
		if (unitAtNode != null && !this.getRoomData().getRoomModel().getIsDoor(node.getX(), node.getY()))
		{
			map.setWalkable(node.getX(), node.getY(), false);
		}

		return map;
	}
	
	public final RoomChat getRoomChat()
	{
		return this.roomChat;
	}
	
	public final RoomData getRoomData()
	{
		return this.roomData;
	}
	
	public final RoomInteractions getRoomInteractions()
	{
		return this.roomInteractions;
	}

	public final RoomObjectsHandler getRoomObjectsHandler()
	{
		return this.roomObjectsHandler;
	}

	public final RoomUnitsHandler getRoomUnitsHandler()
	{
		return this.roomUnitsHandler;
	}
	
	public final TradingEngine getTradingEngine()
	{
		return this.tradingEngine;
	}
	
	public final SecureRandom getRandomizer()
	{
		return this.randomizer;
	}
	
	public final boolean isDeleted()
	{
		return this.deleted;
	}
	
	public final void delete()
	{
		this.deleted = true;
	}
	
	public final void crash()
	{
		this.getRoomUnitsHandler().send(new GenericAlertComposer(Bootstrap.getEngine().getConfig().getString("error.manager.rooms.room_crashed", "The room you where in just crashed! To avoid some bugs and mistakes the room has been cleared. You can enter it again.")));
		this.getRoomUnitsHandler().getUsers().forEach(user -> user.kick(true));
		this.dispose();
	}
	
	public final void dispose()
	{
		this.active = false;

		this.getRoomUnitsHandler().getUsers().forEach(user -> user.kick(true));
		
		this.cycles.forEach(ICycle::stop);
		this.getRoomUnitsHandler().getBots().forEach(RoomBot::dispose);
		this.getRoomUnitsHandler().getPets().forEach(RoomPet::dispose);
		this.getRoomUnitsHandler().getUsers().clear();
		
		this.cycles.clear();
		this.games.clear();
		this.getRoomObjectsHandler().getFloorItems().clear();
		this.getRoomObjectsHandler().getWallItems().clear();
	}

	public final void unload()
	{
		this.unload(true);
	}
	
	public final void unload (final boolean notify)
	{
		if (notify)
		{
			this.getRoomUnitsHandler().send(new GenericAlertComposer(Bootstrap.getEngine().getConfig().getString("error.manager.rooms.room_unloaded", "The room you where in has been unloaded, you can now enter it again.")));
		}
		
		this.getRoomUnitsHandler().getUsers().forEach(user -> user.kick(true));
		this.dispose();
	}
	
}
