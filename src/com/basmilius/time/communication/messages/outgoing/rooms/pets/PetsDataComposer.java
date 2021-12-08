package com.basmilius.time.communication.messages.outgoing.rooms.pets;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.pets.Pet;
import com.basmilius.time.habbohotel.pets.races.HorsePet;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class PetsDataComposer extends MessageComposer
{

	private final Pet pet;
	
	public PetsDataComposer(final Pet pet)
	{
		this.pet = pet;
	}

	@Override
	public final ServerMessage compose()
	{
		response.init(Outgoing.PetsData);
		response.appendInt(this.pet.getId());
		response.appendString(this.pet.getName());
		response.appendInt(this.pet.getLevel());
		response.appendInt(Bootstrap.getEngine().getGame().getPetManager().getExperienceGoals().length + 1);
		response.appendInt(this.pet.getExperience());
		response.appendInt(Bootstrap.getEngine().getGame().getPetManager().getExperienceGoals()[this.pet.getLevel() - 1]);
		response.appendInt(this.pet.getEnergy());
		response.appendInt(100); // Max Energy
		response.appendInt(this.pet.getNutrition());
		response.appendInt(150); // Max Nutrition
		response.appendInt(this.pet.getScratched());
		response.appendInt(this.pet.getHabbo().getId());
		response.appendInt(this.pet.getAge());
		response.appendString(this.pet.getHabbo().getUsername());
		response.appendInt(this.pet.getType());
		response.appendBoolean(HorsePet.class.isInstance(this.pet) && ((HorsePet) this.pet).getSaddleId() > 0);
		response.appendBoolean(false); // HORSE: Someone is riding this pet.
		response.appendInt(0);
		response.appendInt(HorsePet.class.isInstance(this.pet) ? (((HorsePet) this.pet).isAnyoneCanRide() ? 1 : 0) : 0);
		response.appendBoolean(false); // MONSTERPLANT: Grows
		response.appendBoolean(false); // ??
		response.appendBoolean(false); // MONSTERPLANT: Dead
		response.appendInt(this.pet.getRarity());
		response.appendInt(-1);
		response.appendInt(-1);
		response.appendInt(-1);
		response.appendBoolean(true); // ??
		return response;
	}

}
