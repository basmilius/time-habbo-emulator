package com.basmilius.time.habbohotel.pets;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomPet;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.outgoing.rooms.pets.PetsExperienceReceivedComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.pets.PetsScratchedComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.users.RoomUserIdleComposer;
import com.basmilius.time.util.TimeUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Pet
{

	private final int id;
	private final Habbo habbo;
	private final Room room;
	private final String name;
	private final int type;
	private final int race;
	private final String color;
	private int energy;
	private int experience;
	private int nutrition;
	private int scratched;
	private int rarity;
	private final int createdOn;
	private int lastHealthOn;
	private int x;
	private int y;
	private double z;
	private int rotation;

	protected Pet(final ResultSet result) throws SQLException
	{
		this.id = result.getInt("id");
		this.habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this.room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(result.getInt("room_id"));
		this.name = result.getString("name");
		this.type = result.getInt("type");
		this.race = result.getInt("race");
		this.color = result.getString("color");
		this.energy = result.getInt("energy");
		this.experience = result.getInt("experience");
		this.nutrition = result.getInt("nutrition");
		this.scratched = result.getInt("scratched");
		this.rarity = result.getInt("rarity");
		this.createdOn = result.getInt("created_on");
		this.lastHealthOn = result.getInt("last_health_on");
		this.x = result.getInt("x");
		this.y = result.getInt("y");
		this.z = result.getDouble("z");
		this.rotation = result.getInt("rotation");
	}
	
	public abstract String getLook();

	public final int getId()
	{
		return this.id;
	}

	public final Habbo getHabbo()
	{
		return this.habbo;
	}

	public final Room getRoom()
	{
		return this.room;
	}

	public final String getName()
	{
		return this.name;
	}

	public final int getType()
	{
		return this.type;
	}

	public final int getRace()
	{
		return this.race;
	}

	public final String getColor()
	{
		return this.color;
	}

	public final int getEnergy()
	{
		return this.energy;
	}

	public final int getExperience()
	{
		return this.experience;
	}

	public final int getNutrition()
	{
		return this.nutrition;
	}

	public final int getScratched()
	{
		return this.scratched;
	}

	public final int getRarity()
	{
		return this.rarity;
	}

	public final int getCreatedOn()
	{
		return this.createdOn;
	}

	public final int getLastHealthOn()
	{
		return this.lastHealthOn;
	}

	public final int getX()
	{
		return this.x;
	}

	public final int getY()
	{
		return this.y;
	}

	public final double getZ()
	{
		return this.z;
	}

	public final int getRotation()
	{
		return this.rotation;
	}
	
	public final int getAge()
	{
		final Calendar birthDate = new GregorianCalendar();
		final Calendar nowDate = new GregorianCalendar();
		
		birthDate.setTimeInMillis(this.getCreatedOn() * 1000);
		nowDate.setTimeInMillis(TimeUtils.getUnixTimestamp() * 1000);
		
		return (int)Math.ceil((nowDate.getTime().getTime() - birthDate.getTime().getTime()) / (((1000 * 60) * 60) * 24));
	}
	
	public final int getLevel()
	{
		for (int i = 0; i < Bootstrap.getEngine().getGame().getPetManager().getExperienceGoals().length; i++)
		{
			if (this.experience < Bootstrap.getEngine().getGame().getPetManager().getExperienceGoals()[i])
			{
				return (i + 1);
			}
		}
		return (Bootstrap.getEngine().getGame().getPetManager().getExperienceGoals().length + 1);
	}
	
	public final RoomPet getRoomPet()
	{
		if (this.room != null)
		{
			final List<RoomPet> pets = this.room.getRoomUnitsHandler().getPets().stream().filter(r -> r.getPet().getId() == this.getId()).collect(Collectors.toList());
			if (pets.size() > 0)
			{
				return pets.get(0);
			}
		}
		
		return null;
	}
	
	public final void experience(final int amount)
	{
		if (this.getRoomPet() == null)
			return;
		
		final int oldExperience = this.experience;
		this.experience += amount;
		
		if (this.experience >= Bootstrap.getEngine().getGame().getPetManager().getExperienceGoals()[Bootstrap.getEngine().getGame().getPetManager().getExperienceGoals().length - 1])
			this.experience = Bootstrap.getEngine().getGame().getPetManager().getExperienceGoals()[Bootstrap.getEngine().getGame().getPetManager().getExperienceGoals().length - 1];
		else if (this.experience <= 0)
			this.experience = 0;
		
		this.room.getRoomUnitsHandler().send(new PetsExperienceReceivedComposer(this.getId(), this.getRoomPet().getUnitId(), amount));
		
		if (this.experience < oldExperience)
			return;
		/*
		 * TODO: Notify the owner if this pet receives a new level.
		 * TODO: Update the trainer panel for the owner.
		 * TODO: Remove underliyng line.
		 */
		this.experience++;
	}
	
	public final void scratch(final Habbo habbo)
	{
		if (this.room != null && this.getRoomPet() != null)
		{
			this.scratched++;
			this.experience(10);
			
			habbo.getSettings().updateDailyRespectPoints(0, -1);
			
			final QueuedComposers composers = new QueuedComposers();
			
			composers.appendComposer(new RoomUserIdleComposer(this.getRoomPet().getUnitId(), true));
			composers.appendComposer(new PetsScratchedComposer(1, this.getRoomPet()));
			
			room.getRoomUnitsHandler().send(composers);
		}
	}
	
}