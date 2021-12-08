package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class RoomPermissions
{
	
	private final Room room;
	
	private boolean isPublicRoom;
	private boolean isOtherPetsAllowed;
	private boolean isOtherPetsEatingAllowed;
	private boolean isWalkthroughAllowed;
	
	private int muteAccessLevel;
	private int kickAccessLevel;
	private int banAccessLevel;
	
	private final List<Habbo> controllers;

	public RoomPermissions(final Room room, final ResultSet result) throws SQLException
	{
		this.room = room;
		
		this.isPublicRoom = result.getString("is_public").equals("1");
		this.isOtherPetsAllowed = result.getString("allow_other_pets").equals("1");
		this.isOtherPetsEatingAllowed = result.getString("allow_other_pets_eat").equals("1");
		this.isWalkthroughAllowed = result.getString("allow_walkthrough").equals("1");

		this.muteAccessLevel = result.getInt("who_can_mute");
		this.kickAccessLevel = result.getInt("who_can_kick");
		this.banAccessLevel = result.getInt("who_can_ban");
		
		this.controllers = Lists.newLinkedList();
	}

	public final boolean isPublicRoom()
	{
		return this.isPublicRoom;
	}

	public final boolean isOtherPetsAllowed()
	{
		return this.isOtherPetsAllowed;
	}

	public final boolean isOtherPetsEatingAllowed()
	{
		return this.isOtherPetsEatingAllowed;
	}

	public final boolean isWalkthroughAllowed()
	{
		return this.isWalkthroughAllowed;
	}

	public final int getMuteAccessLevel()
	{
		return this.muteAccessLevel;
	}

	public final int getKickAccessLevel()
	{
		return this.kickAccessLevel;
	}

	public final int getBanAccessLevel()
	{
		return this.banAccessLevel;
	}
	
	public final List<Habbo> getControllers()
	{
		return this.controllers;
	}
	
	public final boolean addController(final Habbo habbo)
	{
		this.controllers.add(habbo);
		return true;
	}

	public final boolean removeController(final Habbo habbo)
	{
		this.controllers.remove(habbo);
		return true;
	}

	public final boolean removeControllers()
	{
		this.controllers.clear();
		return true;
	}
	
	public final boolean hasRights(final Habbo habbo)
	{
		if (habbo == null || habbo.getPermissions() == null)
			return false;
		
		/**
		 * TODO Make this better.
		 */
		return this.isOwner(habbo) || this.controllers.contains(habbo) || habbo.getPermissions().contains("acc_anyroomowner");
	}
	
	public final boolean isOwner(final Habbo habbo)
	{
		return this.room.getRoomData().getOwner() == habbo;
	}

	public final void setIsOtherPetsAllowed(final boolean isOtherPetsAllowed)
	{
		this.isOtherPetsAllowed = isOtherPetsAllowed;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setIsWalkthroughAllowed(final boolean isWalkthroughAllowed)
	{
		this.isWalkthroughAllowed = isWalkthroughAllowed;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setIsOtherPetsEatingAllowed(final boolean isOtherPetsEatingAllowed)
	{
		this.isOtherPetsEatingAllowed = isOtherPetsEatingAllowed;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setIsPublicRoom(final boolean isPublicRoom)
	{
		this.isPublicRoom = isPublicRoom;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setMuteAccessLevel(final int muteAccessLevel)
	{
		this.muteAccessLevel = muteAccessLevel;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setKickAccessLevel(final int kickAccessLevel)
	{
		this.kickAccessLevel = kickAccessLevel;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}

	public final void setBanAccessLevel(final int banAccessLevel)
	{
		this.banAccessLevel = banAccessLevel;
		Bootstrap.getEngine().getGame().getRoomManager().getRoomsSaver().addRoom(this.room);
	}
}
