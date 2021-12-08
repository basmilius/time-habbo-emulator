package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.habbohotel.habbo.inventory.HabboEffect;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.ApplyEffect)
public class ApplyEffectEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int effectId = packet.readInt();
		HabboEffect effect = connection.getHabbo().getInventory().getEffectsInventory().getEffect(effectId);

		if (!connection.getHabbo().isInRoom() || !(effectId <= 0 || (effect != null && effect.getTimeLeft() > 0)))
			return; // User isn't in a room

		RoomUser roomUser = connection.getHabbo().getRoomUser();

		if (roomUser == null)
			return;

		roomUser.applyEffect(effectId);
	}

}
