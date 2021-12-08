package com.basmilius.time.habbohotel.pets.races;

import com.basmilius.time.habbohotel.pets.Pet;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class HorsePet extends Pet
{

	private int saddleId;
	private int hair;
	private int hairDye;
	private boolean anyoneCanRide;

	public HorsePet(final ResultSet result) throws SQLException
	{
		super(result);
		
		this.saddleId = result.getInt("saddle_id");
		this.hair = result.getInt("hair");
		this.hairDye = result.getInt("hair_dye");
		this.anyoneCanRide = result.getString("anyone_can_ride").equals("1");
	}
	
	public final String getLook()
	{
		final StringBuilder builder = new StringBuilder();
		
		builder.append(String.format("%d %d %s ", this.getType(), this.getRace(), this.getColor()));
		if (this.saddleId > 0)
		{
			builder.append("3 4 10 0 2 ");
		}
		else
		{
			builder.append("3 2 ");
		}
		builder.append(String.format("%1$d %2$d %1$d %2$d ", this.hair, this.hairDye));
		
		return builder.toString();
	}

	public final int getSaddleId()
	{
		return this.saddleId;
	}

	public final int getHair()
	{
		return this.hair;
	}

	public final int getHairDye()
	{
		return this.hairDye;
	}

	public final boolean isAnyoneCanRide()
	{
		return this.anyoneCanRide;
	}
}
