package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.habbohotel.habbo.inventory.HabboEffect;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;

@Event(id = Incoming.ActivateEffect)
public class ActivateEffectEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		int effectId = packet.readInt();
		HabboEffect effect = connection.getHabbo().getInventory().getEffectsInventory().getEffect(effectId);

		effect.activate();
	}

}
