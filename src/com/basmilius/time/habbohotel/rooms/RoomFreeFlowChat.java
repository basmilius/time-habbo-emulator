package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RoomFreeFlowChat
{

	private final Room room;
	
	private int chatHearingDistance;
	private int chatMode;
	private int chatSpeed;
	private int chatWeight;
	private int floodControl;

	public RoomFreeFlowChat(final Room room, final ResultSet result) throws SQLException
	{
		this.room = room;
		
		this.chatHearingDistance = result.getInt("chat_hearing_distance");
		this.chatMode = result.getInt("chat_mode");
		this.chatSpeed = result.getInt("chat_speed");
		this.chatWeight = result.getInt("chat_weight");
		this.floodControl = result.getInt("anti_flood_control");
	}

	public final int getChatHearingDistance()
	{
		return this.chatHearingDistance;
	}

	public final int getChatMode()
	{
		return this.chatMode;
	}

	public final int getChatSpeed()
	{
		return this.chatSpeed;
	}

	public final int getChatWeight()
	{
		return this.chatWeight;
	}

	public final int getFloodControl()
	{
		return this.floodControl;
	}

	public final void setChatHearingDistance(final int chatHearingDistance)
	{
		this.chatHearingDistance = chatHearingDistance;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setChatMode(final int chatMode)
	{
		this.chatMode = chatMode;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setChatSpeed(final int chatSpeed)
	{
		this.chatSpeed = chatSpeed;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setChatWeight(final int chatWeight)
	{
		this.chatWeight = chatWeight;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setFloodControl(final int floodControl)
	{
		this.floodControl = floodControl;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}
}
