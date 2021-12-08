package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.communication.messages.outgoing.guilds.GuildBadgesComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomSettingsEnforceNewCategoryComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomUserComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.users.RemoveRoomUserComposer;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public final class RoomUnitsHandler
{

	private final Room room;

	private final List<RoomUnit> units;
	private final Queue<RoomUnit> unitsToUpdate;
	private int unitCounter;
	
	public RoomUnitsHandler(final Room room)
	{
		this.room = room;
		
		this.units = Lists.newLinkedList();
		this.unitsToUpdate = Queues.newConcurrentLinkedQueue();
	}
	
	public final void addRoomUnit (final RoomUnit unit, final boolean hasInitialNode)
	{
		this.units.add(unit);
		
		if (RoomUser.class.isInstance(unit) && ((RoomUser) unit).isSpectator())
			return;
		
		if (this.room.getRoomData().getCategory() == -1 && RoomUser.class.isInstance(unit) && this.room.getRoomData().getPermissions().isOwner(((RoomUser) unit).getHabbo()))
			((RoomUser) unit).getConnection().send(new RoomSettingsEnforceNewCategoryComposer());
		
		unit.setUnitId(++this.unitCounter);
		
		if (!hasInitialNode)
		{
			unit.setPosition(this.room.getRoomData().getRoomModel().getDoorNode());
			unit.setHeight(this.room.getRoomData().getRoomModel().getDoorZ());
			unit.setRotation(this.room.getRoomData().getRoomModel().getDoorRotation());
		}
		
		final QueuedComposers composers = new QueuedComposers();
		composers.appendComposer(new RoomUserComposer(unit));
		composers.appendComposer(new GuildBadgesComposer(this.room));
		this.send(composers);
		
		unit.updateStatus();
	}
	
	public final boolean canWalk(final Node node)
	{
		return this.room.getGameMap().getWalkable(node.getX(), node.getY());
	}
	
	public final boolean isFull()
	{
		return this.room.getRoomData().getUsersLimit() <= this.getUsers().size();
	}
	
	public final void removeRoomUnit(final RoomUnit unit)
	{
		if (unit == null || !this.getUnits().contains(unit))
			return;
		
		this.units.remove(unit);
		this.send(new RemoveRoomUserComposer(unit.getUnitId()));
		
		if (RoomBot.class.isInstance(unit))
		{
			((RoomBot) unit).dispose();
			((RoomBot) unit).getBot().dispose();
		}
		
		if (this.getUsersAndSpectators().size() == 0)
		{
			this.room.unload(false);
		}
	}
	
	public final List<RoomBot> getBots()
	{
		return this.getUnits().stream().filter(RoomBot.class::isInstance).collect(Collectors.toList()).stream().map(u -> (RoomBot) u).collect(Collectors.toList());
	}

	public final List<RoomPet> getPets()
	{
		return this.getUnits().stream().filter(RoomPet.class::isInstance).collect(Collectors.toList()).stream().map(u -> (RoomPet) u).collect(Collectors.toList());
	}

	public final List<RoomUser> getUsers()
	{
		return this.getUnits().stream().filter(RoomUser.class::isInstance).filter(u -> !((RoomUser) u).isSpectator()).collect(Collectors.toList()).stream().map(u -> (RoomUser) u).collect(Collectors.toList());
	}
	
	public final List<RoomUser> getUsersAndSpectators()
	{
		return this.getUnits().stream().filter(RoomUser.class::isInstance).collect(Collectors.toList()).stream().map(u -> (RoomUser) u).collect(Collectors.toList());
	}
	
	public final List<RoomUnit> getUnits()
	{
		return this.units;
	}
	
	public final Queue<RoomUnit> getUnitsToUpdate()
	{
		return this.unitsToUpdate;
	}
	
	public final RoomUnit getUnit(final int unitId)
	{
		final List<RoomUnit> units = this.getUnits().stream().filter(unit -> unit.getUnitId() == unitId).collect(Collectors.toList());
		if (units.size() > 0)
			return units.get(0);
		return null;
	}
	
	public final RoomUnit getUnitAt(final Node node)
	{
		synchronized (this.units)
		{
			final List<RoomUnit> units = this.units.stream().filter(u -> u.getNode().equals(node)).collect(Collectors.toList());
			if (units.size() > 0)
				return units.get(0);
			return null;
		}
	}
	
	public final void send(final MessageComposer composer)
	{
		this.send(composer, false);
	}
	
	public final void send(final MessageComposer composer, final boolean rightsRequired)
	{
		for (final RoomUser user : this.getUsersAndSpectators().stream().filter(u -> this.room.getRoomData().getPermissions().hasRights(u.getHabbo()) || !rightsRequired).collect(Collectors.toList()))
		{
			try
			{
				user.getConnection().send(composer);
			}
			catch(final Exception e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		}
	}
	
	public final void send(final QueuedComposers composers)
	{
		this.send(composers, false);
	}
	
	public final void send(final QueuedComposers composers, final boolean rightsRequired)
	{
		for (final RoomUser user : this.getUsersAndSpectators().stream().filter(u -> this.room.getRoomData().getPermissions().hasRights(u.getHabbo()) || !rightsRequired).collect(Collectors.toList()))
		{
			try
			{
				user.getConnection().send(composers);
			}
			catch(final Exception e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		}
	}
	
}
