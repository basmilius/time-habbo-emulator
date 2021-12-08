package com.basmilius.time.communication.messages.incoming.namechange;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.namechange.NameChangeClaimResultComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.users.UpdateUnitNameComposer;
import com.basmilius.time.communication.messages.outgoing.users.UserInfoComposer;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.NameChangeClaimName)
public class NameChangeClaimNameEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String name = packet.readString();

		int result;

		if (name.length() < 3)
			result = NameChangeClaimResultComposer.TOO_SHORT;
		else if (name.length() > 15)
			result = NameChangeClaimResultComposer.TOO_LONG;
		else if (Bootstrap.getEngine().getGame().getWordFilterManager().stringContainsBadWord(name))
			result = NameChangeClaimResultComposer.INVALID;
		else if (!connection.getHabbo().getUsername().equalsIgnoreCase(name) && Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(name) != null)
			result = NameChangeClaimResultComposer.TAKEN;
		else if (!Bootstrap.getEngine().getConfig().getBoolean("hotel.name.change.enabled", true))
			result = NameChangeClaimResultComposer.CHANGE_NOT_ALLOWED;
		else
			result = NameChangeClaimResultComposer.CHANGED;

		List<String> names = new ArrayList<>();
		for (int i = 0; i < 3; i++)
		{
			names.add(((name.length() > 15) ? name.substring(0, 14) : name) + (int) Math.floor((new SecureRandom(Integer.toString(i).getBytes())).nextInt(100) * 10));
		}

		if (result == NameChangeClaimResultComposer.CHANGED)
		{
			connection.getHabbo().setUsername(name);
			connection.getHabbo().getSettings().setCanChangeName(false);
			connection.send(new UserInfoComposer(connection));
			if (connection.getHabbo().isInRoom())
			{
				connection.send(new UpdateUnitNameComposer(connection.getHabbo().getRoomUser().getUnitId(), name));
			}
		}

		connection.send(new NameChangeClaimResultComposer(result, name, names));
	}

}
