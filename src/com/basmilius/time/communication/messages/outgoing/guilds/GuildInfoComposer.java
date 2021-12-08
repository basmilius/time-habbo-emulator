package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.GuildMemberLevel;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GuildInfoComposer extends MessageComposer
{

	private final Habbo habbo;
	private final Guild guild;
	private final boolean openInfo;

	public GuildInfoComposer(Habbo habbo, Guild guild, boolean openInfo)
	{
		this.habbo = habbo;
		this.guild = guild;
		this.openInfo = openInfo;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GuildInfo);
		response.appendInt(this.guild.getId());
		response.appendBoolean(true);
		response.appendInt(this.guild.getType());
		response.appendString(this.guild.getName());
		response.appendString(this.guild.getDescription());
		response.appendString(this.guild.getBadgeData());
		response.appendInt(this.guild.getRoom().getRoomData().getId());
		response.appendString(this.guild.getRoom().getRoomData().getRoomName());
		response.appendInt(this.guild.getStatus(this.habbo));
		response.appendInt(this.guild.getMembers().size());
		response.appendBoolean(this.habbo.getSettings().getFavoriteGuild() != null && this.habbo.getSettings().getFavoriteGuild().getId() == this.guild.getId());
		response.appendString("04-11-2013"); // date created
		response.appendBoolean(this.habbo.getId() == this.guild.getHabbo().getId());
		response.appendBoolean((this.guild.getHasMember(this.habbo) && (this.guild.getMemberByHabbo(this.habbo).getLevel() == GuildMemberLevel.MODERATOR || this.guild.getMemberByHabbo(this.habbo).getLevel() == GuildMemberLevel.ADMIN)));
		response.appendString(this.guild.getHabbo().getUsername());
		response.appendBoolean(this.openInfo);
		response.appendBoolean(false);
		response.appendInt((this.guild.getHasMember(this.habbo) && (this.guild.getMemberByHabbo(this.habbo).getLevel() == GuildMemberLevel.MODERATOR || this.guild.getMemberByHabbo(this.habbo).getLevel() == GuildMemberLevel.ADMIN)) ? this.guild.getRequestMembers().size() : 0);
		response.appendBoolean(this.guild.isForumActive());
		return response;
	}

}
