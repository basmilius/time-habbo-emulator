package com.basmilius.time.habbohotel.habbo;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ISave;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.youtube.YouTubeManager;
import com.basmilius.time.communication.messages.outgoing.users.CreditsComposer;
import com.basmilius.time.communication.messages.outgoing.users.FuserightComposer;
import com.basmilius.time.util.TimeUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("UnusedDeclaration")
public class HabboSettings extends ISave
{

	private final Habbo habbo;

	private boolean canChangeName;
	private int credits;
	private int achievementScore;
	private int dailyRespectPoints;
	private int dailyPetRespectPoints;
	private int respectsGiven;
	private int respectsReceived;
	private int favoriteGuildId;
	private boolean canTrade;
	private boolean isCitizen;
	private int citizenLevel;
	private int helperLevel;
	private boolean preventedFollowing;
	private int systemVolume;
	private int furniVolume;
	private int traxVolume;
	private int homeRoom;
	private int supportChfs;
	private int supportChfsAbusive;
	private int supportCautions;
	private int supportBans;
	private boolean supportBanned;
	private int secondsOnline;
	private String moodLightPresets;
	private boolean preferOldChat;
	private boolean ignoreRoomInvites;
	private boolean dontFocusOnOwnAvatar;
	private boolean isAmbassador;
	private int latestChatBubble;
	private int toolbarState;

	private HabboCurrencies currencies;
	private HabboIgnores ignores;
	private HabboRoomFavorites roomFavorites;
	private HabboWardrobe wardrobe;
	private YouTubeManager youTubeManager;

	public HabboSettings(final Habbo habbo)
	{
		this.habbo = habbo;

		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.habbos", 30000));

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users_settings WHERE user_id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, this.habbo.getId());
					final ResultSet result = statement.executeQuery();
					result.next();

					this.canChangeName = result.getString("can_change_name").equals("1");
					this.credits = result.getInt("credits");
					this.dailyRespectPoints = result.getInt("daily_respect_points");
					this.dailyPetRespectPoints = result.getInt("daily_pet_respect_points");
					this.respectsGiven = result.getInt("respects_given");
					this.respectsReceived = result.getInt("respects_received");
					this.achievementScore = result.getInt("achievement_score");
					this.favoriteGuildId = result.getInt("favorite_guild_id");
					this.canTrade = result.getString("can_trade").equals("1");
					this.isCitizen = result.getString("is_citizen").equals("1");
					this.citizenLevel = result.getInt("citizen_level");
					this.helperLevel = result.getInt("helper_level");
					this.preventedFollowing = (result.getString("prevented_following").equals("1"));
					this.systemVolume = result.getInt("volume_system");
					this.furniVolume = result.getInt("volume_furni");
					this.traxVolume = result.getInt("volume_trax");
					this.homeRoom = result.getInt("home_room");
					this.supportChfs = result.getInt("support_chfs");
					this.supportChfsAbusive = result.getInt("support_chfs_abusive");
					this.supportCautions = result.getInt("support_cautions");
					this.supportBans = result.getInt("support_bans");
					this.supportBanned = (result.getString("support_banned").equals("1"));
					this.secondsOnline = result.getInt("seconds_online");
					this.moodLightPresets = result.getString("moodlight_presets");
					this.preferOldChat = (result.getString("prefer_old_chat").equals("1"));
					this.ignoreRoomInvites = (result.getString("ignore_room_invites").equals("1"));
					this.dontFocusOnOwnAvatar = (result.getString("dont_focus_on_own_avatar").equals("1"));
					this.isAmbassador = (result.getString("is_ambassador").equals("1"));
					this.latestChatBubble = result.getInt("latest_chat_bubble");
					this.toolbarState = result.getInt("toolbar_state");

					this.currencies = new HabboCurrencies(this.getHabbo());
					this.ignores = new HabboIgnores(this.getHabbo());
					this.roomFavorites = new HabboRoomFavorites(this.getHabbo());
					this.wardrobe = new HabboWardrobe(this.getHabbo());
					this.youTubeManager = new YouTubeManager(this.getHabbo());
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public final int getLatestChatBubble()
	{
		return latestChatBubble;
	}

	public final void setLatestChatBubble(final int latestChatBubble)
	{
		if (this.latestChatBubble == latestChatBubble)
			return;

		this.latestChatBubble = latestChatBubble;

		this.setNeedsUpdate(true);
	}

	public final int getToolbarState()
	{
		return this.toolbarState;
	}

	public final void setToolbarState(final int toolbarState)
	{
		this.toolbarState = toolbarState;
	}

	private Habbo getHabbo()
	{
		return this.habbo;
	}

	public boolean getCanChangeName()
	{
		return this.canChangeName;
	}

	public void setCanChangeName(final boolean can)
	{
		this.canChangeName = can;
		this.setNeedsUpdate(true);
	}

	public int getCredits()
	{
		return this.credits;
	}

	public HabboCurrencies getCurrencies()
	{
		return this.currencies;
	}

	public int getAchievementScore()
	{
		return this.achievementScore;
	}

	public int getDailyRespectPoints()
	{
		return this.dailyRespectPoints;
	}

	public int getDailyPetRespectPoints()
	{
		return this.dailyPetRespectPoints;
	}

	public int getRespectsGiven()
	{
		return this.respectsGiven;
	}

	public int getRespectsReceived()
	{
		return this.respectsReceived;
	}

	public Guild getFavoriteGuild()
	{
		if (this.favoriteGuildId == 0)
			return null;

		Guild guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(this.favoriteGuildId);
		if (guild.getHasMember(this.getHabbo()))
			return guild;

		return null;
	}

	public void setFavoriteGuild(final Guild guild)
	{
		this.favoriteGuildId = (guild != null) ? guild.getId() : 0;

		this.setNeedsUpdate(true);
	}

	public boolean getCanTrade()
	{
		return this.canTrade;
	}

	public void setCanTrade(final boolean canTrade)
	{
		this.canTrade = canTrade;

		this.setNeedsUpdate(true);
	}

	public boolean getIsAmbassador()
	{
		return this.isAmbassador;
	}

	public void setIsAmbassador(final boolean isAmbassador)
	{
		this.isAmbassador = isAmbassador;

		if (this.habbo.isOnline())
		{
			this.habbo.getConnection().send(new FuserightComposer(this.habbo));
		}

		this.setNeedsUpdate(true);
	}

	public boolean getIsCitizen()
	{
		return this.isCitizen;
	}

	public void setIsCitizen(final boolean isCitizen)
	{
		this.isCitizen = isCitizen;

		this.setNeedsUpdate(true);
	}

	public int getCitizenLevel()
	{
		return this.citizenLevel;
	}

	public void setCitizenLevel(final int level)
	{
		this.citizenLevel = level;

		this.setNeedsUpdate(true);
	}

	public int getHelperLevel()
	{
		return this.helperLevel;
	}

	public void setHelperLevel(final int level)
	{
		this.helperLevel = level;

		this.setNeedsUpdate(true);
	}

	public boolean getPreventedFollowing()
	{
		return this.preventedFollowing;
	}

	public int getSystemVolume()
	{
		return this.systemVolume;
	}

	public int getFurniVolume()
	{
		return this.furniVolume;
	}

	public int getTraxVolume()
	{
		return this.traxVolume;
	}

	public int getHomeRoom()
	{
		return this.homeRoom;
	}

	public void setHomeRoom(final int roomId)
	{
		this.homeRoom = roomId;

		this.setNeedsUpdate(true);
	}

	public int getSupportChfs()
	{
		return this.supportChfs;
	}

	public int getSupportChfsAbusive()
	{
		return this.supportChfsAbusive;
	}

	public int getSupportCautions()
	{
		return this.supportCautions;
	}

	public int getSupportBans()
	{
		return this.supportBans;
	}

	public boolean getSupportBanned()
	{
		return this.supportBanned;
	}

	public String getMoodLightPresets()
	{
		return this.moodLightPresets;
	}

	public void setMoodLightPresets(final String presets)
	{
		this.moodLightPresets = presets;

		this.setNeedsUpdate(true);
	}

	public boolean getDontFocusOnOwnAvatar()
	{
		return this.dontFocusOnOwnAvatar;
	}

	public void setDontFocusOnOwnAvatar(final boolean dontFocusOnOwnAvatar)
	{
		this.dontFocusOnOwnAvatar = dontFocusOnOwnAvatar;

		this.setNeedsUpdate(true);
	}

	public boolean getIgnoreRoomInvites()
	{
		return this.ignoreRoomInvites;
	}

	public void setIgnoreRoomInvites(final boolean ignoreRoomInvites)
	{
		this.ignoreRoomInvites = ignoreRoomInvites;

		this.setNeedsUpdate(true);
	}

	public boolean getPreferOldChat()
	{
		return this.preferOldChat;
	}

	public void setPreferOldChat(final boolean preferOldChat)
	{
		this.preferOldChat = preferOldChat;

		this.setNeedsUpdate(true);
	}

	public HabboWardrobe getWardrobe()
	{
		return this.wardrobe;
	}

	public HabboIgnores getIgnores()
	{
		return this.ignores;
	}

	public HabboRoomFavorites getRoomFavorites()
	{
		return this.roomFavorites;
	}

	public YouTubeManager getYouTubeManager()
	{
		return this.youTubeManager;
	}

	public void updateCredits(final int value)
	{
		this.credits += value;

		if (this.habbo.isOnline())
			this.habbo.getConnection().send(new CreditsComposer(this.habbo.getConnection()));

		this.setNeedsUpdate(true);
	}

	public void updateDailyRespectPoints(final int respectPoints, final int petRespectPoints)
	{
		this.dailyRespectPoints += respectPoints;
		this.dailyPetRespectPoints += petRespectPoints;

		this.setNeedsUpdate(true);
	}

	public void updateRespect(final int given, final int received)
	{
		this.respectsGiven += given;
		this.respectsReceived += received;

		this.setNeedsUpdate(true);
	}

	public void updateSecondsOnline()
	{
		this.secondsOnline = (this.secondsOnline + 1);

		if ((this.secondsOnline % (5 * 60)) == 0)
		{
			this.habbo.getAchievements().unlockAchievement("ACH_AllTimeHotelPresence", (this.secondsOnline / 60), true);
		}

		if ((this.secondsOnline % 60) == 0)
		{
			this.habbo.getAchievements().unlockAchievement("ACH_RegistrationDuration", (TimeUtils.getUnixTimestamp() - this.getHabbo().getAccountCreated()) / 86400, true);
		}

		this.setNeedsUpdate(true);
	}

	public void setVolumes(final int systemVolume, final int furniVolume, final int traxVolume)
	{
		this.systemVolume = systemVolume;
		this.furniVolume = furniVolume;
		this.traxVolume = traxVolume;

		this.setNeedsUpdate(true);
	}

	@Override
	public void save() throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE users_settings SET credits = ?, achievement_score = ?, daily_respect_points = ?, daily_pet_respect_points = ?, respects_given = ?, respects_received = ?, favorite_guild_id = ?, can_trade = ?, is_citizen = ?, citizen_level = ?, helper_level = ?, volume_system = ?, volume_furni = ?, volume_trax = ?, home_room = ?, support_chfs = ?, support_chfs_abusive = ?, support_cautions = ?, support_bans = ?, support_banned = ?, seconds_online = ?, moodlight_presets = ?, can_change_name = ?, dont_focus_on_own_avatar = ?, ignore_room_invites = ?, prefer_old_chat = ?, is_ambassador = ?, latest_chat_bubble = ?, toolbar_state = ? WHERE user_id = ?");
		{
			if (statement != null)
			{
				int i = 0;
				statement.setInt(++i, this.credits);
				statement.setInt(++i, this.achievementScore);
				statement.setInt(++i, this.dailyRespectPoints);
				statement.setInt(++i, this.dailyPetRespectPoints);
				statement.setInt(++i, this.respectsGiven);
				statement.setInt(++i, this.respectsReceived);
				statement.setInt(++i, this.favoriteGuildId);
				statement.setString(++i, ((this.canTrade) ? "1" : "0"));
				statement.setString(++i, ((this.isCitizen) ? "1" : "0"));
				statement.setInt(++i, this.citizenLevel);
				statement.setInt(++i, this.helperLevel);
				statement.setInt(++i, this.systemVolume);
				statement.setInt(++i, this.furniVolume);
				statement.setInt(++i, this.traxVolume);
				statement.setInt(++i, this.homeRoom);
				statement.setInt(++i, this.supportChfs);
				statement.setInt(++i, this.supportChfsAbusive);
				statement.setInt(++i, this.supportCautions);
				statement.setInt(++i, this.supportBans);
				statement.setString(++i, ((this.supportBanned) ? "1" : "0"));
				statement.setInt(++i, this.secondsOnline);
				statement.setString(++i, this.moodLightPresets);
				statement.setString(++i, ((this.canChangeName) ? "1" : "0"));
				statement.setString(++i, ((this.dontFocusOnOwnAvatar) ? "1" : "0"));
				statement.setString(++i, ((this.ignoreRoomInvites) ? "1" : "0"));
				statement.setString(++i, ((this.preferOldChat) ? "1" : "0"));
				statement.setString(++i, ((this.isAmbassador) ? "1" : "0"));
				statement.setInt(++i, this.latestChatBubble);
				statement.setInt(++i, this.toolbarState);
				statement.setInt(++i, this.habbo.getId());
				statement.execute();
			}
		}

		this.setNeedsUpdate(false);
	}

}
