package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.habbohotel.utils.avatar.LookFunctions;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.UpdateLook)
public class UpdateLookEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		String gender = packet.readString();
		String figure = packet.readString();

		if ((gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) && LookFunctions.isValidLook(figure) && !figure.equals(connection.getHabbo().getLook()))
		{
			connection.getHabbo().setLook(figure, gender, connection.getHabbo().getMotto());

			connection.getHabbo().getAchievements().unlockAchievement("ACH_AvatarLooks", 1);
		}
	}

}
