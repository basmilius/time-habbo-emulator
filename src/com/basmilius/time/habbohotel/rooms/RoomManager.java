package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.collections.NavigatorOwnRoomsComparator;
import com.basmilius.time.habbohotel.collections.NavigatorUsersGuildMembersComparator;
import com.basmilius.time.habbohotel.collections.NavigatorUsersScoreComparator;
import com.basmilius.time.habbohotel.enums.DoorMode;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.messenger.Friend;
import com.basmilius.time.habbohotel.rooms.floorplan.*;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.outgoing.general.SendHotelViewComposer;
import com.basmilius.time.communication.messages.outgoing.navigator.DoorbellKnockComposer;
import com.basmilius.time.communication.messages.outgoing.navigator.DoorbellRemoveUserComposer;
import com.basmilius.time.communication.messages.outgoing.navigator.RoomNameUnacceptableComposer;
import com.basmilius.time.communication.messages.outgoing.handshake.GenericErrorMessageComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.*;
import com.basmilius.time.communication.messages.outgoing.rooms.users.SpectatorModeComposer;
import com.google.common.collect.Lists;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class RoomManager extends IManager
{

	private final List<RoomModel> models;
	private final List<Room> rooms;
	private final List<RoomCategory> categories;
	private final Map<String, Integer> doorbell;
	
	private RoomsSaver roomsSaver;

	public RoomManager()
	{
		this.models = Lists.newLinkedList();
		this.rooms = Lists.newLinkedList();
		this.categories = Lists.newLinkedList();
		this.doorbell = new HashMap<>();
	}

	public final Map<String, Integer> getDoorbell()
	{
		return this.doorbell;
	}
	
	public final RoomsSaver getRoomsSaver()
	{
		return this.roomsSaver;
	}

	private void loadCategories()
	{
		try
		{
			Bootstrap.getEngine().getLogging().logNoNewLine(RoomManager.class, "Loading room categories .. ");

			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM navigator_flatcats");
			{
				if (statement != null)
				{
					final ResultSet result = statement.executeQuery();
					while (result.next())
					{
						this.categories.add(new RoomCategory(result));
					}

					Bootstrap.getEngine().getLogging().logOK();
				}
				else
				{
					throw new SQLException("Statement is NULL");
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			Bootstrap.getEngine().onLaunchFail();
		}
	}

	public void loadModels()
	{
		this.models.clear();

		Bootstrap.getEngine().getLogging().logNoNewLine(RoomManager.class, "Loading room models .. ");

		Map<String, IFloorPlan> floorPlans = new HashMap<>();
		floorPlans.put("model_0", new Model0FloorPlan());
		floorPlans.put("model_1", new Model1FloorPlan());
		floorPlans.put("model_2", new Model2FloorPlan());
		floorPlans.put("model_3", new Model3FloorPlan());
		floorPlans.put("model_4", new Model4FloorPlan());
		floorPlans.put("model_5", new Model5FloorPlan());
		floorPlans.put("model_6", new Model6FloorPlan());
		floorPlans.put("model_7", new Model7FloorPlan());
		floorPlans.put("model_8", new Model8FloorPlan());
		floorPlans.put("model_9", new Model9FloorPlan());
		floorPlans.put("model_a", new ModelAFloorPlan());
		floorPlans.put("model_b", new ModelBFloorPlan());
		floorPlans.put("model_c", new ModelCFloorPlan());
		floorPlans.put("model_d", new ModelDFloorPlan());
		floorPlans.put("model_e", new ModelEFloorPlan());
		floorPlans.put("model_f", new ModelFFloorPlan());
		floorPlans.put("model_g", new ModelGFloorPlan());
		floorPlans.put("model_h", new ModelHFloorPlan());
		floorPlans.put("model_i", new ModelIFloorPlan());
		floorPlans.put("model_j", new ModelJFloorPlan());
		floorPlans.put("model_k", new ModelKFloorPlan());
		floorPlans.put("model_l", new ModelLFloorPlan());
		floorPlans.put("model_m", new ModelMFloorPlan());
		floorPlans.put("model_n", new ModelNFloorPlan());
		floorPlans.put("model_o", new ModelOFloorPlan());
		floorPlans.put("model_p", new ModelPFloorPlan());
		floorPlans.put("model_q", new ModelQFloorPlan());
		floorPlans.put("model_r", new ModelRFloorPlan());
		floorPlans.put("model_s", new ModelSFloorPlan());
		floorPlans.put("model_t", new ModelTFloorPlan());
		floorPlans.put("model_u", new ModelUFloorPlan());
		floorPlans.put("model_v", new ModelVFloorPlan());
		floorPlans.put("model_w", new ModelWFloorPlan());
		floorPlans.put("model_x", new ModelXFloorPlan());
		floorPlans.put("model_y", new ModelYFloorPlan());
		floorPlans.put("model_z", new ModelZFloorPlan());

		this.models.addAll(floorPlans.entrySet().stream().map(entry -> new RoomModel(entry.getKey(), entry.getValue())).collect(Collectors.toList()));

		Bootstrap.getEngine().getLogging().logOK();
	}

	private void loadRoomsWithHighestScore()
	{
		try
		{
			Bootstrap.getEngine().getLogging().logNoNewLine(RoomManager.class, "Loading rooms with highest score .. ");

			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM rooms ORDER BY score DESC LIMIT 50");
			{
				if (statement != null)
				{
					final ResultSet result = statement.executeQuery();

					while (result.next())
					{
						if (!this.containsRoom(result.getInt("id")))
						{
							this.rooms.add(new Room(result));
						}
					}
				}
				else
				{
					throw new SQLException("Statement is NULL");
				}

				Bootstrap.getEngine().getLogging().logOK();
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			Bootstrap.getEngine().onLaunchFail();
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleException(e);
			Bootstrap.getEngine().onLaunchFail();
		}
	}

	public void loadRoomForHabbo(final Habbo habbo, final int roomId, final String password)
	{
		this.loadRoomForHabbo(habbo, roomId, password, false);
	}

	public void loadRoomForHabbo(final Habbo habbo, final int roomId, final String password, final boolean skipDoorCheck)
	{
		final Room room = this.getRoom(roomId);
		boolean spectator = false;

		if (room == null)
		{
			habbo.leaveRoom(true);
			return;
		}

		habbo.leaveRoom();

		try
		{
			room.load();

			final QueuedComposers composers = new QueuedComposers();

			if (room.getRoomUnitsHandler().isFull() || (habbo.getHabboCache() != null && habbo.getHabboCache().getBoolean("pt.management:roommanager.next.spectator", false)))
			{
				habbo.getHabboCache().set("pt.management:roommanager.next.spectator", false);
				spectator = true;
			}

			if (!skipDoorCheck && habbo.getId() != room.getRoomData().getOwner().getId())
			{
				if (room.getRoomData().getDoorMode() == DoorMode.PASSWORD && !room.getRoomData().getRoomPassword().isEmpty())
				{
					if (!room.getRoomData().getRoomPassword().equalsIgnoreCase(password))
					{
						composers.appendComposer(new RoomNameUnacceptableComposer(RoomNameUnacceptableComposer.INVALID_PASSWORD));
						composers.appendComposer(new SendHotelViewComposer());
						return;
					}
				}
				else if (room.getRoomData().getDoorMode() == DoorMode.DOORBELL && room.getRoomUnitsHandler().getUsers().size() == 0)
				{
					habbo.leaveRoom(true);
					composers.appendComposer(new DoorbellRemoveUserComposer(""));
					return;
				}
				else if (room.getRoomData().getDoorMode() == DoorMode.DOORBELL)
				{
					this.getDoorbell().put(habbo.getUsername(), roomId);
					habbo.leaveRoom(true);
					composers.appendComposer(new DoorbellKnockComposer(""));
					room.getRoomUnitsHandler().send(new DoorbellKnockComposer(habbo.getUsername()), true);
					return;
				}
			}

			if (this.getDoorbell().containsKey(habbo.getUsername()))
			{
				this.getDoorbell().remove(habbo.getUsername());
			}

			if (room.getRoomData().getOwner().getId() != habbo.getId() || room.getRoomData().getPermissions().isPublicRoom())
			{
				habbo.getAchievements().unlockAchievement("ACH_RoomEntry", 1);
			}

			habbo.setCurrentRoom(room);

			final RoomUser user = new RoomUser(room, habbo.getConnection());
			user.setIsSpectator(spectator);
			room.getRoomUnitsHandler().addRoomUnit(user, false);

			composers.appendComposer(new StartRoomComposer());

			if (user.isSpectator())
			{
				composers.appendComposer(new SpectatorModeComposer());
			}

			composers.appendComposer(new SendModelComposer(room.getRoomData().getRoomModel().getId(), room.getRoomData().getId()));

			if (!room.getRoomData().getRoomDecoration().getWallpaper().equals("0.0"))
			{
				composers.appendComposer(new SendPaperComposer("wallpaper", room.getRoomData().getRoomDecoration().getWallpaper()));
			}

			if (!room.getRoomData().getRoomDecoration().getFloorpaper().equals("0.0"))
			{
				composers.appendComposer(new SendPaperComposer("floor", room.getRoomData().getRoomDecoration().getFloorpaper()));
			}

			composers.appendComposer(new SendPaperComposer("landscape", Double.toString(room.getRoomData().getRoomDecoration().getLandscape())));

			if (room.getRoomData().getPermissions().isOwner(habbo))
			{
				composers.appendComposer(new RoomRightsComposer(4));
				composers.appendComposer(new RoomOwnerComposer());
			}
			else if (room.getRoomData().getPermissions().hasRights(habbo))
			{
				composers.appendComposer(new RoomRightsComposer(1));
			}
			else
			{
				composers.appendComposer(new RoomRightsComposer(0));
			}

			// TODO: Check if the user can vote. Change boolean to false if the user can't.
			composers.appendComposer(new RoomScoreComposer(room.getRoomData().getScore(), true));

			habbo.getConnection().send(composers);
		}
		catch (RoomException e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
			habbo.getConnection().send(new GenericErrorMessageComposer(e));
		}
	}

	public final List<Room> filterRooms(final List<Room> oldList)
	{
		final List<Room> newList = Lists.newLinkedList();

		for (final Room room : oldList)
		{
			if (room.isDeleted())
				continue;

			newList.add(room);
		}

		return newList;
	}

	public RoomCategory getCategory(int id)
	{
		for (RoomCategory category : this.categories)
			if (category.getId() == id)
				return category;

		return null;
	}

	public final Room getRoom(final int roomId)
	{
		Room room = null;

		for (final Room r : this.rooms)
		{
			if (r.getRoomData().getId() == roomId)
			{
				room = r;
				break;
			}
		}

		if (room == null && this.loadSingleRoom(roomId))
			room = this.getRoom(roomId);

		if (room != null && room.isDeleted())
			return null;

		return room;
	}

	public RoomModel getRoomModel(String id)
	{
		for (RoomModel model : this.models)
			if (model.getId().equalsIgnoreCase(id))
				return model;
		return null;
	}

	public List<Room> getRoomsWithOwner(Habbo habbo)
	{
		List<Room> rooms = Lists.newLinkedList();

		if (habbo == null)
		{
			return rooms;
		}

		for (Room room : this.rooms)
		{
			if (room.getRoomData().getOwner() == null)
				continue;

			if (room.getRoomData().getOwner().getId() == habbo.getId())
			{
				rooms.add(room);
			}
			else if (room.getRoomData().getGuild() != null && Bootstrap.getEngine().getConfig().getBoolean("hotel.navigator.guilds.in.me", false) && room.getRoomData().getGuild().getHasMember(habbo))
			{
				rooms.add(room);
			}
		}

		Collections.sort(rooms, new NavigatorOwnRoomsComparator());

		rooms = this.filterRooms(rooms);

		return rooms;
	}

	public List<Room> getRoomsWithOwnerFriendWith(Habbo habbo)
	{
		List<Room> rooms = Lists.newLinkedList();

		if (habbo == null)
		{
			return rooms; // Huh? no habbo?!
		}

		if (habbo.isOnline())
		{
			for (Friend friend : habbo.getMessenger().getFriends())
			{
				this.preloadRoomsForUser(friend.getHabbo());

				for (Room room : this.getRoomsWithOwner(friend.getHabbo()))
				{
					if (!rooms.contains(room))
					{
						rooms.add(room);
					}
				}
			}
		}

		Collections.sort(rooms, new NavigatorUsersScoreComparator());

		rooms = this.filterRooms(rooms);

		return rooms.subList(0, (rooms.size() > 100) ? 100 : rooms.size());
	}

	public List<Room> getRoomsWhereFriendsAre(Habbo habbo)
	{
		List<Room> rooms = Lists.newLinkedList();

		if (habbo == null)
			return rooms;

		if (habbo.isOnline())
		{
			for (Friend friend : habbo.getMessenger().getFriends())
			{
				if (!friend.getHabbo().isOnline())
					continue;

				if (!rooms.contains(friend.getHabbo().getCurrentRoom()))
				{
					rooms.add(friend.getHabbo().getCurrentRoom());
				}
			}
		}

		Collections.sort(rooms, Collections.reverseOrder());

		rooms = this.filterRooms(rooms);

		return rooms.subList(0, (rooms.size() > 100) ? 100 : rooms.size());
	}

	public List<Room> getPopularRooms()
	{
		List<Room> rooms = this.rooms.stream().collect(Collectors.toList());

		Collections.sort(rooms, new NavigatorUsersScoreComparator());

		rooms = this.filterRooms(rooms);

		return rooms.subList(0, (rooms.size() > 100) ? 100 : rooms.size());
	}

	public List<Room> getPopularRoomsWithCategory(int categoryId)
	{
		List<Room> rooms = this.filterRooms(this.rooms.stream().filter(r -> r.getRoomData().getCategory() == categoryId).collect(Collectors.toList()));
		
		Collections.sort(rooms, new NavigatorUsersScoreComparator());
		
		return rooms.subList(0, (rooms.size() > 100) ? 100 : rooms.size());
	}

	public List<Guild> getPopularGuilds()
	{
		List<Guild> guilds = Lists.newLinkedList();

		for (final Room room : this.rooms)
		{
			if (room.getRoomData().getGuild() == null)
				continue;

			guilds.add(room.getRoomData().getGuild());
		}

		return guilds.subList(0, (guilds.size() > 100) ? 100 : guilds.size());
	}

	public List<Room> getPopularRoomsWithGuild()
	{
		List<Room> rooms = Lists.newLinkedList();

		for (Room room : this.rooms)
		{
			if (room.getRoomData().getGuild() == null)
				continue;

			rooms.add(room);
		}

		Collections.sort(rooms, new NavigatorUsersGuildMembersComparator());

		rooms = this.filterRooms(rooms);

		return rooms.subList(0, (rooms.size() > 100) ? 100 : rooms.size());
	}

	public List<Room> getRoomsWithHighestScores()
	{
		List<Room> rooms = Lists.newLinkedList();

		for (Room Room : this.rooms)
		{
			rooms.add(Room);
		}

		Collections.sort(rooms, new NavigatorUsersScoreComparator());

		rooms = this.filterRooms(rooms);

		return rooms.subList(0, (rooms.size() > 100) ? 100 : rooms.size());
	}

	public List<Room> getRoomsWithQuery(String query)
	{
		if (query.startsWith(Bootstrap.getEngine().getConfig().getString("hotel.navigator.owner", "owner") + ":"))
		{
			String ownerName = query.replace(Bootstrap.getEngine().getConfig().getString("hotel.navigator.owner", "owner") + ":", "");

			Habbo ownerHabbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(ownerName);

			if (ownerHabbo == null)
				return Lists.newLinkedList();

			this.preloadRoomsForUser(ownerHabbo);

			return this.getRoomsWithOwner(ownerHabbo);
		}
		return this.loadRoomsForSearch(query);
	}

	public List<RoomCategory> getCategories()
	{
		return this.categories;
	}

	public boolean preloadRoomsForUser(Habbo habbo)
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM rooms WHERE owner_id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, habbo.getId());
					return this.loadRooms(statement);
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
		
		return false;
	}

	public final List<Room> loadRoomsForSearch(String query)
	{
		List<Room> rooms = Lists.newLinkedList();

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM rooms WHERE room_name LIKE ?");
			{
				if (statement != null)
				{
					statement.setString(1, query + "%");
					final ResultSet result = statement.executeQuery();

					while (result.next())
					{
						Room room = this.getRoom(result.getInt("id"));
						if (room != null)
						{
							rooms.add(room);
						}
					}
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}

		Collections.sort(rooms, new NavigatorUsersScoreComparator());

		rooms = this.filterRooms(rooms);

		return rooms;
	}

	public boolean loadSingleRoom(Integer roomId)
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM rooms WHERE id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, roomId);
					return this.loadRooms(statement);
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}
		
		return false;
	}

	boolean loadRooms(PreparedStatement statement) throws Exception
	{
		final ResultSet result = statement.executeQuery();

		int i = 0;

		while (result.next())
		{
			i++;
			if (!this.containsRoom(result.getInt("id")))
			{
				this.rooms.add(new Room(result));
			}
		}

		return (i > 0); // if there where any rooms loaded, return true.
	}

	public boolean containsRoom(int roomId)
	{
		for (Room room : this.rooms)
			if (room.getRoomData().getId() == roomId)
				return true;
		return false;
	}

	@Override
	public void initialize()
	{
		this.roomsSaver = new RoomsSaver();
		this.loadCategories();
		this.loadModels();
		this.loadRoomsWithHighestScore();
	}

	@Override
	public void dispose()
	{
		for (final Room room : this.rooms)
		{
			if (room.getRoomUnitsHandler().getUsers().size() == 0)
				continue;

			for (final RoomUser user : room.getRoomUnitsHandler().getUsers())
			{
				user.getHabbo().leaveRoom(true);
			}
		}

		this.categories.clear();
		this.models.clear();
		this.rooms.clear();
	}

}
