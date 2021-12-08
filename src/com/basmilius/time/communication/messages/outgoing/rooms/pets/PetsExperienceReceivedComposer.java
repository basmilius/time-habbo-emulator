package com.basmilius.time.communication.messages.outgoing.rooms.pets;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PetsExperienceReceivedComposer extends MessageComposer
{

	private final int petId;
	private final int unitId;
	private final int amount;
	
	public PetsExperienceReceivedComposer(final int petId, final int unitId, final int amount)
	{
		this.petId = petId;
		this.unitId = unitId;
		this.amount = amount;
	}

	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.PetsExperienceReceived);
		response.appendInt(this.petId);
		response.appendInt(this.unitId);
		response.appendInt(this.amount);
		return response;
	}

}
