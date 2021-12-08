package com.basmilius.time.communication.messages.outgoing.catalogue;

import com.basmilius.time.habbohotel.pets.race.PetRace;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class CataloguePetRacesComposer extends MessageComposer
{
	
	private final String petType;
	private final List<PetRace> races;
	
	public CataloguePetRacesComposer(final String petType, final List<PetRace> races)
	{
		this.petType = petType;
		this.races = races;
	}
	
	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.CataloguePetRaces);
		response.appendString(this.petType);
		response.appendInt(this.races.size());
		for (final PetRace race : this.races)
		{
			response.appendInt(race.getPetId());
			response.appendInt(race.getColorOne());
			response.appendInt(race.getColorTwo());
			response.appendBoolean(race.isColorOneEnabled());
			response.appendBoolean(race.isColorTwoEnabled());
		}
		return response;
	}

}
