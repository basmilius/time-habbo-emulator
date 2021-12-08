package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.DoorMode;
import com.basmilius.time.habbohotel.enums.TradingMode;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.floorplaneditor.FloorPlanData;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class RoomData
{

	private final Room room;
	
	private final int id;
	private final Habbo owner;
	private String roomName;
	private String roomDescription;
	private DoorMode doorMode;
	private String roomPassword;
	private RoomModel roomModel;
	private TradingMode tradingMode;
	private int usersLimit;
	private int guildId;
	private int category;
	private int score;
	private final RoomDecoration roomDecoration;
	private String[] tags;
	private final RoomFreeFlowChat freeFlowChat;
	private FloorPlanData floorPlanData;
	private final RoomPermissions permissions;
	private RoomEvent roomEvent;
	
	public RoomData(final Room room, final ResultSet result) throws SQLException, NoSuchAlgorithmException
	{
		this.room = room;
		
		this.id = result.getInt("id");
		this.owner = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("owner_id"));
		this.roomName = result.getString("room_name");
		this.roomDescription = result.getString("room_description");
		this.doorMode = DoorMode.fromInt(result.getInt("door_mode"));
		this.roomPassword = result.getString("room_password");
		this.tradingMode = TradingMode.fromInt(result.getInt("trading_mode"));
		this.usersLimit = result.getInt("users_max");
		this.guildId = result.getInt("guild_id");
		this.category = result.getInt("category");
		this.score = result.getInt("score");
		this.roomDecoration = new RoomDecoration(this.room, result);
		this.tags = result.getString("tags").split(",");
		this.freeFlowChat = new RoomFreeFlowChat(this.room, result);
		this.floorPlanData = new FloorPlanData(this.room, result);
		this.permissions = new RoomPermissions(this.room, result);
		
		final String model = result.getString("room_model");
		if (this.floorPlanData.getFloorPlan().isEmpty())
		{
			this.roomModel = Bootstrap.getEngine().getGame().getRoomManager().getRoomModel(model);
		}
		else
		{
			this.roomModel = new RoomModel(this.floorPlanData);
		}
	}

	public final int getId()
	{
		return this.id;
	}

	public final Habbo getOwner()
	{
		return this.owner;
	}

	public final String getRoomName()
	{
		return this.roomName;
	}

	public final String getRoomDescription()
	{
		return this.roomDescription;
	}

	public final DoorMode getDoorMode()
	{
		return this.doorMode;
	}

	public final String getRoomPassword()
	{
		return this.roomPassword;
	}

	public final RoomModel getRoomModel()
	{
		return this.roomModel;
	}

	public final TradingMode getTradingMode()
	{
		return this.tradingMode;
	}

	public final int getUsersLimit()
	{
		return this.usersLimit;
	}

	public final Guild getGuild()
	{
		return Bootstrap.getEngine().getGame().getGuildManager().getGuild(this.guildId);
	}
	
	public final int getGuildId()
	{
		return this.guildId;
	}

	public final int getCategory()
	{
		return this.category;
	}

	public final int getScore()
	{
		return this.score;
	}

	public final RoomDecoration getRoomDecoration()
	{
		return this.roomDecoration;
	}

	public final String[] getTags()
	{
		return this.tags;
	}

	public final RoomFreeFlowChat getFreeFlowChat()
	{
		return this.freeFlowChat;
	}

	public final FloorPlanData getFloorPlanData()
	{
		return this.floorPlanData;
	}

	public final RoomPermissions getPermissions()
	{
		return this.permissions;
	}
	
	public final RoomEvent getRoomEvent()
	{
		return this.roomEvent;
	}

	public final void setRoomName(final String roomName)
	{
		this.roomName = roomName;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setRoomDescription(final String roomDescription)
	{
		this.roomDescription = roomDescription;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setDoorMode(final DoorMode doorMode)
	{
		this.doorMode = doorMode;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setRoomPassword(final String roomPassword)
	{
		this.roomPassword = roomPassword;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setTradingMode(final TradingMode tradingMode)
	{
		this.tradingMode = tradingMode;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setUsersLimit(final int usersLimit)
	{
		this.usersLimit = usersLimit;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setGuildId(final int guildId)
	{
		this.guildId = guildId;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setCategory(final int category)
	{
		this.category = category;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setScore(final int score)
	{
		this.score = score;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setTags(final String[] tags)
	{
		this.tags = tags;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}
	
	public final void setRoomEvent(final RoomEvent roomEvent)
	{
		this.roomEvent = roomEvent;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}
	
	public final void setFloorPlanData(final FloorPlanData floorPlanData) throws NoSuchAlgorithmException
	{
		this.floorPlanData = floorPlanData;
		this.roomModel = new RoomModel(this.floorPlanData);
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}
	
}
