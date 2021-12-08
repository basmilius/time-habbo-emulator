package com.basmilius.time.habbohotel.rooms.cycles;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.RollerUserItem;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.outgoing.rooms.RollerMoveComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.RollerMoveFurniComposer;

import java.util.ArrayList;
import java.util.List;

public class RollerCycle extends ICycle
{

	private final int cycle;
	private final int rollerSpeed;
	private final List<RoomUnit> usersMoved;

	public RollerCycle(Room room)
	{
		super(room);
		this.cycle = 0;
		this.rollerSpeed = 4;
		this.usersMoved = new ArrayList<>();
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				if (!this.getRoom().isActive())
					break;

				this.usersMoved.clear();

				if (this.cycle >= this.rollerSpeed || this.cycle == 0)
				{
					final List<RollerUserItem> rollers = this.getRoom().getRoomObjectsHandler().getItems(RollerUserItem.class);

					if (rollers.size() > 0)
					{
						final List<UserItem> itemsMoved = new ArrayList<>();

						for (final UserItem roller : rollers)
						{
							final Node nodeInFront = roller.getNodeInFront();
							boolean canRoll = true;
							boolean nextIsRoller = false;
							double nextHeight;

							if (this.getRoom().getRoomData().getRoomModel().isValidNode(nodeInFront))
								nextHeight = this.getRoom().getRoomData().getRoomModel().getNodeHeights()[nodeInFront.getX()][nodeInFront.getY()];
							else
							{
								Bootstrap.getEngine().getLogging().logDebugLine("Roller.continue.");
								continue;
							}

							final RoomUnit roomUnit = this.getRoom().getRoomUnitsHandler().getUnitAt(roller.getNode());
							final List<UserItem> itemsOnRoller = this.getRoom().getRoomObjectsHandler().getFloorItemsAt(roller.getNode(), roller.getTotalHeight());
							List<UserItem> itemsInFront = this.getRoom().getRoomObjectsHandler().getFloorItemsAt(nodeInFront);

							if (roomUnit != null && (this.usersMoved.contains(roomUnit) || roomUnit.getIsWalking()))
								continue;

							for (final UserItem item : itemsInFront)
							{
								if (RollerUserItem.class.isInstance(item))
								{
									nextIsRoller = true;
									nextHeight = item.getTotalHeight();
									itemsInFront = this.getRoom().getRoomObjectsHandler().getFloorItemsAt(item.getNode(), item.getTotalHeight());
								}
							}

							if (nextIsRoller)
							{
								if (itemsInFront.size() > 1 || this.getRoom().getRoomUnitsHandler().getUnitAt(nodeInFront) != null)
								{
									canRoll = false;
								}
							}
							else
							{
								if (!canRollHere(nodeInFront))
								{
									canRoll = false;
								}
							}

							if (roomUnit != null && canRoll)
							{
								this.getRoom().getRoomUnitsHandler().send(new RollerMoveComposer(roomUnit, nodeInFront, roller, nextHeight));
								roomUnit.setPos(nodeInFront.getX(), nodeInFront.getY());
								roomUnit.setHeight(nextHeight);
								this.usersMoved.add(roomUnit);
							}

							for (final UserItem item : itemsOnRoller)
							{
								if (itemsMoved.contains(item) || !canRoll)
									continue;
								
								if (item.getId() == roller.getId())
									continue;
								
								this.getRoom().getRoomUnitsHandler().send(new RollerMoveFurniComposer(item, nodeInFront, roller, nextHeight));
								item.setX(nodeInFront.getX());
								item.setY(nodeInFront.getY());
								item.setZ(nextHeight);
								itemsMoved.add(item);
							}
						}
					}
				}

				Thread.sleep(430 * this.rollerSpeed);
			}
			catch (InterruptedException ignored)
			{
			}
			catch (Exception e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
				this.getRoom().crash();
			}
		}
	}

	private boolean canRollHere(final Node node)
	{
		if (this.getRoom().getRoomUnitsHandler().getUnitAt(node) != null)
			return false;

		for (final UserItem item : this.getRoom().getRoomObjectsHandler().getFloorItemsAt(node))
		{
			if (!item.getIsWalkable() && !item.getCanSit() && !item.getCanLay())
				return false;
		}
		return true;
	}

}
