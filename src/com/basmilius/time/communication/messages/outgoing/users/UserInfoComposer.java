package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.text.SimpleDateFormat;

public class UserInfoComposer extends MessageComposer
{

	private final SocketConnection connection;

	public UserInfoComposer(SocketConnection connection)
	{
		this.connection = connection;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.UserInfo);
		response.appendInt(this.connection.getHabbo().getId());
		response.appendString(this.connection.getHabbo().getUsername());
		response.appendString(this.connection.getHabbo().getLook());
		response.appendString(this.connection.getHabbo().getGender());
		response.appendString(this.connection.getHabbo().getMotto());
		response.appendString(this.connection.getHabbo().getRealName());
		response.appendBoolean(true);
		response.appendInt(this.connection.getHabbo().getSettings().getRespectsReceived());
		response.appendInt(this.connection.getHabbo().getSettings().getDailyRespectPoints());
		response.appendInt(this.connection.getHabbo().getSettings().getDailyPetRespectPoints());
		response.appendBoolean(false);
		response.appendString(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.connection.getHabbo().getLastOnline()));
		response.appendBoolean(this.connection.getHabbo().getSettings().getCanChangeName());
		response.appendBoolean(false);
		return response;
	}

}
