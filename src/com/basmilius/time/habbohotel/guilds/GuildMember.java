package com.basmilius.time.habbohotel.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ISave;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.util.TimeUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuildMember extends ISave
{

	private int guild;
	private Habbo habbo;
	private int level;
	private int memberSince;

	{
		this.setSaveTime(Bootstrap.getEngine().getConfig().getInt("server.thread.save.guilds", 30000));
	}

	public GuildMember(final ResultSet result) throws SQLException
	{
		this.guild = result.getInt("guild_id");
		this.habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this.level = result.getInt("level_id");
		this.memberSince = result.getInt("member_since");
	}

	public GuildMember(final Guild guild, final Habbo habbo, final GuildMemberLevel level)
	{
		this.guild = guild.getId();
		this.habbo = habbo;
		this.level = Bootstrap.getEngine().getGame().getGuildManager().getIdFromMemberLevel(level);
		this.memberSince = TimeUtils.getUnixTimestamp();

		this.setNeedsInsert(true);
		this.setNeedsUpdate(true);
	}

	Guild getGuild()
	{
		return Bootstrap.getEngine().getGame().getGuildManager().getGuild(this.guild);
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public GuildMemberLevel getLevel()
	{
		return Bootstrap.getEngine().getGame().getGuildManager().getMemberLevelFromId(this.level);
	}

	public void setLevel(GuildMemberLevel level)
	{
		if (this.getLevel() == level)
			return;

		this.level = Bootstrap.getEngine().getGame().getGuildManager().getIdFromMemberLevel(level);
		this.setNeedsUpdate(true);
	}

	public int getLevelID()
	{
		return this.level;
	}

	public int getMemberSince()
	{
		return this.memberSince;
	}

	@Override
	public void save() throws SQLException
	{
		if (this.getNeedsDelete())
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("DELETE FROM groups_members WHERE user_id = ? AND guild_id = ?");
			{
				statement.setInt(1, this.habbo.getId());
				statement.setInt(2, this.guild);
				statement.execute();
			}
		}
		else if (this.getNeedsInsert())
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO groups_members (guild_id, user_id, level_id, member_since) VALUES (?, ?, ?, ?)");
			{
				statement.setInt(1, this.guild);
				statement.setInt(2, this.habbo.getId());
				statement.setInt(3, this.level);
				statement.setInt(4, this.memberSince);
				statement.execute();
			}
		}
		else if (this.getNeedsUpdate())
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("UPDATE groups_members SET level_id = ?, member_since = ? WHERE user_id = ? AND guild_id = ?");
			{
				statement.setInt(1, this.level);
				statement.setInt(2, this.memberSince);
				statement.setInt(3, this.habbo.getId());
				statement.setInt(4, this.guild);
				statement.executeUpdate();
			}
		}

		this.setNeedsDelete(false);
		this.setNeedsInsert(false);
		this.setNeedsUpdate(false);
	}

	public void delete()
	{
		this.setNeedsUpdate(true);
		this.setNeedsDelete(true);

		this.level = 4;

		this.getGuild().removeMember(this);
	}

}
