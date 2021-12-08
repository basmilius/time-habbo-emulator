package com.basmilius.time.communication.messages.outgoing.users;

import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PerkAllowancesComposer extends MessageComposer
{

	private final SocketConnection connection;

	public PerkAllowancesComposer(SocketConnection connection)
	{
		this.connection = connection;
	}

	@Override
	public ServerMessage compose()
	{
		return response.init(Outgoing.PerkAllowances)
				.appendInt(16)
				.appendString("USE_GUIDE_TOOL")
				.appendString("")
				.appendBoolean(true)

				.appendString("GIVE_GUIDE_TOURS")
				.appendString("")
				.appendBoolean(true)

				.appendString("JUDGE_CHAT_REVIEWS")
				.appendString("")
				.appendBoolean(true)

				.appendString("VOTE_IN_COMPETITIONS")
				.appendString("")
				.appendBoolean(true)

				.appendString("CALL_ON_HELPERS")
				.appendString("")
				.appendBoolean(true)

				.appendString("CITIZEN")
				.appendString("")
				.appendBoolean(connection.getHabbo().getSettings().getIsCitizen())

				.appendString("TRADE")
				.appendString("")
				.appendBoolean(connection.getHabbo().getSettings().getCanTrade())

				.appendString("HEIGHTMAP_EDITOR_BETA")
				.appendString("")
				.appendBoolean(this.connection.getHabbo().getPermissions().contains("acc_supporttool"))

				.appendString("EXPERIMENTAL_CHAT_BETA")
				.appendString("")
				.appendBoolean(true)

				.appendString("EXPERIMENTAL_TOOLBAR")
				.appendString("")
				.appendBoolean(true)

				.appendString("BUILDER_AT_WORK")
				.appendString("")
				.appendBoolean(true)

				.appendString("NAVIGATOR_PHASE_ONE_2014")
				.appendString("")
				.appendBoolean(true)

				.appendString("CAMERA")
				.appendString("")
				.appendBoolean(true)

				.appendString("NAVIGATOR_PHASE_TWO_2014")
				.appendString("")
				.appendBoolean(false)

				.appendString("MOUSE_ZOOM")
				.appendString("")
				.appendBoolean(true)

				.appendString("NAVIGATOR_ROOM_THUMBNAIL_CAMERA")
				.appendString("")
				.appendBoolean(false);
	}

}
