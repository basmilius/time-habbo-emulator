package com.basmilius.time.communication.messages.incoming.rooms.users;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.AvatarAction;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.users.RoomUserRespectedComposer;

@Event(id = Incoming.GiveRespect)
public class GiveRespectEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int userId = packet.readInt();
		final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(userId);

		if (habbo == null)
			return;

		if (connection.getHabbo().getSettings().getDailyRespectPoints() <= 0)
			return;

		if (connection.getHabbo().getId() == habbo.getId())
			return;

		if (!habbo.isInRoom() || !connection.getHabbo().isInRoom())
			return;
		
		if (connection.getHabbo().getRoomUser() == null)
			return;

		connection.getHabbo().getSettings().updateDailyRespectPoints(-1, 0);
		connection.getHabbo().getSettings().updateRespect(+1, 0);
		connection.getHabbo().getRoomUser().applyAction(AvatarAction.RESPECT);
		habbo.getSettings().updateRespect(0, +1);

		connection.getHabbo().getCurrentRoom().getRoomUnitsHandler().send(new RoomUserRespectedComposer(habbo.getId(), habbo.getSettings().getRespectsReceived()));

		connection.getHabbo().getAchievements().unlockAchievement("ACH_RespectGiven", 1);
		habbo.getAchievements().unlockAchievement("ACH_RespectEarned", 1);
	}

}
