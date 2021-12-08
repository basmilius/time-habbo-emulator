package com.basmilius.time.habbohotel.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.core.StringCrypt;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.google.common.collect.Lists;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GuildManager extends IManager
{

	private final List<Guild> guilds;
	private final List<GuildBadgePart> partsBase;
	private final List<GuildBadgePart> partsSymbols;
	private final Map<String, List<GuildColor>> partsColors;

	public GuildManager()
	{
		this.guilds = Lists.newLinkedList();
		this.partsBase = Lists.newLinkedList();
		this.partsSymbols = Lists.newLinkedList();
		this.partsColors = new HashMap<>();

		this.partsColors.put("color", Lists.newLinkedList());
		this.partsColors.put("color2", Lists.newLinkedList());
		this.partsColors.put("color3", Lists.newLinkedList());
	}

	private void loadBadgeParts() throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM groups_items ORDER BY type");
		{
			if (statement != null)
			{
				final ResultSet result = statement.executeQuery();

				while (result.next())
				{
					if (result.getString("type").contains("color"))
					{
						this.partsColors.get(result.getString("type")).add(new GuildColor(result));
					}
					else if (result.getString("type").equals("base"))
					{
						this.partsBase.add(new GuildBadgePart(result));
					}
					else if (result.getString("type").equals("symbol"))
					{
						this.partsSymbols.add(new GuildBadgePart(result));
					}
				}
			}
		}
	}

	public void loadGuildsForHabbo(Habbo _habbo)
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT g.* FROM groups g, groups_members m WHERE g.id = m.guild_id AND m.user_id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, _habbo.getId());

					final ResultSet result = statement.executeQuery();

					while (result.next())
					{
						if (this.getGuild(result.getInt("id")) == null)
						{
							this.guilds.add(new Guild(result));
						}
					}
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public Guild getGuild(int id)
	{
		for (Guild guild : this.guilds)
			if (guild.getId() == id)
				return guild;

		return null;
	}

	public List<Guild> getGuilds(Habbo habbo)
	{
		return this.guilds.stream().filter(guild -> guild.getHasMember(habbo)).collect(Collectors.toList());
	}

	public Guild createGuild(final String name, final String description, final Habbo owner, final Room homeRoom, final int badgeBase, final int badgeBaseColor, final String badgeData, final int customColor1, final int customColor2) throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO groups (user_id, name, description, room_id, color_one_id, color_two_id, base_id, base_color, badge_data) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		{
			if (statement != null)
			{
				statement.setInt(1, owner.getId());
				statement.setString(2, name);
				statement.setString(3, description);
				statement.setInt(4, homeRoom.getRoomData().getId());
				statement.setInt(5, customColor1);
				statement.setInt(6, customColor2);
				statement.setInt(7, badgeBase);
				statement.setInt(8, badgeBaseColor);
				statement.setString(9, badgeData);
				statement.execute();

				final ResultSet keys = statement.getGeneratedKeys();
				keys.next();

				final int groupId = keys.getInt(1);
				final Guild newGuild = new Guild(groupId, name, description, owner, homeRoom, customColor1, customColor2, badgeBase, badgeBaseColor, badgeData);
				this.guilds.add(newGuild);
				return newGuild;
			}
		}
		
		return null;
	}

	public List<Guild> getGuildsFromHabbo(Habbo habbo)
	{
		List<Guild> _guilds = Lists.newLinkedList();

		for (Guild _guild : this.guilds)
			_guilds.addAll(_guild.getMembers().stream().filter(_member -> _member.getHabbo().getId() == habbo.getId()).map(_member -> _guild).collect(Collectors.toList()));

		return _guilds;
	}

	public final Guild getGuildFromRoom(Room room)
	{
		for (Guild guild : this.guilds)
			if (guild.getRoom().getRoomData().getId() == room.getRoomData().getId())
				return guild;

		return null;
	}

	public GuildColor getColor(int colorId)
	{
		for (GuildColor color : this.partsColors.get("color"))
			if (color.getId() == colorId)
				return color;

		return null;
	}

	public GuildColor getColor2(int colorId)
	{
		for (GuildColor color : this.partsColors.get("color2"))
			if (color.getId() == colorId)
				return color;

		return null;
	}

	public GuildColor getColor3(int colorId)
	{
		for (GuildColor color : this.partsColors.get("color3"))
			if (color.getId() == colorId)
				return color;

		return null;
	}

	public GuildBadgePart getBasePart(int partId)
	{
		for (GuildBadgePart part : this.partsBase)
			if (part.getId() == partId)
				return part;

		return null;
	}

	public List<int[]> generateBadgeData(String badgeData)
	{
		List<int[]> _data = Lists.newLinkedList();
		String[] parts = badgeData.split("\\-")[0].split("(?=[bs])");

		for (String s : parts)
		{
			if (s.isEmpty())
				continue;

			List<String> partData = StringCrypt.getParts(s.substring(1, s.length()), 2);

			if (s.length() > 6)
			{
				partData = StringCrypt.getParts(s.substring(2, s.length()), 2);
				partData.set(0, s.substring(1, 2) + partData.get(0));
			}

			_data.add(new int[]{Integer.parseInt(partData.get(0)), Integer.parseInt(partData.get(1)), Integer.parseInt(partData.get(2))});
		}

		if (_data.size() < 5)
		{
			while (_data.size() < 5)
			{
				_data.add(new int[]{0, 0, 0});
			}
		}

		return _data;
	}

	public String generateGuildImage(int guildBase, int guildBaseColor, List<Integer> guildStates)
	{
		String badgeData = "b";
		int partCount = 0;
		int partOffset = 0;
		int partData;

		if (Integer.toString(guildBase).length() >= 2)
		{
			badgeData += "" + guildBase;
		}
		else
		{
			badgeData += "0" + Integer.toString(guildBase);
		}

		if (Integer.toString(guildBaseColor).length() >= 2)
		{
			badgeData += "" + guildBaseColor;
		}
		else
		{
			badgeData += "0" + Integer.toString(guildBaseColor);
		}

		badgeData += "0";

		if (guildStates.size() > 9 && guildStates.get(9) != 0)
		{
			partCount = 4;
		}
		else if (guildStates.size() > 6 && guildStates.get(6) != 0)
		{
			partCount = 3;
		}
		else if (guildStates.size() > 3 && guildStates.get(3) != 0)
		{
			partCount = 2;
		}
		else if (guildStates.size() > 0 && guildStates.get(0) != 0)
		{
			partCount = 1;
		}

		for (int i = 0; i < partCount; i++)
		{
			badgeData += "s";
			partData = (guildStates.get(partOffset));

			if (Integer.toString(partData).length() >= 2)
			{
				badgeData += "" + partData;
			}
			else
			{
				badgeData += "0" + partData;
			}

			partData = (guildStates.get(partOffset + 1));

			if (Integer.toString(partData).length() >= 2)
			{
				badgeData += "" + partData;
			}
			else
			{
				badgeData += "0" + partData;
			}

			badgeData += Integer.toString(guildStates.get(partOffset + 2));

			if (partOffset == 0)
			{
				partOffset = 3;
			}
			else if (partOffset == 3)
			{
				partOffset = 6;
			}
			else if (partOffset == 6)
			{
				partOffset = 9;
			}
		}

		try
		{
			return (badgeData + "-" + StringCrypt.getSHA1Hash(badgeData));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return badgeData;
		}
	}

	public List<GuildBadgePart> getPartsBase()
	{
		return this.partsBase;
	}

	public List<GuildBadgePart> getPartsSymbols()
	{
		return this.partsSymbols;
	}

	public Map<String, List<GuildColor>> getPartsColors()
	{
		return this.partsColors;
	}

	public int getIdFromMemberLevel(GuildMemberLevel _level)
	{
		if (_level == GuildMemberLevel.DELETED)
			return 4;
		else if (_level == GuildMemberLevel.NOT_A_MEMBER)
			return 3;
		else if (_level == GuildMemberLevel.ADMIN)
			return 0;
		else if (_level == GuildMemberLevel.MODERATOR)
			return 1;
		return 2;
	}

	public GuildMemberLevel getMemberLevelFromId(int _id)
	{
		if (_id == 4)
			return GuildMemberLevel.DELETED;
		else if (_id == 3)
			return GuildMemberLevel.NOT_A_MEMBER;
		else if (_id == 0)
			return GuildMemberLevel.ADMIN;
		else if (_id == 1)
			return GuildMemberLevel.MODERATOR;
		return GuildMemberLevel.MEMBER;
	}

	@Override
	public void initialize()
	{
		try
		{
			this.loadBadgeParts();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void dispose()
	{
		this.guilds.clear();
		this.partsBase.clear();
		this.partsColors.clear();
		this.partsSymbols.clear();
	}

}
