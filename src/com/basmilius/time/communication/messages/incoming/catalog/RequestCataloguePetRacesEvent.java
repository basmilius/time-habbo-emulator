package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.pets.race.PetRace;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.CataloguePetRacesComposer;

import java.util.List;

@Event(id = Incoming.RequestCataloguePetRaces)
public class RequestCataloguePetRacesEvent extends MessageEvent
{

	@Override
	public final void handle()
	{
		final String pet = packet.readString();
		final List<PetRace> races = Bootstrap.getEngine().getGame().getPetManager().getRacesForPet(pet);
		
		if (races == null)
			return;
		
		connection.send(new CataloguePetRacesComposer(pet, races));
	}

}
