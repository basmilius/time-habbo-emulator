package com.basmilius.time.habbohotel.rooms.cycles;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.event.RoomUnitWalkEventArgs;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.habbohotel.rooms.pathfinding.Pathfinder;
import com.basmilius.time.habbohotel.rooms.pathfinding.Rotation;
import com.basmilius.time.communication.messages.outgoing.rooms.RoomUsersStatusComposer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WalkCycle extends ICycle
{

	public WalkCycle(Room room)
	{
		super(room);
	}

	private Node calculateNewPath(RoomUnit roomUnit)
	{
		Pathfinder p = new Pathfinder(roomUnit.getRoom(), roomUnit);
		roomUnit.setPath(p.findPath());

		return roomUnit.getPath().poll();
	}

	@Override
	public void run()
	{
		while (this.getRoom().isActive())
		{
			if (!this.getRoom().isActive())
				break;

			try
			{
				final List<RoomUnit> unitsToUpdate = new ArrayList<>();
				final List<RoomUnit> units = new ArrayList<>(this.getRoomUnits());

				for (final RoomUnit user : units)
				{
					if (user.getIsWalking() && user.getPath() != null && user.getPath().size() > 0)
					{
						Node next = this.calculateNewPath(user);

						if (user.getStatuses().containsKey("mv"))
						{
							user.getStatuses().remove("mv");
						}

						if (user.getStatuses().containsKey("sit"))
						{
							user.getStatuses().remove("sit");
						}

						if (user.getStatuses().containsKey("lay"))
						{
							user.getStatuses().remove("lay");
						}

						if (next == null)
							if ((next = this.calculateNewPath(user)) == null)
							{
								user.setPath(new LinkedList<>());
								user.updateStatus();
								continue;
							}

						final int oldX = user.getX();
						final int oldY = user.getY();
						final double oldZ = user.getZ();
						final double newZ = this.getRoom().getRoomObjectsHandler().getStackHeight(next, null);

						int Rot = Rotation.calculate(user.getX(), user.getY(), next.getX(), next.getY());
						user.setRotation(Rot);

						user.getStatuses().put("mv", String.valueOf(next.getX()).concat(",").concat(String.valueOf(next.getY())).concat(",").concat(String.valueOf(newZ)));
						user.resetTimer();

						for (UserItem item : this.getRoom().getRoomObjectsHandler().getFloorItemsAt(user.getNode()))
						{
							user.setPos(next.getX(), next.getY());
							this.getRoom().getRoomInteractions().userWalkedOffItem(user, item);
							user.setPos(oldX, oldY);
						}

						user.setWalkingFromNode(new Node(oldX, oldY, oldZ));
						user.setPos(next.getX(), next.getY());

						for (UserItem item : getRoom().getRoomObjectsHandler().getFloorItemsAt(user.getNode()))
						{
							this.getRoom().getRoomInteractions().userWalkedOnItem(user, item);
						}

						user.dispatchEvent(RoomUnitWalkEventArgs.class, new RoomUnitWalkEventArgs(user.getX(), user.getY(), newZ));

						user.setHeight(newZ);
						user.updateStatus();
						
						if (RoomUser.class.isInstance(user) && next.equals(this.getRoom().getRoomData().getRoomModel().getDoorNode()))
						{
							((RoomUser) user).getHabbo().leaveRoom(true);
						}
					}
					else if (user.getIsWalking())
					{
						if (user.getStatuses().containsKey("mv"))
						{
							user.getStatuses().remove("mv");
						}
						user.setWalkingFromNode(null);

						user.updateStatus();
						user.setNeedsUpdate(true);
						user.setIsWalking(false);
					}

					if (user.getNeedsUpdate())
					{
						if (user.getStatuses().containsKey("mv") && !user.getIsWalking())
						{
							user.setWalkingFromNode(null);
							user.getStatuses().remove("mv");
						}

						for (UserItem item : this.getRoom().getRoomObjectsHandler().getFloorItemsAt(user.getNode()))
						{
							if (item.getCanSit())
							{
								this.getRoom().getRoomInteractions().userWalkedOnSeat(user, item);
								break;
							}
							else if (item.getCanLay())
							{
								this.getRoom().getRoomInteractions().userWalkedOnBed(user, item);
								break;
							}
						}

						user.updateStatus();
						unitsToUpdate.add(user);
						user.setNeedsUpdate(false);
						user.setIsWalking(false);
					}

				}

				unitsToUpdate.stream().filter(user -> user instanceof RoomUser).filter(user -> user.getGoal().equals(user.getNode()) && user.getNode().equals(this.getRoom().getRoomData().getRoomModel().getDoorNode())).forEach(user -> ((RoomUser) user).getHabbo().leaveRoom(true));

				while (this.getRoom().getRoomUnitsHandler().getUnitsToUpdate().size() > 0)
				{
					final List<RoomUnit> uList = new ArrayList<>();
					int max = 25;
					while(this.getRoom().getRoomUnitsHandler().getUnitsToUpdate().peek() != null)
					{
						if (max <= 0)
							break;

						max--;
						uList.add(this.getRoom().getRoomUnitsHandler().getUnitsToUpdate().poll());
					}
					this.getRoom().getRoomUnitsHandler().send(new RoomUsersStatusComposer(uList));
				}

				unitsToUpdate.clear();
			}
			catch (Exception e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
				this.getRoom().crash();
			}

			try
			{
				Thread.sleep(430);
			}
			catch (InterruptedException ignored)
			{
			}
		}
	}
}
