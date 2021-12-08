package com.basmilius.time.communication.messages.outgoing.rooms.pets;

import com.basmilius.time.habbohotel.rooms.RoomPet;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PetsScratchedComposer extends MessageComposer
{

	private final int scratched;
	private final RoomPet roomPet;
	
	public PetsScratchedComposer(final int scratched, final RoomPet roomPet)
	{
		this.scratched = scratched;
		this.roomPet = roomPet;
	}

	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.PetsScratched);
		response.appendInt(this.scratched);
		response.appendInt(this.roomPet.getUnitId());
		response.appendInt(this.roomPet.getPet().getId());
		response.appendString(this.roomPet.getPet().getName());
		response.appendInt(this.roomPet.getPet().getType());
		response.appendInt(this.roomPet.getPet().getRace());
		response.appendString(this.roomPet.getPet().getColor());
		response.appendInt(this.roomPet.getPet().getRarity());
		response.appendInt(0);
		response.appendInt(0);
		return response;
	}

}
