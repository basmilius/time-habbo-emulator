package com.basmilius.time.communication.messages.outgoing.guilds.forum;

import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class GuildForumGuildDataComposer extends MessageComposer
{

	private final Guild guild;
	private final Habbo habbo;

	public GuildForumGuildDataComposer(final Guild guild, final Habbo habbo)
	{
		this.guild = guild;
		this.habbo = habbo;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.GuildForumGuildData);
		this.guild.getGuildForum().serializeData(this.habbo, response);
		response.appendInt(this.guild.getForumReadLevel());
		response.appendInt(this.guild.getForumReplyLevel());
		response.appendInt(this.guild.getForumTopicLevel());
		response.appendInt(this.guild.getForumModerateLevel());
		response.appendString(""); // authorisation error, if any.
		response.appendString(this.guild.getHasMember(this.habbo) ? "" : "not_member");
		response.appendString(this.guild.getHasMember(this.habbo) ? "" : "not_member");
		response.appendString(this.guild.isModerator(this.habbo) ? "" : "not_admin");
		response.appendString("");
		response.appendBoolean(this.guild.getHabbo().getId() == this.habbo.getId());
		response.appendBoolean(false);
		return response;
	}

}
