package com.basmilius.time.communication.messages.incoming.room.avatar;

import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.ChangeMotto)
public class ChangeMottoMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final String motto = packet.readString();

		if (!motto.equals(connection.getHabbo().getMotto()))
		{
			connection.getHabbo().setLook(connection.getHabbo().getLook(), connection.getHabbo().getGender(), motto);
			connection.getHabbo().getAchievements().unlockAchievement("ACH_Motto", 1);
		}
	}

}
