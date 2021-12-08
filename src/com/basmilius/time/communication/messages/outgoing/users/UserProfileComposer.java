package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.util.TimeUtils;

import java.text.SimpleDateFormat;

public class UserProfileComposer extends MessageComposer
{

	private final SocketConnection connection;
	private final Habbo habbo;

	public UserProfileComposer(SocketConnection connection, Habbo habbo)
	{
		this.connection = connection;
		this.habbo = habbo;
		this.habbo.loadSettings();
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UserProfile);
		response.appendInt(this.habbo.getId());
		response.appendString(this.habbo.getUsername());
		response.appendString(this.habbo.getLook());
		response.appendString(this.habbo.getMotto());
		response.appendString(new SimpleDateFormat("dd-MM-yyyy").format(((long) this.habbo.getAccountCreated() * (long) 1000)));
		response.appendInt(this.habbo.getSettings().getAchievementScore());
		response.appendInt(this.habbo.getMessenger().getFriends().size());
		response.appendBoolean(this.connection.getHabbo().getId() != this.habbo.getId() && this.connection.getHabbo().getMessenger().isFriendWith(this.habbo));
		response.appendBoolean(!this.connection.getHabbo().getMessenger().isFriendWith(this.habbo) && this.connection.getHabbo().getMessenger().isHabboRequested(this.habbo));
		response.appendBoolean(this.habbo.isOnline());
		response.appendInt(this.habbo.getGuilds().size());
		for (Guild guild : this.habbo.getGuilds())
		{
			response.appendInt(guild.getId());
			response.appendString(guild.getName());
			response.appendString(guild.getBadgeData());
			response.appendString(guild.getColorOne());
			response.appendString(guild.getColorTwo());
			response.appendBoolean(this.habbo.getSettings().getFavoriteGuild() != null && this.habbo.getSettings().getFavoriteGuild().getId() == guild.getId());
			response.appendInt(0); // forums id?
			response.appendBoolean(false); // forums enabled?
		}
		response.appendInt(TimeUtils.getUnixTimestamp() - this.habbo.getLastOnline());
		response.appendBoolean(true);
		return response;
	}

}
