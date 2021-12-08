package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.habbohotel.abstracts.ISave;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.inventory.InventoryManager;
import com.basmilius.time.habbohotel.habbo.messenger.MessengerManager;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.ambassador.AmbassadorMutedComposer;
import com.basmilius.time.communication.messages.outgoing.general.RoomForwardComposer;
import com.basmilius.time.communication.messages.outgoing.general.SendHotelViewComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.DisconnectRoomComposer;
import com.basmilius.time.communication.messages.outgoing.users.UpdateLookComposer;
import com.basmilius.time.communication.messages.outgoing.users.UpdateLookInRoomComposer;
import com.basmilius.time.util.TimeUtils;
import com.basmilius.time.util.json.IJSONParsable;
import com.basmilius.time.util.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Habbo extends ISave implements IJSONParsable
{

	private SocketConnection connection;
	private HabboCache cache;
	private HabboAchievements achievements;
	private HabboSettings settings;
	private HabboSubscriptions subscriptions;
	private HabboThread habboThread;
	private MessengerManager messengerManager;
	private InventoryManager inventory;

	private int id;
	private String identifier;
	private String username;
	private String realName;
	private String mail;
	private String look;
	private String gender;
	private String motto;
	private int rank;
	private int lastOnline;
	private int accountCreated;

	private Room currentRoom;
	private int currentEffectId;
	private Runnable userEntersRoomCallback;

	private boolean canUseSpecialCommands;
	private Map<String, Boolean> values;

	public Habbo(ResultSet dataRow) throws SQLException
	{
		this.connection = null;

		this.updateData(dataRow);

		this.currentRoom = null;
		this.currentEffectId = 0;
		this.userEntersRoomCallback = null;

		this.cache = new HabboCache(this);

		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.habbos", 30000));

		this.canUseSpecialCommands = false;
		this.values = new HashMap<>();

		this.habboThread = new HabboThread(this);
	}

	public final void updateData(ResultSet dataRow) throws SQLException
	{
		this.id = dataRow.getInt("id");
		this.identifier = dataRow.getString("identifier");
		this.username = dataRow.getString("username");
		this.realName = dataRow.getString("real_name");
		this.mail = dataRow.getString("mail");
		this.look = dataRow.getString("look");
		this.gender = dataRow.getString("gender");
		this.motto = dataRow.getString("motto");
		this.rank = dataRow.getInt("rank");
		this.lastOnline = dataRow.getInt("last_online");
		this.accountCreated = dataRow.getInt("account_created");
	}

	public final void userEntersRoom()
	{
		if (this.userEntersRoomCallback != null)
		{
			this.userEntersRoomCallback.run();
			this.userEntersRoomCallback = null;
		}
	}

	public final void goToRoom(Room room)
	{
		this.getConnection().send(new RoomForwardComposer(room.getRoomData().getId()));
	}

	public final void setUserEntersRoomCallback(Runnable runnable)
	{
		this.userEntersRoomCallback = runnable;
	}

	public final List<String> getPermissions()
	{
		return Bootstrap.getEngine().getGame().getPermissionsManager().getForRank((this.canUseSpecialCommands) ? -1 : this.getRank());
	}

	public final HabboCache getHabboCache()
	{
		return this.cache;
	}

	public final HabboAchievements getAchievements()
	{
		return this.achievements;
	}

	public final HabboSettings getSettings()
	{
		return this.settings;
	}

	public final HabboSubscriptions getSubscriptions()
	{
		return this.subscriptions;
	}

	public final MessengerManager getMessenger()
	{
		return this.messengerManager;
	}

	public final InventoryManager getInventory()
	{
		return this.inventory;
	}

	public final int getId()
	{
		return this.id;
	}

	public final String getIdentifier()
	{
		return this.identifier;
	}

	public final SocketConnection getConnection ()
	{
		return this.connection;
	}

	public final void setConnection (SocketConnection connection)
	{
		this.connection = connection;

		String online = "0";

		if (this.habboThread != null)
		{
			this.habboThread.stop();
		}
		else
		{
			this.habboThread = new HabboThread(this);
		}

		if (this.connection != null)
		{
			online = "1";
			this.lastOnline = TimeUtils.getUnixTimestamp();
			this.habboThread.start();
		}

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE users SET online = ?, last_online = ? WHERE id = ?");
			{
				if (statement != null)
				{
					statement.setString(1, online);
					statement.setInt(2, this.lastOnline);
					statement.setInt(3, this.id);
					statement.execute();
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public final boolean isOnline()
	{
		return (this.connection != null);
	}

	public final String getUsername()
	{
		return this.username;
	}

	public final void setUsername(String username)
	{
		this.username = username;
		this.setNeedsUpdate(true);
	}

	public final String getRealName()
	{
		return this.realName;
	}

	public final String getMail()
	{
		return this.mail;
	}

	public final String getLook()
	{
		return this.look;
	}

	public final String getGender()
	{
		return this.gender;
	}

	public final String getMotto ()
	{
		return this.motto;
	}

	public final int getRank()
	{
		return this.rank;
	}

	public final int getLastOnline()
	{
		return this.lastOnline;
	}

	public final int getAccountCreated()
	{
		return this.accountCreated;
	}

	public final int getCurrentEffectId()
	{
		return this.currentEffectId;
	}

	public final void setCurrentEffectId(Integer effectId)
	{
		this.currentEffectId = effectId;
	}

	public final List<Guild> getGuilds()
	{
		return Bootstrap.getEngine().getGame().getGuildManager().getGuilds(this);
	}

	public final boolean getCanUseSpecialCommands()
	{
		return this.canUseSpecialCommands;
	}

	public final Map<String, Boolean> getValues()
	{
		return this.values;
	}

	public final void leaveRoom()
	{
		this.leaveRoom(false);
	}

	public final void leaveRoom(final boolean sendToHotelView)
	{
		if (this.isInRoom() && this.getRoomUser() != null)
		{
			this.getRoomUser().setIsLeaving(true);

			final HabboRoomVisit roomVisit = this.getHabboCache().getRoomVisit(this.getCurrentRoom());
			if (roomVisit != null)
			{
				roomVisit.setLeaveTime();
			}

			this.getCurrentRoom().getRoomUnitsHandler().removeRoomUnit(this.getRoomUser());
			this.currentRoom = null;
			this.messengerManager.sendUpdate();

			if (sendToHotelView)
			{
				this.getConnection().send(new DisconnectRoomComposer(this.getUsername()));
				this.getConnection().send(new SendHotelViewComposer());
			}
		}
	}

	@SuppressWarnings("unused")
	public final void mute(final int seconds, final Habbo muter)
	{
		if (!this.isOnline() || !this.isInRoom())
			return;

		/**
		 * TODO: Save and log the muter. If this muter was an ambassador save it into ambassador log.
		 */

		this.getHabboCache().setMute(TimeUtils.getUnixTimestamp(), (TimeUtils.getUnixTimestamp() + seconds));
		this.getConnection().send(new AmbassadorMutedComposer(seconds));
	}

	@SuppressWarnings("unused")
	public final void unMute()
	{
		this.getHabboCache().setMute(0, 0);
	}

	public final Room getCurrentRoom()
	{
		return this.currentRoom;
	}

	public final void setCurrentRoom(Room room)
	{
		this.currentRoom = room;
		this.getHabboCache().getRoomVisits().add(new HabboRoomVisit(room));
	}

	public final boolean isInRoom()
	{
		return this.currentRoom != null && this.getRoomUser() != null;
	}

	public final RoomUser getRoomUser()
	{
		final List<RoomUser> users = this.getCurrentRoom().getRoomUnitsHandler().getUsers().stream().filter(u -> u.getHabbo() == this).collect(Collectors.toList());
		if (users.size() > 0)
			return users.get(0);
		return null;
	}

	public final void loadAchievements()
	{
		if (this.achievements == null)
		{
			this.achievements = new HabboAchievements(this);
			this.achievements.loadAchievements();
		}
	}

	public final void loadMessenger()
	{
		if (this.messengerManager == null)
		{
			this.messengerManager = new MessengerManager(this);
		}
	}

	public final void loadSettings()
	{
		if (this.settings == null)
		{
			this.settings = new HabboSettings(this);
		}
	}

	public final void loadSubscriptions()
	{
		if (this.subscriptions == null)
		{
			this.subscriptions = new HabboSubscriptions(this);
		}
	}

	public final void loadInventory()
	{
		if (this.inventory != null)
			return;

		this.inventory = new InventoryManager(this);
	}

	public final void setLook(String figure, String gender, String motto)
	{
		this.look = figure;
		this.gender = gender;
		this.motto = motto;

		this.setNeedsUpdate(true);

		this.getMessenger().sendUpdate();

		this.connection.send(new UpdateLookComposer(this.connection.getHabbo()));
		this.connection.send(new UpdateLookInRoomComposer(-1, this.getLook(), this.getGender(), this.getMotto(), this.getSettings().getAchievementScore()));

		if (this.getRoomUser() != null)
		{
			this.connection.getHabbo().getCurrentRoom().getRoomUnitsHandler().send(new UpdateLookInRoomComposer(this.getRoomUser().getUnitId(), this.getLook(), this.getGender(), this.getMotto(), this.getSettings().getAchievementScore()));
		}
	}

	public final boolean getValue(String key, boolean defaultValue)
	{
		if (this.getValues().containsKey(key))
			return this.getValues().get(key);

		return defaultValue;
	}

	public final void setValue(String key, boolean value)
	{
		if (this.getValues().containsKey(key))
		{
			this.getValues().remove(key);
		}

		this.getValues().put(key, value);
	}

	@Override
	public final void save() throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE users SET username = ?, look = ?, gender = ?, motto = ? WHERE id = ?");
		{
			if (statement != null)
			{
				statement.setString(1, this.username);
				statement.setString(2, this.look);
				statement.setString(3, this.gender);
				statement.setString(4, this.motto);
				statement.setInt(5, this.id);
				statement.execute();
			}
		}
		this.setNeedsUpdate(false);
	}

	@Override
	public final void dispose()
	{
		this.settings.dispose();

		this.achievements.dispose();
		this.achievements = null;

		this.cache.dispose();
		this.cache = null;

		this.habboThread.stop();
		this.habboThread = null;

		this.inventory.dispose();
		this.inventory = null;

		this.messengerManager.dispose();
		this.messengerManager = null;

		this.settings.dispose();
		this.settings = null;

		this.subscriptions.dispose();
		this.subscriptions = null;

		this.values.clear();

		super.dispose();
	}

	@Override
	public final JSONObject parseJSON()
	{
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("identifier", this.getIdentifier());
		jsonObject.put("name", this.getUsername());
		jsonObject.put("look", this.getLook());
		jsonObject.put("gender", this.getGender());
		jsonObject.put("motto", this.getMotto());
		jsonObject.put("rank", this.getRank());
		jsonObject.put("current_room");

		if (this.isInRoom())
		{
			jsonObject.put("current_room", this.getCurrentRoom());
		}

		return jsonObject;
	}
}
