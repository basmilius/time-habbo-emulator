package com.basmilius.time.communication.messages.outgoing.rooms.users;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ApplyEffectComposer extends MessageComposer
{

	private final RoomUnit unit;
	private final int effectId;

	public ApplyEffectComposer(RoomUnit unit, Integer effectId)
	{
		this.unit = unit;
		this.effectId = effectId;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.ApplyEffect);
		response.appendInt(this.unit.getUnitId());
		response.appendInt(this.effectId);
		response.appendInt(0);
		return response;
	}

}
