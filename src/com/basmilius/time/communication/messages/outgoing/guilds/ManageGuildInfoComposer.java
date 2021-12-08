package com.basmilius.time.communication.messages.outgoing.guilds;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class ManageGuildInfoComposer extends MessageComposer
{

	private final List<Room> rooms;
	private final Guild guild;

	public ManageGuildInfoComposer(Guild guild, List<Room> rooms)
	{
		this.guild = guild;
		this.rooms = rooms;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.ManageGuildInfo);
		response.appendInt(this.rooms.size());
		for (Room room : this.rooms)
		{
			response.appendInt(room.getRoomData().getId());
			response.appendString(room.getRoomData().getRoomName());
			response.appendBoolean(false);
		}
		response.appendBoolean(true); // TODO: Don't know this boolean.
		response.appendInt(this.guild.getId());
		response.appendString(this.guild.getName());
		response.appendString(this.guild.getDescription());
		response.appendInt(this.guild.getRoom().getRoomData().getId());
		response.appendInt(this.guild.getColorOne().getId());
		response.appendInt(this.guild.getColorTwo().getId());
		response.appendInt(this.guild.getType());
		response.appendInt((this.guild.getAllMembersHaveRights()) ? 0 : 1);
		response.appendBoolean(false);
		response.appendString("");
		response.appendInt(5);
		for (final int[] partData : Bootstrap.getEngine().getGame().getGuildManager().generateBadgeData(this.guild.getBadgeData()))
		{
			response.appendInt(partData[0]);
			response.appendInt(partData[1]);
			response.appendInt(partData[2]);
		}
		response.appendString(this.guild.getBadgeData());
		response.appendInt(this.guild.getMembers().size());
		return response;
	}

}
