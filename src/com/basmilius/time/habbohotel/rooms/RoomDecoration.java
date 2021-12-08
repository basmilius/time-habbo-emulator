package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RoomDecoration
{
	
	private final Room room;

	private String wallpaper;
	private String floorpaper;
	private double landscape;

	private int floorThickness;
	private boolean isWallHidden;
	private int wallHeight;
	private int wallThickness;

	public RoomDecoration(final Room room, final ResultSet result) throws SQLException
	{
		this.room = room;
		
		this.wallpaper = result.getString("paper_wall");
		this.floorpaper = result.getString("paper_floor");
		this.landscape = result.getDouble("paper_landscape");

		this.floorThickness = result.getInt("thickness_floor");
		this.isWallHidden = result.getString("allow_hidewall").equals("1");
		this.wallHeight = result.getInt("height_wall");
		this.wallThickness = result.getInt("thickness_wall");
	}

	public final String getWallpaper()
	{
		return this.wallpaper;
	}

	public final String getFloorpaper()
	{
		return this.floorpaper;
	}

	public final double getLandscape()
	{
		return this.landscape;
	}

	public final int getFloorThickness()
	{
		return this.floorThickness;
	}
	
	public final boolean isWallHidden()
	{
		return this.isWallHidden;
	}

	public final int getWallHeight()
	{
		return this.wallHeight;
	}

	public final int getWallThickness()
	{
		return this.wallThickness;
	}

	public final void setWallpaper(final String wallpaper)
	{
		this.wallpaper = wallpaper;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setFloorpaper(final String floorpaper)
	{
		this.floorpaper = floorpaper;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setLandscape(final double landscape)
	{
		this.landscape = landscape;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setFloorThickness(final int floorThickness)
	{
		this.floorThickness = floorThickness;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setIsWallHidden(final boolean isWallHidden)
	{
		this.isWallHidden = isWallHidden;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setWallHeight(final int wallHeight)
	{
		this.wallHeight = wallHeight;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setWallThickness(final int wallThickness)
	{
		this.wallThickness = wallThickness;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}
	
}