package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.GuildMember;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.util.TimeUtils;

import java.util.List;

public class GuildMembersComposer extends MessageComposer
{

	private final int searchType;
	private final int pageId;
	private final Guild guild;
	private final List<GuildMember> members;
	private final int totalResults;
	private final boolean isOwner;

	public GuildMembersComposer(final int searchType, final int pageId, final Guild guild, final List<GuildMember> members, final int totalResults, final boolean isOwner)
	{
		this.searchType = searchType;
		this.pageId = pageId;
		this.guild = guild;
		this.members = members;
		this.totalResults = totalResults;
		this.isOwner = isOwner;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.GuildMembers);
		response.appendInt(this.guild.getId());
		response.appendString(this.guild.getName());
		response.appendInt(this.guild.getRoom().getRoomData().getId());
		response.appendString(this.guild.getBadgeData());
		response.appendInt(this.totalResults);
		response.appendInt(this.members.size());
		for (GuildMember member : this.members)
		{
			response.appendInt(Bootstrap.getEngine().getGame().getGuildManager().getIdFromMemberLevel(member.getLevel()));
			response.appendInt(member.getHabbo().getId());
			response.appendString(member.getHabbo().getUsername());
			response.appendString(member.getHabbo().getLook());
			response.appendString((Bootstrap.getEngine().getGame().getGuildManager().getIdFromMemberLevel(member.getLevel()) < 3) ? TimeUtils.unixTimestampToStringWithMonths(member.getMemberSince(), "dd-MM-yyyy") : "");
		}
		response.appendBoolean(this.isOwner);
		response.appendInt(14);
		response.appendInt(this.pageId);
		response.appendInt(this.searchType);
		response.appendString("");
		return response;
	}

}
