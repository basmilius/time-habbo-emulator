package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.GuildMember;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.util.TimeUtils;

public class UpdateGuildMemberComposer extends MessageComposer
{

	private final GuildMember member;

	public UpdateGuildMemberComposer(GuildMember member)
	{
		this.member = member;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UpdateGuildMember);
		response.appendInt(1); // count for loop
		response.appendInt(Bootstrap.getEngine().getGame().getGuildManager().getIdFromMemberLevel(this.member.getLevel()));
		response.appendInt(this.member.getHabbo().getId());
		response.appendString(this.member.getHabbo().getUsername());
		response.appendString(this.member.getHabbo().getLook());
		response.appendString(TimeUtils.unixTimestampToStringWithMonths(this.member.getMemberSince(), "dd-MM-yyyy"));
		return response;
	}

}
