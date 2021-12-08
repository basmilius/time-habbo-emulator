package com.basmilius.time.habbohotel.pets;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.pets.race.PetRace;
import com.basmilius.time.habbohotel.pets.races.DefaultPet;
import com.basmilius.time.habbohotel.pets.races.HorsePet;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.util.ObjectUtils;
import com.google.common.collect.Lists;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetManager extends IManager
{

	private final List<Pet> pets; 
	private final Map<String, List<PetRace>> races;

	public PetManager()
	{
		this.pets = Lists.newLinkedList();
		
		this.races = new HashMap<>();
		this.races.put("a0 pet0", Lists.newLinkedList());
		this.races.put("a0 pet1", Lists.newLinkedList());
		this.races.put("a0 pet2", Lists.newLinkedList());
		this.races.put("a0 pet3", Lists.newLinkedList());
		this.races.put("a0 pet4", Lists.newLinkedList());
		this.races.put("a0 pet5", Lists.newLinkedList());
		this.races.put("a0 pet6", Lists.newLinkedList());
		this.races.put("a0 pet7", Lists.newLinkedList());
		this.races.put("a0 pet8", Lists.newLinkedList());
		this.races.put("a0 pet9", Lists.newLinkedList());
		this.races.put("a0 pet10", Lists.newLinkedList());
		this.races.put("a0 pet11", Lists.newLinkedList());
		this.races.put("a0 pet12", Lists.newLinkedList());
		this.races.put("a0 pet14", Lists.newLinkedList());
		this.races.put("a0 pet15", Lists.newLinkedList());
		this.races.put("a0 pet17", Lists.newLinkedList());
		this.races.put("a0 pet18", Lists.newLinkedList());
		this.races.put("a0 pet19", Lists.newLinkedList());
		this.races.put("a0 pet20", Lists.newLinkedList());
		this.races.put("a0 pet21", Lists.newLinkedList());
		this.races.put("a0 pet22", Lists.newLinkedList());
		this.races.put("a0 pet23", Lists.newLinkedList());
		this.races.put("a0 pet24", Lists.newLinkedList());
		this.races.put("a0 pet25", Lists.newLinkedList());
		this.races.put("a0 pet26", Lists.newLinkedList());

		this.races.get("a0 pet0").add(new PetRace(0, 0, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 1, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 2, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 3, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 4, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 5, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 6, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 7, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 8, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 9, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 11, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 12, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 13, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 14, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 15, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 16, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 17, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 18, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 19, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 20, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 21, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 22, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 23, 0, true, false));
		this.races.get("a0 pet0").add(new PetRace(0, 24, 0, true, false));

		this.races.get("a0 pet1").add(new PetRace(1, 0, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 1, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 2, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 3, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 4, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 5, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 6, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 7, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 8, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 9, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 11, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 12, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 13, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 14, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 15, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 16, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 17, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 18, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 19, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 20, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 21, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 22, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 23, 0, true, false));
		this.races.get("a0 pet1").add(new PetRace(1, 24, 0, true, false));

		this.races.get("a0 pet2").add(new PetRace(2, 0, 0, true, false));
		this.races.get("a0 pet2").add(new PetRace(2, 1, 0, true, false));
		this.races.get("a0 pet2").add(new PetRace(2, 2, 0, true, false));
		this.races.get("a0 pet2").add(new PetRace(2, 3, 0, true, false));
		this.races.get("a0 pet2").add(new PetRace(2, 4, 0, true, false));
		this.races.get("a0 pet2").add(new PetRace(2, 5, 0, true, false));
		this.races.get("a0 pet2").add(new PetRace(2, 6, 0, true, false));
		this.races.get("a0 pet2").add(new PetRace(2, 7, 0, true, false));
		this.races.get("a0 pet2").add(new PetRace(2, 8, 0, true, false));
		this.races.get("a0 pet2").add(new PetRace(2, 9, 0, true, false));
		this.races.get("a0 pet2").add(new PetRace(2, 11, 0, true, false));

		this.races.get("a0 pet3").add(new PetRace(3, 0, 0, true, false));
		this.races.get("a0 pet3").add(new PetRace(3, 1, 0, true, false));
		this.races.get("a0 pet3").add(new PetRace(3, 2, 0, true, false));
		this.races.get("a0 pet3").add(new PetRace(3, 3, 0, true, false));
		this.races.get("a0 pet3").add(new PetRace(3, 4, 0, true, false));
		this.races.get("a0 pet3").add(new PetRace(3, 5, 0, true, false));
		this.races.get("a0 pet3").add(new PetRace(3, 6, 0, true, false));

		this.races.get("a0 pet4").add(new PetRace(4, 0, 0, true, false));
		this.races.get("a0 pet4").add(new PetRace(4, 1, 0, true, false));
		this.races.get("a0 pet4").add(new PetRace(4, 2, 0, true, false));
		this.races.get("a0 pet4").add(new PetRace(4, 3, 0, true, false));

		this.races.get("a0 pet5").add(new PetRace(5, 0, 0, true, false));
		this.races.get("a0 pet5").add(new PetRace(5, 1, 0, true, false));
		this.races.get("a0 pet5").add(new PetRace(5, 2, 0, true, false));
		this.races.get("a0 pet5").add(new PetRace(5, 3, 0, true, false));
		this.races.get("a0 pet5").add(new PetRace(5, 4, 0, true, false));
		this.races.get("a0 pet5").add(new PetRace(5, 5, 0, true, false));
		this.races.get("a0 pet5").add(new PetRace(5, 6, 0, true, false));
		this.races.get("a0 pet5").add(new PetRace(5, 7, 0, true, false));
		this.races.get("a0 pet5").add(new PetRace(5, 8, 0, true, false));

		this.races.get("a0 pet6").add(new PetRace(6, 0, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 1, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 2, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 3, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 4, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 5, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 6, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 7, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 8, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 9, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 11, 0, true, false));
		this.races.get("a0 pet6").add(new PetRace(6, 12, 0, true, false));

		this.races.get("a0 pet7").add(new PetRace(7, 0, 0, true, false));
		this.races.get("a0 pet7").add(new PetRace(7, 1, 0, true, false));
		this.races.get("a0 pet7").add(new PetRace(7, 2, 0, true, false));
		this.races.get("a0 pet7").add(new PetRace(7, 0, 3, false, true));
		this.races.get("a0 pet7").add(new PetRace(7, 4, 0, true, false));
		this.races.get("a0 pet7").add(new PetRace(7, 5, 0, true, false));
		this.races.get("a0 pet7").add(new PetRace(7, 6, 0, true, false));
		this.races.get("a0 pet7").add(new PetRace(7, 7, 0, true, false));

		this.races.get("a0 pet8").add(new PetRace(8, 0, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 1, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 2, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 0, 3, false, true));
		this.races.get("a0 pet8").add(new PetRace(8, 4, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 5, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 6, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 7, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 8, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 9, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 10, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 11, 0, true, false));
		this.races.get("a0 pet8").add(new PetRace(8, 14, 0, true, false));

		this.races.get("a0 pet9").add(new PetRace(9, 0, 0, true, false));
		this.races.get("a0 pet9").add(new PetRace(9, 1, 0, true, false));
		this.races.get("a0 pet9").add(new PetRace(9, 2, 0, true, false));
		this.races.get("a0 pet9").add(new PetRace(9, 3, 0, true, false));
		this.races.get("a0 pet9").add(new PetRace(9, 4, 0, true, false));
		this.races.get("a0 pet9").add(new PetRace(9, 5, 0, true, false));
		this.races.get("a0 pet9").add(new PetRace(9, 6, 0, true, false));
		this.races.get("a0 pet9").add(new PetRace(9, 7, 0, true, false));
		this.races.get("a0 pet9").add(new PetRace(9, 8, 0, true, false));
		
		this.races.get("a0 pet10").add(new PetRace(10, 0, 0, true, false));
		this.races.get("a0 pet10").add(new PetRace(10, 1, 0, true, false));
		this.races.get("a0 pet10").add(new PetRace(10, 2, 0, true, false));
		this.races.get("a0 pet10").add(new PetRace(10, 3, 0, true, false));
		this.races.get("a0 pet10").add(new PetRace(10, 4, 0, true, false));

		this.races.get("a0 pet11").add(new PetRace(11, 1, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 2, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 3, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 4, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 5, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 6, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 8, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 9, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 10, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 11, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 12, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 13, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 15, 0, true, false));
		this.races.get("a0 pet11").add(new PetRace(11, 18, 0, true, false));

		this.races.get("a0 pet12").add(new PetRace(12, 0, 0, true, false));
		this.races.get("a0 pet12").add(new PetRace(12, 1, 0, true, false));
		this.races.get("a0 pet12").add(new PetRace(12, 2, 0, true, false));
		this.races.get("a0 pet12").add(new PetRace(12, 3, 0, true, false));
		this.races.get("a0 pet12").add(new PetRace(12, 4, 0, true, false));
		this.races.get("a0 pet12").add(new PetRace(12, 5, 0, true, false));

		this.races.get("a0 pet14").add(new PetRace(14, 0, 0, true, false));
		this.races.get("a0 pet14").add(new PetRace(14, 1, 0, true, false));
		this.races.get("a0 pet14").add(new PetRace(14, 2, 0, true, false));
		this.races.get("a0 pet14").add(new PetRace(14, 3, 0, true, false));
		this.races.get("a0 pet14").add(new PetRace(14, 5, 0, true, false));
		this.races.get("a0 pet14").add(new PetRace(14, 6, 0, true, false));
		this.races.get("a0 pet14").add(new PetRace(14, 7, 0, true, false));
		this.races.get("a0 pet14").add(new PetRace(14, 8, 0, true, false));

		this.races.get("a0 pet15").add(new PetRace(15, 1, 0, true, false));
		this.races.get("a0 pet15").add(new PetRace(15, 2, 0, true, false));
		this.races.get("a0 pet15").add(new PetRace(15, 3, 0, true, false));
		this.races.get("a0 pet15").add(new PetRace(15, 5, 0, true, false));
		this.races.get("a0 pet15").add(new PetRace(15, 6, 0, true, false));
		this.races.get("a0 pet15").add(new PetRace(15, 7, 0, true, false));

		this.races.get("a0 pet17").add(new PetRace(17, 1, 0, true, false));
		this.races.get("a0 pet17").add(new PetRace(17, 2, 0, true, false));
		this.races.get("a0 pet17").add(new PetRace(17, 3, 0, true, false));
		this.races.get("a0 pet17").add(new PetRace(17, 4, 0, true, false));

		this.races.get("a0 pet18").add(new PetRace(18, 0, 0, true, false));

		this.races.get("a0 pet19").add(new PetRace(19, 0, 0, true, false));

		this.races.get("a0 pet20").add(new PetRace(20, 0, 0, true, false));

		this.races.get("a0 pet21").add(new PetRace(21, 0, 0, true, false));

		this.races.get("a0 pet22").add(new PetRace(22, 0, 0, true, false));
		
		this.races.get("a0 pet26").add(new PetRace(26, 0, 0, true, true));
		this.races.get("a0 pet26").add(new PetRace(26, 1, 1, true, false));
		this.races.get("a0 pet26").add(new PetRace(26, 2, 2, true, true));
		this.races.get("a0 pet26").add(new PetRace(26, 3, 3, true, true));
		this.races.get("a0 pet26").add(new PetRace(26, 4, 4, true, true));
		this.races.get("a0 pet26").add(new PetRace(26, 5, 5, true, true));
		this.races.get("a0 pet26").add(new PetRace(26, 6, 6, true, true));
		this.races.get("a0 pet26").add(new PetRace(26, 7, 7, true, true));
		this.races.get("a0 pet26").add(new PetRace(26, 8, 8, true, true));
		this.races.get("a0 pet26").add(new PetRace(26, 9, 9, true, true));
		this.races.get("a0 pet26").add(new PetRace(26, 10, 10, true, true));
		this.races.get("a0 pet26").add(new PetRace(26, 11, 11, true, true));
	}
	
	public final int[] getExperienceGoals()
	{
		return new int[]{100, 200, 400, 600, 1000, 1300, 1800, 2400, 3200, 4300, 7200, 8500, 10100, 13300, 17500, 23000, 51900};
	}
	
	public final Pet getPet(final int id)
	{
		if (this.hasPet(id))
			return (Pet) this.pets.stream().filter(p -> p.getId() == id).toArray()[0];
		return null;
	}

	public final int getPetIdFromPetString(final String pet)
	{
		final String petId = pet.replace("a0 pet", "");

		if (ObjectUtils.isNumeric(petId))
			return Integer.parseInt(petId);

		return 0;
	}
	
	public final List<PetRace> getRacesForPet(final String pet)
	{
		if (this.races.containsKey(pet))
			return this.races.get(pet);
		return null;
	}
	
	public final boolean hasPet(final int petId)
	{
		return this.pets.stream().filter(p -> p.getId() == petId).toArray().length > 0;
	}
	
	public final List<Pet> loadPetsForRoom(final Room room) throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM `pets` WHERE `room_id` = ?");
		{
			if (statement != null)
			{
				statement.setInt(1, room.getRoomData().getId());
				final ResultSet result = statement.executeQuery();
				
				while(result.next())
				{
					try
					{
						if (!this.hasPet(result.getInt("id")))
						{
							final Class<? extends Pet> petClass = this.getPetClass(result.getInt("type"));

							if(petClass == null)
								continue;

							final Pet pet = petClass.getConstructor(ResultSet.class).newInstance(result);
							this.pets.add(pet);
						}
					}
					catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e)
					{
						Bootstrap.getEngine().getLogging().handleException(e);
					}
				}
			}
		}
		
		return this.pets.stream().filter(p -> p.getRoom() == room).collect(Collectors.toList());
	}
	
	private Class<? extends Pet> getPetClass(final int type)
	{
		switch(type)
		{
			case 15:
				return HorsePet.class;
			default:
				return DefaultPet.class;
		}
	}

	/**
	 * Initializes this manager.
	 */
	@Override
	public final void initialize() throws Exception
	{

	}

	/**
	 * Disposes this manager.
	 */
	@Override
	public final void dispose()
	{

	}

}
