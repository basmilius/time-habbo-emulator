package com.basmilius.time.habbohotel.moderation.chatlog;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.util.TimeUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Chatlog implements ISerialize
{

	private int _id;
	private Habbo _habbo;
	private Habbo _habboTo;
	private Room _room;
	private int _time;
	private String _message;

	public Chatlog(ResultSet result) throws SQLException
	{
		this._id = result.getInt("id");
		this._habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_id"));
		this._habboTo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_to_id"));
		this._room = Bootstrap.getEngine().getGame().getRoomManager().getRoom(result.getInt("room_id"));
		this._time = result.getInt("timestamp");
		this._message = result.getString("message");
	}

	public Chatlog(Habbo habbo, Habbo habboTo, Room room, Integer time, String message)
	{
		this._id = -1;
		this._habbo = habbo;
		this._habboTo = habboTo;
		this._room = room;
		this._time = time;
		this._message = message;
	}

	public int getId()
	{
		return this._id;
	}

	public int getTime()
	{
		return this._time;
	}

	public int getTimeAgo()
	{
		return (TimeUtils.getUnixTimestamp() - this._time);
	}

	public Habbo getHabbo()
	{
		return this._habbo;
	}

	public Habbo getHabboTo()
	{
		return this._habboTo;
	}

	public Room getRoom()
	{
		return this._room;
	}

	public String getMessage()
	{
		return this._message;
	}

	@Override
	public void serialize(ServerMessage response)
	{
		response.appendInt(this._time);
		response.appendInt(this._habbo.getId());
		response.appendString(this._habbo.getUsername());
		response.appendString(this._message);
		response.appendBoolean(true);
	}

}
