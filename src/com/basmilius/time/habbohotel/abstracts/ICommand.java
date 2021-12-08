package com.basmilius.time.habbohotel.abstracts;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;

/**
 * Base for in-game chat commands.
 * In-game commands can be executed by all kind of units. Users, Bots and Pets.
 */
public abstract class ICommand
{

	protected final RoomUnit invoker;
	protected final boolean shouted;
	protected final int bubbleId;
	private boolean allowBots = false;

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public ICommand(final RoomUnit invoker, final boolean shouted, final int bubbleId)
	{
		this.invoker = invoker;
		this.shouted = shouted;
		this.bubbleId = bubbleId;
	}

	/**
	 * Handles the command and returns the result as a boolean.
	 *
	 * @param parts Command arguments.
	 * @return boolean
	 * @throws Exception
	 */
	public abstract boolean handleCommand(final String[] parts) throws Exception;

	/**
	 * Gets if this command allows server units (PETS AND BOTS).
	 *
	 * @return boolean
	 */
	public boolean getAllowBots()
	{
		return this.allowBots;
	}

	/**
	 * Sets if this commands allows server units.
	 *
	 * @param allow boolean
	 */
	public void setAllowBots(final boolean allow)
	{
		this.allowBots = allow;
	}

	/**
	 * Gets the invoker as the specified IRoomUnit object.
	 *
	 * @param unitClass RoomUnit class type.
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public <T> T getInvoker(Class<T> unitClass)
	{
		if (unitClass.isInstance(this.invoker))
			return (T) this.invoker;

		return null;
	}

	/**
	 * Gets if the executing unit is a BOT.
	 *
	 * @return boolean
	 */
	protected boolean isBot()
	{
		return this.invoker.getUnitType() == RoomUnitType.BOT;
	}

	/**
	 * Gets if the executing unit is a PET.
	 *
	 * @return boolean
	 */
	protected boolean isPet()
	{
		return this.invoker.getUnitType() == RoomUnitType.PET;
	}

	/**
	 * Gets if the executing unit is a super user.
	 *
	 * @param unit Unit to check.
	 * @return boolean
	 */
	protected boolean isSuperUser(final RoomUnit unit)
	{
		if (this.isUser())
		{
			final String[] superUsers = Bootstrap.getEngine().getConfig().getStringArray("hotel.roles.super.users", new String[]{});

			for (final String superUser : superUsers)
				if (superUser.equalsIgnoreCase(unit.getName()))
					return true;
		}
		return false;
	}

	/**
	 * Gets if the executing unit is a USER.
	 *
	 * @return boolean
	 */
	protected boolean isUser()
	{
		return this.invoker.getUnitType() == RoomUnitType.USER;
	}

	/**
	 * Checks if the unit is a server unit or the user has the specified permission.
	 *
	 * @param permission Permission.
	 * @return boolean
	 */
	protected boolean isServerUnitOrHasPermission(final String permission)
	{
		if (this.isBot() || this.isPet())
			return true;
		else if (this.isUser() && ((RoomUser) this.invoker).getHabbo().getPermissions().contains(permission))
			return true;

		return false;
	}

	/**
	 * Checks if the unit is a server unit or the user has the specified subscription.
	 *
	 * @param subscription Subscription ID.
	 * @return boolean
	 */
	protected boolean isServerUnitOrHasSubscription(final String subscription)
	{
		if (this.isBot() || this.isPet())
			return true;
		else if (this.isUser() && ((RoomUser) this.invoker).getHabbo().getSubscriptions().hasSubscription(subscription))
			return true;
		else if (this.isUser() && ((RoomUser) this.invoker).getHabbo().getPermissions().contains("is_staff"))
			return true;

		return false;
	}

	/**
	 * Sends the user the "You need VIP" chat message.
	 *
	 * @param executable Command executor.
	 */
	public void sendUserNeedsVipMessage(final String executable)
	{
		this.invoker.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("vip.user.isnt.subscribed", "Your account needs a VIP-subscription to use the command '%command%'!").replace("%command%", Bootstrap.getEngine().getConfig().getString(executable, executable)), ChatBubble.BOT);
	}

}
