package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;

@Command(
		executable = "cmd.syntax.spectator",
		syntax = ":spectator"
)
public class SpectatorCommand extends ICommand
{

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public SpectatorCommand(final RoomUnit invoker, final boolean shouted, final int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(final String[] parts) throws Exception
	{
		if (this.isUser() && this.getInvoker(RoomUser.class).getHabbo().getPermissions().contains("cmd_spectator"))
		{
			this.getInvoker(RoomUser.class).getHabbo().getHabboCache().set("pt.management:roommanager.next.spectator", !this.getInvoker(RoomUser.class).getHabbo().getHabboCache().getBoolean("pt.management:roommanager.next.spectator", false));
			return true;
		}
		return false;
	}

}
