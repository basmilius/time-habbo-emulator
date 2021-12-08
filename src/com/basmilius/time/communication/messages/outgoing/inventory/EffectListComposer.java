package com.basmilius.time.communication.messages.outgoing.inventory;

import com.basmilius.time.habbohotel.habbo.inventory.HabboEffect;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class EffectListComposer extends MessageComposer
{

	private final List<HabboEffect> effects;

	public EffectListComposer(List<HabboEffect> effects)
	{
		this.effects = effects;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.EffectList);
		response.appendInt(this.effects.size());
		for (HabboEffect effect : this.effects)
		{
			response.appendInt(effect.getEffectId());
			response.appendInt(1); // TODO: 1 = costume, 0 = magic wand
			response.appendInt(effect.getTimeTotal());
			response.appendInt(effect.getCount() - 1);
			response.appendInt((effect.getIsActivated()) ? effect.getTimeLeft() : -1);
			response.appendBoolean(false);
		}
		return response;
	}

}
