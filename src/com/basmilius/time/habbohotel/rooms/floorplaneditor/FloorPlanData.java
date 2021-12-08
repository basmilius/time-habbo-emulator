package com.basmilius.time.habbohotel.rooms.floorplaneditor;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FloorPlanData
{

	private final Room room;
	
	private String floorPlan;
	private Node doorNode;
	private int doorRotation;

	public FloorPlanData(final Room room, final ResultSet result) throws SQLException
	{
		this.room = room;
		
		this.floorPlan = result.getString("floorplan_map");
		this.doorNode = new Node(result.getInt("floorplan_door_x"), result.getInt("floorplan_door_y"), (int) result.getDouble("floorplan_door_z"));
		this.doorRotation = result.getInt("floorplan_door_rotation");
	}

	public FloorPlanData(final Room room, final String floorPlan, final Node doorNode, final int doorRotation)
	{
		this.room = room;
		
		this.floorPlan = floorPlan;
		this.doorNode = doorNode;
		this.doorRotation = doorRotation;
	}

	public String getFloorPlan()
	{
		return floorPlan.toLowerCase();
	}

	public Node getDoorNode()
	{
		return doorNode;
	}

	public int getDoorRotation()
	{
		return doorRotation;
	}

	public final void setFloorPlan(final String floorPlan)
	{
		this.floorPlan = floorPlan;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setDoorNode(final Node doorNode)
	{
		this.doorNode = doorNode;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setDoorRotation(final int doorRotation)
	{
		this.doorRotation = doorRotation;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}
}
