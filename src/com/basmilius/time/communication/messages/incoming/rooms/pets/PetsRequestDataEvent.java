package com.basmilius.time.communication.messages.incoming.rooms.pets;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.pets.Pet;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.pets.PetsDataComposer;

@Event(id = Incoming.PetsRequestData)
public class PetsRequestDataEvent extends MessageEvent
{

	@Override
	public final void handle()
	{
		final int petId = packet.readInt();
		final Pet pet = Bootstrap.getEngine().getGame().getPetManager().getPet(petId);
		
		if (pet == null)
			return;
		
		connection.send(new PetsDataComposer(pet));
	}

}
