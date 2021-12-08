package com.basmilius.time.habbohotel.rooms;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.core.event.IEventDispatcher;
import com.basmilius.time.habbohotel.abstracts.IPixelException;
import com.basmilius.time.habbohotel.enums.AvatarAction;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.enums.SpecialEffects;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.chat.RoomChatMessage;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.habbohotel.rooms.pathfinding.Pathfinder;
import com.basmilius.time.habbohotel.rooms.pathfinding.Rotation;
import com.basmilius.time.communication.messages.ISerialize;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.outgoing.rooms.users.*;
import com.basmilius.time.communication.messages.outgoing.users.AvatarEditorApplyEffectComposer;
import com.basmilius.time.util.TimeUtils;

import java.util.*;

/**
 * The base of every unit in the server.
 * - BOTS.
 * - PETS.
 * - USERS.
 */
public abstract class RoomUnit extends IEventDispatcher implements ISerialize, Runnable
{

	private final Map<String, String> statuses;
	protected RoomUnitType unitType;
	protected Room room;
	protected int x;
	protected int y;
	protected double z;
	protected boolean isTeleporting;
	protected boolean isGivingCarryItem;
	protected boolean canWalk;
	private int unitId;
	private int goalX;
	private int goalY;
	private boolean canWalkOnGoal;
	private int bodyRotation;
	private int headRotation;
	private boolean isWalking;
	private boolean needsUpdate;
	private Queue<Node> path;
	private boolean isIdle;
	private int effectId;
	private int carryItem;
	private int currentDance;

	private Timer timer;
	private boolean timerHasTask;
	private int lastWalkTimer;

	private Node walkingFromNode;

	/**
	 * Constructor.
	 */
	protected RoomUnit (final Room room)
	{
		this.room = room;
		
		this.statuses = new HashMap<>();
		this.isTeleporting = false;
		this.isIdle = false;
		this.isGivingCarryItem = false;
		this.canWalk = true;

		this.unitId = 0;
		this.effectId = 0;
		this.carryItem = 0;
		this.currentDance = 0;

		this.timer = new Timer();
		this.timerHasTask = false;
		this.resetTimer();

		Thread thread = new Thread(this);
		thread.start();

		this.lastWalkTimer = 0;

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{

			@Override
			public void run()
			{
				if (getLastWalkTimer() > 0)
				{
					minLastWalkTimer();
				}
			}

		}, 0, 480);

		this.walkingFromNode = null;
	}

	public final Node getWalkingFromNode()
	{
		return this.walkingFromNode;
	}

	public final void setWalkingFromNode(final Node walkingFromNode)
	{
		this.walkingFromNode = walkingFromNode;
	}

	/**
	 * Timer for preventing walk spamming.
	 *
	 * @return int
	 */
	private int getLastWalkTimer()
	{
		return this.lastWalkTimer;
	}

	/**
	 * Sets the last time that this unit walks.
	 */
	private void setLastWalkTimer(final int x)
	{
		this.lastWalkTimer = x;
	}

	/**
	 * Resets the last time that this unit walks.
	 */
	private void minLastWalkTimer()
	{
		this.lastWalkTimer--;
	}

	/**
	 * Resets the IDLE check timer.
	 */
	public void resetTimer()
	{
		if (this.timerHasTask)
		{
			try
			{
				this.timer.cancel();
				this.timer.purge();
			}
			catch (IllegalStateException ignored)
			{

			}

			this.timerHasTask = false;
		}

		final RoomUnit unit = this;

		this.timer = new Timer();
		this.timer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				try
				{
					unit.setIdle(true);
				}
				catch (NullPointerException ignored)
				{

				}
			}

		}, Bootstrap.getEngine().getConfig().getInt("hotel.rooms.auto.idle", 300000));
		this.timerHasTask = true;
	}

	/**
	 * Gets the unit ID.
	 * This is an unique ID for al the units in a room.
	 *
	 * @return int
	 */
	public int getUnitId()
	{
		return this.unitId;
	}

	/**
	 * Sets the unit ID.
	 * This is an unique ID for al the units in a room.
	 *
	 * @param id Unit ID.
	 */
	public void setUnitId(final int id)
	{
		this.unitId = id;
	}

	/**
	 * Gets the Type of this unit (BOT, PET, USER).
	 *
	 * @return RoomUnitType
	 */
	public RoomUnitType getUnitType()
	{
		return this.unitType;
	}

	/**
	 * Gets the Room where this unit is.
	 *
	 * @return Room
	 */
	public Room getRoom()
	{
		return this.room;
	}

	/**
	 * Gets the X position of this unit.
	 *
	 * @return int
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * Gets the Y position of this unit.
	 *
	 * @return int
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * Gets the Z position of this unit.
	 *
	 * @return double
	 */
	public double getZ()
	{
		return this.z;
	}

	/**
	 * Gets the goal Node.
	 * The unit automatically walks to the goal.
	 *
	 * @return Node
	 */
	public Node getGoal()
	{
		return new Node(this.goalX, this.goalY);
	}

	/**
	 * Sets this units Goal Node.
	 *
	 * @param node The goal Node.
	 */
	public void setGoal(final Node node)
	{
		this.setGoal(node.getX(), node.getY(), false);
	}

	/**
	 * Sets this units Goal Node.
	 *
	 * @param node          The goal Node.
	 * @param canWalkOnGoal Can the unit always walk on the goal?
	 */
	public void setGoal(final Node node, final boolean canWalkOnGoal)
	{
		this.setGoal(node.getX(), node.getY(), canWalkOnGoal);
	}

	/**
	 * Sets this units Goal Node.
	 *
	 * @param x X-Position.
	 * @param y Y-Position.
	 */
	public void setGoal(final int x, final int y)
	{
		this.setGoal(x, y, false);
	}

	/**
	 * Sets this units Goal Node.
	 *
	 * @param x             X-Position.
	 * @param y             Y-Position.
	 * @param canWalkOnGoal Can the unit always walk on the goal?
	 */
	public void setGoal(int x, int y, final boolean canWalkOnGoal)
	{
		final List<UserItem> itemsOnGoal = this.getRoom().getRoomObjectsHandler().getFloorItemsAt(new Node(x, y));

		for (final UserItem item : itemsOnGoal)
		{
			if (item.getCanLay())
			{
				if (item.getRot() == 0)
				{
					for (int bedY = item.getY(); bedY <= (item.getY() + item.getWidth() - 1); bedY++)
					{
						boolean done = false;

						for (int bedX = item.getX(); bedX <= (item.getX() + item.getLength() - 1); bedX++)
						{
							if (x == bedX && y == bedY)
							{
								done = true;
								break;
							}
							else if (x == bedX)
							{
								x = bedX;
								y = item.getY();
							}
						}

						if (done)
							break;
					}
				}
				else if (item.getRot() == 2)
				{
					for (int bedX = item.getX(); bedX <= (item.getX() + item.getLength() - 1); bedX++)
					{
						boolean done = false;

						for (int bedY = item.getY(); bedY <= (item.getY() + item.getWidth() - 1); bedY++)
						{
							if (x == bedX && y == bedY)
							{
								done = true;
								break;
							}
							else if (y == bedY)
							{
								x = item.getX();
								y = bedY;
							}
						}

						if (done)
							break;
					}
				}
			}
		}

		this.goalX = x;
		this.goalY = y;
		this.canWalkOnGoal = canWalkOnGoal;

		final Pathfinder pathfinder = new Pathfinder(this.getRoom(), this);
		final Queue<Node> path = pathfinder.findPath(x, y);

		if (path == null)
		{
			return;
		}

		this.setPath(path);
		this.setIsWalking(true);
		this.setLastWalkTimer(1);
	}

	/**
	 * Gets if this unit can walk on the goal node.
	 *
	 * @return boolean
	 */
	public boolean getCanWalkOnGoal()
	{
		return this.canWalkOnGoal;
	}

	/**
	 * Gets the units body rotation.
	 *
	 * @return int
	 */
	public int getBodyRotation()
	{
		return this.bodyRotation;
	}

	/**
	 * Gets the units head rotation.
	 *
	 * @return int
	 */
	public int getHeadRotation()
	{
		return this.headRotation;
	}

	/**
	 * Gets if this unit needs an update.
	 *
	 * @return boolean
	 */
	public boolean getNeedsUpdate()
	{
		return this.needsUpdate;
	}

	/**
	 * Sets if this unit needs an update.
	 *
	 * @param needsUpdate Does this unit need an update.
	 */
	public void setNeedsUpdate(final boolean needsUpdate)
	{
		this.needsUpdate = needsUpdate;
	}

	/**
	 * Gets if this unit is laying down.
	 *
	 * @return boolean
	 */
	public boolean getIsLayingDown()
	{
		return this.statuses.containsKey("lay");
	}

	/**
	 * Gets if this unit is walking.
	 *
	 * @return boolean
	 */
	public boolean getIsWalking()
	{
		return this.isWalking;
	}

	/**
	 * Sets if this unit is walking.
	 *
	 * @param isWalking Is walking.
	 */
	public void setIsWalking(final boolean isWalking)
	{
		this.setIdle(false);
		this.isWalking = isWalking;
	}

	/**
	 * Gets if this unit is sitting.
	 *
	 * @return boolean
	 */
	public boolean getIsSitting()
	{
		return this.statuses.containsKey("sit");
	}

	/**
	 * Gets if this unit is teleporting.
	 *
	 * @return boolean
	 */
	public boolean getIsTeleporting()
	{
		return this.isTeleporting;
	}

	/**
	 * Sets if this unit is teleporting.
	 *
	 * @param isTeleporting Is teleporting.
	 */
	public void setIsTeleporting(final boolean isTeleporting)
	{
		this.isTeleporting = isTeleporting;
	}

	/**
	 * Gets if this unit is sleeping.
	 *
	 * @return boolean
	 */
	public boolean getIsIdle()
	{
		return this.isIdle;
	}

	/**
	 * Gets if this unit is giving an item.
	 *
	 * @return boolean
	 */
	public boolean getIsGivingCarryItem()
	{
		return this.isGivingCarryItem;
	}

	/**
	 * Sets if this unit is giving an item.
	 *
	 * @param isGiving Is giving.
	 */
	public void setIsGivingCarryItem(final boolean isGiving)
	{
		this.isGivingCarryItem = isGiving;
	}

	/**
	 * Gets if this unit can walk.
	 *
	 * @return boolean
	 */
	public boolean getCanWalk()
	{
		return (this.canWalk && this.lastWalkTimer == 0);
	}

	/**
	 * Sets if this unit can walk.
	 *
	 * @param canWalk Can walk.
	 */
	public void setCanWalk(final boolean canWalk)
	{
		this.canWalk = canWalk;
	}

	/**
	 * Gets the path that this unit is walking.
	 *
	 * @return Queue
	 */
	public Queue<Node> getPath()
	{
		return this.path;
	}

	/**
	 * Sets the path that this unit is walking.
	 *
	 * @param coords Coords.
	 */
	public void setPath(final Queue<Node> coords)
	{
		this.path = coords;
	}

	/**
	 * Gets the gender of this unit.
	 *
	 * @return String
	 */
	public String getGender()
	{
		if (this.getUnitType() == RoomUnitType.USER)
		{
			return ((RoomUser) this).getHabbo().getGender();
		}
		else if (this.getUnitType() == RoomUnitType.BOT)
		{
			return ((RoomBot) this).getBot().getGender();
		}
		return "U";
	}

	/**
	 * Sets the position of this unit.
	 *
	 * @param pos Position Node.
	 */
	public void setPosition(final Node pos)
	{
		this.setPos(pos.getX(), pos.getY());
	}

	/**
	 * Sets the position of this unit.
	 *
	 * @param x X-Position.
	 * @param y Y-Position.
	 */
	public void setPos(final int x, final int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the unit Z-Position.
	 *
	 * @param z Height.
	 */
	public void setHeight(final double z)
	{
		this.z = z;
	}

	/**
	 * Sets the units rotation.
	 *
	 * @param body Body and Head rotation.
	 */
	public void setRotation(final int body)
	{
		this.setRotation(body, body);
	}

	/**
	 * Sets the units rotation.
	 *
	 * @param body Body rotation.
	 * @param head Head rotation.
	 */
	public void setRotation(final int body, final int head)
	{
		if (this.getIsLayingDown())
			return;

		this.bodyRotation = body;
		this.headRotation = head;
	}

	/**
	 * Gets the statuses of this unit.
	 *
	 * @return Map
	 */
	public Map<String, String> getStatuses()
	{
		return this.statuses;
	}

	/**
	 * Sends a chat message.
	 *
	 * @param type       Type of chat (WHISPER, TALK, SHOUT).
	 * @param text       Text that will be chatted.
	 * @param chatBubble Chat bubble type.
	 */
	public void chatRoomMessage(final ChatType type, final String text, final ChatBubble chatBubble)
	{
		this.chatRoomMessage(type, text, chatBubble, null, false);
	}

	/**
	 * Sends a chat message.
	 *
	 * @param type       Type of chat (WHISPER, TALK, SHOUT).
	 * @param text       Text that will be chatted.
	 * @param chatBubble Chat bubble type.
	 * @param habboTo    Send only to this Habbo.
	 */
	public void chatRoomMessage(ChatType type, String text, final ChatBubble chatBubble, final Habbo habboTo, final boolean ignoreFlooding)
	{
		RoomChatMessage message = new RoomChatMessage(this, this.getRoom(), type, text, chatBubble, TimeUtils.getUnixTimestamp());

		if (habboTo != null && habboTo.getRoomUser() != null)
			message.setUnitTo(habboTo.getRoomUser());

		if (this.getRoom().getRoomChat() != null)
			this.room.getRoomChat().addMessage(message, ignoreFlooding);
	}

	/**
	 * Let this unit looks at a Node.
	 *
	 * @param node Node to look.
	 */
	public void lookAtPoint(final Node node)
	{
		this.lookAtPoint(node.getX(), node.getY());
	}

	/**
	 * Let this unit looks at a Node.
	 *
	 * @param x X-Position.
	 * @param y Y-Position.
	 */
	public void lookAtPoint(final int x, final int y)
	{
		if (this.x == x && this.y == y)
			return;

		boolean headOnly = false;

		if (this.getIsWalking() || this.getIsGivingCarryItem())
			return;

		if (this.getIsSitting())
			headOnly = true;

		this.setIdle(false);

		int rotation = Rotation.calculate(this.getX(), this.getY(), x, y);

		if (headOnly && (Math.abs(rotation - this.getBodyRotation()) > 1))
			return;
		else if (!headOnly && (Math.abs(rotation - this.getBodyRotation()) == 1))
			headOnly = true;

		if (headOnly)
			this.setRotation(this.getBodyRotation(), rotation);
		else
			this.setRotation(rotation);
		this.updateStatus();
	}

	/**
	 * Let this unit look at an IRoomUnit.
	 *
	 * @param unitToLook Unit to look at.
	 */
	public void lookAtUnit(final RoomUnit unitToLook)
	{
		if (unitToLook == null)
			return;

		if (this.unitId == unitToLook.unitId)
			return;

		this.lookAtPoint(unitToLook.getNode());
	}

	/**
	 * Gets the current effect ID used by this unit.
	 *
	 * @return int.
	 */
	public int getEffectId()
	{
		return this.effectId;
	}

	/**
	 * Gets the current item ID used by this unit.
	 *
	 * @return int.
	 */
	public int getCarryItem()
	{
		return this.carryItem;
	}

	/**
	 * Gets the current dance id.
	 *
	 * @return int.
	 */
	public int getCurrentDance()
	{
		return this.currentDance;
	}

	/**
	 * Let this unit dance.
	 *
	 * @param danceId Dance ID.
	 */
	public void dance(final int danceId)
	{
		this.setIdle(false);
		this.currentDance = danceId;
		this.getRoom().getRoomUnitsHandler().send(new ApplyDanceComposer(this, danceId));
	}

	/**
	 * Applies an action to this unit.
	 *
	 * @param action Action.
	 */
	public void applyAction(final AvatarAction action)
	{
		this.getRoom().getRoomUnitsHandler().send(new UserActionComposer(this.getUnitId(), action));
	}

	/**
	 * Applies an effect to this unit.
	 *
	 * @param effectId Effect ID.
	 */
	public void applyEffect(final int effectId)
	{
		this.setIdle(false);

		boolean canChange = true;
		final List<UserItem> items = this.getRoom().getRoomObjectsHandler().getFloorItemsAt(this.getNode());

		for (final UserItem item : items)
		{
			if (item.getBaseItem().getEffectFemale() == SpecialEffects.NONE && item.getBaseItem().getEffectMale() == SpecialEffects.NONE)
				continue;

			if (item.getBaseItem().getEffectMale() == effectId)
				continue;

			if (item.getBaseItem().getEffectFemale() == effectId)
				continue;

			canChange = false;
		}

		if (!canChange)
			return;

		this.effectId = effectId;

		if (this.unitType.equals(RoomUnitType.USER))
		{
			((RoomUser) this).getHabbo().setCurrentEffectId(effectId);
			((RoomUser) this).getHabbo().getConnection().send(new AvatarEditorApplyEffectComposer(effectId, 1));
		}

		this.getRoom().getRoomUnitsHandler().send(new ApplyEffectComposer(this, effectId));
	}

	/**
	 * Let this unit holt an item.
	 *
	 * @param itemId Item ID.
	 */
	public void carryItem(final int itemId)
	{
		this.setIdle(false);
		this.carryItem = itemId;
		this.getRoom().getRoomUnitsHandler().send(new CarryItemComposer(this, itemId));
	}

	/**
	 * Sets this units Idle status.
	 *
	 * @param idle Is sleeping.
	 */
	public void setIdle(final boolean idle)
	{
		this.setIdle(idle, false);
	}

	/**
	 * Sets this units Idle status.
	 *
	 * @param idle     Is sleeping.
	 * @param override Always override the value.
	 */
	public void setIdle(final boolean idle, final boolean override)
	{
		this.resetTimer();

		if (idle == this.isIdle && !override)
			return;

		if (this.room == null)
			return;

		this.isIdle = idle;
		this.room.getRoomUnitsHandler().send(new RoomUserIdleComposer(this.getUnitId(), idle));
	}

	/**
	 * Updates the status of this unit.
	 */
	public void updateStatus()
	{
		if (this.getRoom().getRoomUnitsHandler().getUnitsToUpdate().contains(this))
			return;

		this.getRoom().getRoomUnitsHandler().getUnitsToUpdate().add(this);
	}

	/**
	 * Gets the Node that this unit is on.
	 *
	 * @return Node
	 */
	public Node getNode()
	{
		return new Node(this.x, this.y);
	}

	/**
	 * Gets the Node in front of this unit.
	 *
	 * @return Node
	 */
	public Node getNodeInFront()
	{
		return this.getNodeInFront(1);
	}

	/**
	 * Gets the node in front of this unit at a given distance.
	 *
	 * @param distance Distance between.
	 * @return Node
	 */
	public Node getNodeInFront(final int distance)
	{
		Node coord = new Node(this.x, this.y);

		if (this.bodyRotation == 0)
		{
			coord.setCoordinates(coord.getX(), (coord.getY() - distance));
		}
		else if (this.bodyRotation == 2)
		{
			coord.setCoordinates((coord.getX() + distance), coord.getY());
		}
		else if (this.bodyRotation == 4)
		{
			coord.setCoordinates(coord.getX(), (coord.getY() + distance));
		}
		else if (this.bodyRotation == 6)
		{
			coord.setCoordinates((coord.getX() - distance), coord.getY());

		}
		else if (this.bodyRotation == 1)
		{
			coord.setCoordinates((coord.getX() + distance), coord.getY() - distance);
		}
		else if (this.bodyRotation == 3)
		{
			coord.setCoordinates((coord.getX() + distance), coord.getY() + distance);
		}
		else if (this.bodyRotation == 5)
		{
			coord.setCoordinates((coord.getX() - distance), coord.getY() + distance);
		}
		else if (this.bodyRotation == 7)
		{
			coord.setCoordinates((coord.getX() - distance), coord.getY() - distance);
		}

		return coord;
	}

	/**
	 * Gets the ID of this unit.
	 * This is NOT the Unique Unit ID!!!
	 *
	 * @return int.
	 */
	public int getId()
	{
		if (this.unitType == RoomUnitType.BOT)
			return ((RoomBot) this).getBot().getId();
		else if (this.unitType == RoomUnitType.USER)
			return ((RoomUser) this).getHabbo().getId();

		return 0;
	}

	/**
	 * Gets the name of this unit.
	 *
	 * @return String
	 */
	public final String getName()
	{
		if (this.unitType == RoomUnitType.BOT)
			return ((RoomBot) this).getBot().getName();
		else if (this.unitType == RoomUnitType.USER)
			return ((RoomUser) this).getHabbo().getUsername();
		else if (this.unitType == RoomUnitType.PET)
			return ((RoomPet) this).getPet().getName();

		return "";
	}

	/**
	 * Serializes the unit.
	 *
	 * @param response Server message.
	 */
	@Override
	public abstract void serialize(final ServerMessage response);

	/**
	 * Serializes the status of this unit.
	 *
	 * @param response Server message.
	 */
	public abstract void serializeStatus(final ServerMessage response);

	/**
	 * Move this unit to an given node.
	 *
	 * @param node     Node to walk to.
	 * @param maxSteps Max steps this unit van walk.
	 * @param callback MoveAvatar Callback.
	 * @throws IPixelException
	 */
	public void walkTo(final Node node, final int maxSteps, final WalkToCallback callback) throws IPixelException
	{
		if (callback == null)
		{
			throw new WalkException("Callback cannot be NULL.");
		}

		if (node.equals(this.getNode()))
		{
			callback.onWalkToSuccess(this);
		}
		else
		{
			final Thread t = new Thread(() -> {
				int steps = 0;
				try
				{
					RoomUnit.this.setGoal(node);
					boolean canceled = false;

					while (node.equals(RoomUnit.this.getGoal()) && !node.equals(RoomUnit.this.getNode()) && !canceled)
					{
						Thread.sleep(430);

						if (!RoomUnit.this.getRoom().isActive())
							return;

						canceled = !callback.onStep(RoomUnit.this, RoomUnit.this.getNode());

						if (maxSteps > -1)
						{
							steps++;

							if (steps >= maxSteps)
								break;
						}
					}

					if (canceled || maxSteps > -1 && !node.equals(RoomUnit.this.getNode()))
					{
						callback.onWalkToFailed(RoomUnit.this);
					}
					else
					{
						Thread.sleep(430);
						callback.onWalkToSuccess(RoomUnit.this);
					}
				}
				catch (InterruptedException ignored)
				{
					callback.onWalkToFailed(RoomUnit.this);
				}
			});
			t.start();
		}
	}

	/**
	 * WalkToCallback interface for IRoomUnit.walkTo(...);
	 */
	public interface WalkToCallback
	{


		boolean onStep(final RoomUnit unit, final Node node);

		/**
		 * Executes when the walk operation succeeds.
		 *
		 * @param unit The IRoomUnit.
		 */
		void onWalkToSuccess(final RoomUnit unit);

		/**
		 * Executes when the walk operation fails.
		 *
		 * @param unit The IRoomUnit.
		 */
		void onWalkToFailed(final RoomUnit unit);

	}

	/**
	 * WalkException Exception.
	 */
	public class WalkException extends IPixelException
	{

		private String message;

		/**
		 * Constructor.
		 *
		 * @param message Exception message.
		 */
		public WalkException(final String message)
		{
			this.errorCode = 2407;
			this.message = message;
		}

		/**
		 * Gets the exception message.
		 *
		 * @return String
		 */
		@Override
		public String getMessage()
		{
			return this.message;
		}

	}

}
