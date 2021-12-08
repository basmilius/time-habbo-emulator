package com.basmilius.time.habbohotel.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ISave;
import com.basmilius.time.habbohotel.guilds.forum.GuildForum;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Guild extends ISave
{

	private int id;
	private String name;
	private String description;
	private Habbo habbo;
	private Room room;
	private int type;
	private int colorOne;
	private int colorTwo;
	private int guildBase;
	private int guildBaseColor;
	private String badgeData;
	private boolean allMembersHaveRights;
	private boolean forumActive;
	private int forumReadLevel;
	private int forumReplyLevel;
	private int forumTopicLevel;
	private int forumModerateLevel;

	private GuildForum guildForum;

	private List<GuildMember> members;

	{
		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.guilds", 30000));
		this.members = new ArrayList<>();
	}

	public Guild(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.name = result.getString("name");
		this.description = result.getString("description");
		this.habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this.room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(result.getInt("room_id"));
		this.type = result.getInt("type");
		this.colorOne = result.getInt("color_one_id");
		this.colorTwo = result.getInt("color_two_id");
		this.guildBase = result.getInt("base_id");
		this.guildBaseColor = result.getInt("base_color");
		this.badgeData = result.getString("badge_data");
		this.allMembersHaveRights = (result.getString("members_have_rights").equals("1"));
		this.forumActive = result.getString("forum_active").equals("1");
		this.forumReadLevel = result.getInt("forum_read_level");
		this.forumReplyLevel = result.getInt("forum_reply_level");
		this.forumTopicLevel = result.getInt("forum_topic_level");
		this.forumModerateLevel = result.getInt("forum_moderate_level");

		this.guildForum = new GuildForum(this);

		this.loadMembers();
	}

	public Guild(final int id, final String name, final String description, final Habbo habbo, final Room room, final int colorOne, final int colorTwo, final int guildBase, final int guildBaseColor, final String badgeData)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.habbo = habbo;
		this.room = room;
		this.type = 0;
		this.colorOne = colorOne;
		this.colorTwo = colorTwo;
		this.guildBase = guildBase;
		this.guildBaseColor = guildBaseColor;
		this.badgeData = badgeData;
		this.allMembersHaveRights = false;
		this.forumActive = false;
		this.forumReadLevel = 0;
		this.forumReplyLevel = 0;
		this.forumTopicLevel = 0;
		this.forumModerateLevel = 2;

		this.guildForum = new GuildForum(this);
	}

	public int getId()
	{
		return this.id;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String name)
	{
		if (this.name.equals(name))
			return;

		this.name = name;
		this.setNeedsUpdate(true);
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(final String description)
	{
		if (this.description.equals(description))
			return;

		this.description = description;
		this.setNeedsUpdate(true);
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public Room getRoom()
	{
		return this.room;
	}

	public int getType()
	{
		return this.type;
	}

	public void setType(final int type)
	{
		if (this.type == type)
			return;

		this.type = type;
		this.setNeedsUpdate(true);
	}

	public GuildColor getColorOne()
	{
		return Bootstrap.getEngine().getGame().getGuildManager().getColor2(this.colorOne);
	}

	public void setColorOne(final int colorId)
	{
		if (this.colorOne == colorId)
			return;

		this.colorOne = colorId;
		this.setNeedsUpdate(true);
	}

	public GuildColor getColorTwo()
	{
		return Bootstrap.getEngine().getGame().getGuildManager().getColor3(this.colorTwo);
	}

	public void setColorTwo(final int colorId)
	{
		if (this.colorTwo == colorId)
			return;

		this.colorTwo = colorId;
		this.setNeedsUpdate(true);
	}

	public GuildBadgePart getGuildBase()
	{
		return Bootstrap.getEngine().getGame().getGuildManager().getBasePart(this.guildBase);
	}

	public GuildColor getGuildBaseColor()
	{
		return Bootstrap.getEngine().getGame().getGuildManager().getColor(this.guildBaseColor);
	}

	public String getBadgeData()
	{
		return this.badgeData;
	}

	public void setBadgeData(final String badgeData)
	{
		if (this.badgeData.equals(badgeData))
			return;

		this.badgeData = badgeData;
		this.setNeedsUpdate(true);
	}

	public boolean getAllMembersHaveRights()
	{
		return this.allMembersHaveRights;
	}

	public void setAllMembersHaveRights(final boolean haveRights)
	{
		if (this.allMembersHaveRights == haveRights)
			return;

		this.allMembersHaveRights = haveRights;
		this.setNeedsUpdate(true);
	}

	public GuildMember getMemberByHabbo(final Habbo habbo)
	{
		for (final GuildMember member : this.members)
			if (member.getHabbo().getId() == habbo.getId())
				if (member.getLevel() != GuildMemberLevel.NOT_A_MEMBER)
					if (member.getLevel() != GuildMemberLevel.DELETED)
						return member;

		return null;
	}

	public GuildMember getMemberByHabboIgnoreRequest(final Habbo habbo)
	{
		for (final GuildMember member : this.members)
			if (member.getHabbo().getId() == habbo.getId())
				if (member.getLevel() != GuildMemberLevel.DELETED)
					return member;

		return null;
	}

	public GuildForum getGuildForum()
	{
		return this.guildForum;
	}

	public boolean getMemberIsRequesting(final Habbo habbo)
	{
		final GuildMember member = this.getMemberByHabboIgnoreRequest(habbo);

		return member != null && (member.getLevel() == GuildMemberLevel.NOT_A_MEMBER);

	}

	public boolean getHasMember(final Habbo habbo)
	{
		return (this.getMemberByHabbo(habbo) != null);
	}

	public void loadMembers() throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM groups_members WHERE guild_id = ?");
		{
			if (statement != null)
			{
				statement.setInt(1, this.id);
				final ResultSet result = statement.executeQuery();

				while (result.next())
				{
					this.members.add(new GuildMember(result));
				}
			}
		}
	}

	public List<GuildMember> getMembers()
	{
		return this.getMembers(3);
	}

	public List<Habbo> getMembersHabbos()
	{
		return this.getMembers().stream().map(GuildMember::getHabbo).collect(Collectors.toList());
	}

	public List<GuildMember> getMembers(final int minLevel)
	{
		return this.members.stream().filter(member -> Bootstrap.getEngine().getGame().getGuildManager().getIdFromMemberLevel(member.getLevel()) <= minLevel && member.getLevel() != GuildMemberLevel.NOT_A_MEMBER && member.getLevel() != GuildMemberLevel.DELETED).collect(Collectors.toList());
	}

	public List<GuildMember> getRequestMembers()
	{
		return this.members.stream().filter(member -> member.getLevel() == GuildMemberLevel.NOT_A_MEMBER).collect(Collectors.toList());
	}

	public void addMember(final Habbo habbo)
	{
		this.addMember(habbo, this.type == 1 ? GuildMemberLevel.NOT_A_MEMBER : GuildMemberLevel.MEMBER);
	}

	public void addMember(final Habbo habbo, final GuildMemberLevel level)
	{
		this.members.add(new GuildMember(this, habbo, level));
	}

	public final boolean isModerator(final Habbo habbo)
	{
		final GuildMember member = this.getMemberByHabbo(habbo);

		return member != null && (member.getLevel() == GuildMemberLevel.ADMIN || member.getLevel() == GuildMemberLevel.MODERATOR);
	}

	public final boolean isForumModerator(final Habbo habbo)
	{
		return this.isModerator(habbo) && this.getMemberByHabbo(habbo).getLevelID() <= Math.abs(this.forumModerateLevel - 3);
	}

	public final boolean isForumActive()
	{
		return this.forumActive;
	}

	public final void setForumActive(final boolean forumActive)
	{
		this.forumActive = forumActive;
	}

	public final int getForumReadLevel()
	{
		return this.forumReadLevel;
	}

	public final void setForumReadLevel(final int forumReadLevel)
	{
		this.forumReadLevel = forumReadLevel;
	}

	public final int getForumReplyLevel()
	{
		return this.forumReplyLevel;
	}

	public final void setForumReplyLevel(final int forumReplyLevel)
	{
		this.forumReplyLevel = forumReplyLevel;
	}

	public final int getForumTopicLevel()
	{
		return this.forumTopicLevel;
	}

	public final void setForumTopicLevel(final int forumTopicLevel)
	{
		this.forumTopicLevel = forumTopicLevel;
	}

	public final int getForumModerateLevel()
	{
		return this.forumModerateLevel;
	}

	public final void setForumModerateLevel(final int forumModerateLevel)
	{
		this.forumModerateLevel = forumModerateLevel;
	}

	public int getStatus(final Habbo habbo)
	{
		for (final GuildMember _member : this.members)
			if (_member.getHabbo().getId() == habbo.getId())
				if (_member.getLevel() == GuildMemberLevel.NOT_A_MEMBER)
					return 2;
				else
					return 1;
		return 0;
	}

	@Override
	public void save() throws SQLException
	{
		if (this.getNeedsUpdate())
		{
			int i = 0;
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE groups SET name = ?, description = ?, type = ?, room_id = ?, color_one_id = ?, color_two_id = ?, base_id = ?, base_color = ?, badge_data = ?, members_have_rights = ? WHERE id = ?");
			{
				if (statement != null)
				{
					statement.setString(++i, this.name);
					statement.setString(++i, this.description);
					statement.setInt(++i, this.type);
					statement.setInt(++i, this.room.getRoomData().getId());
					statement.setInt(++i, this.colorOne);
					statement.setInt(++i, this.colorTwo);
					statement.setInt(++i, this.guildBase);
					statement.setInt(++i, this.guildBaseColor);
					statement.setString(++i, this.badgeData);
					statement.setString(++i, ((this.allMembersHaveRights) ? "1" : "0"));
					statement.setInt(++i, this.id);
					statement.executeUpdate();
				}
			}

			this.setNeedsUpdate(false);
		}

		this.members.stream().filter(member -> member.getNeedsUpdate() || member.getNeedsInsert()).forEach(member -> {
			try
			{
				member.save();
			}
			catch (SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
			}
		});
	}

	public void removeMember(final GuildMember member)
	{
		this.members.remove(member);
	}

}
