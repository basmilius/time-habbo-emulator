package com.basmilius.time.habbohotel.rooms.competition;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.util.ObjectUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomCompetitionManager extends IManager
{

	private List<RoomCompetition> competitions;

	public RoomCompetitionManager()
	{
		this.competitions = new ArrayList<>();
	}

	@Override
	public void initialize() throws Exception
	{
		this.competitions.clear();

		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT id,enabled,name,required_items FROM room_competition");
		{
			ResultSet result = statement.executeQuery();

			while (result.next())
			{
				this.competitions.add(new RoomCompetition(result));
			}
		}
	}

	public RoomCompetition getRoomCompetition(String name)
	{
		for (RoomCompetition competition : this.competitions)
			if (competition.getName().equalsIgnoreCase(name))
				return competition;

		return null;
	}

	public RoomCompetition getRandomEnabledRoomCompetition()
	{
		List<RoomCompetition> competitions = this.competitions.stream().filter(RoomCompetition::isEnabled).collect(Collectors.toList());

		if (competitions.size() > 0)
			return ObjectUtils.getRandomObject(competitions);

		return null;
	}

	@Override
	public void dispose()
	{
		this.competitions.clear();
	}

}
