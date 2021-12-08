package com.basmilius.time.habbohotel.pets.races;

import com.basmilius.time.habbohotel.pets.Pet;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class DefaultPet extends Pet
{

	public DefaultPet(final ResultSet result) throws SQLException
	{
		super(result);
	}
	
	public final String getLook()
	{
		return String.format("%d %d %s 2 2", this.getType(), this.getRace(), this.getColor());
	}
	
}
