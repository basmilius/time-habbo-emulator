package com.basmilius.time.communication.messages.incoming.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.guilds.GuildMember;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildMembersComposer;

import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.RequestGuildMembers)
public class RequestGuildMembersEvent extends MessageEvent
{

	@SuppressWarnings("unused")
	@Override
	public void handle() throws Exception
	{
		List<GuildMember> _members = new ArrayList<>();

		final int guildId = packet.readInt();
		final int pageId = packet.readInt();
		final String keyWord = packet.readString();
		final int memberType = packet.readInt();

		final Guild _guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(guildId);

		if (_guild == null)
			return;

		if (memberType == 0)
		{
			_members = _guild.getMembers(2);
		}
		else if (memberType == 1)
		{
			_members = _guild.getMembers(1);
		}
		else if (memberType == 2)
		{
			_members = _guild.getRequestMembers();
		}

		int _totalResults = _members.size();
		_members = _members.subList((pageId * 14), (_members.size() >= ((pageId * 14) + 14)) ? ((pageId * 14) + 14) : _members.size());

		connection.send(new GuildMembersComposer(memberType, pageId, _guild, _members, _totalResults, (_guild.getHabbo().getId() == connection.getHabbo().getId())));
	}

}
