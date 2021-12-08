package com.basmilius.time.communication.messages.incoming.namechange;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.namechange.NameChangeCheckResultComposer;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Event(id = Incoming.NameChangeCheckName)
public class NameChangeCheckNameEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String name = packet.readString();

		int result;

		if (name.length() < 3)
			result = NameChangeCheckResultComposer.TOO_SHORT;
		else if (name.length() > 15)
			result = NameChangeCheckResultComposer.TOO_LONG;
		else if (Bootstrap.getEngine().getGame().getWordFilterManager().stringContainsBadWord(name))
			result = NameChangeCheckResultComposer.INVALID;
		else if (!connection.getHabbo().getUsername().equalsIgnoreCase(name) && Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(name) != null)
			result = NameChangeCheckResultComposer.TAKEN;
		else if (!Bootstrap.getEngine().getConfig().getBoolean("hotel.name.change.enabled", true))
			result = NameChangeCheckResultComposer.CHANGE_NOT_ALLOWED;
		else
			result = NameChangeCheckResultComposer.AVAILABLE;

		List<String> names = new ArrayList<>();
		for (int i = 0; i < 3; i++)
		{
			names.add(((name.length() > 15) ? name.substring(0, 14) : name) + (int) Math.floor((new SecureRandom(Integer.toString(i).getBytes())).nextInt(100) * 10));
		}

		connection.send(new NameChangeCheckResultComposer(result, name, names));
	}

}
