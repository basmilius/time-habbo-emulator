package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationUserListComposer;

@Command(
		executable = "cmd.syntax.whosonline",
		syntax = ":whosonline"
)
public class WhosOnlineCommand extends ICommand
{

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public WhosOnlineCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.isUser() && this.getInvoker(RoomUser.class).getHabbo().getPermissions().contains("acc_supporttool"))
		{
			this.getInvoker(RoomUser.class).getConnection().send(new ModerationUserListComposer(Bootstrap.getEngine().getGame().getHabboManager().getOnlineHabbos()));
			return true;
		}
		return false;
	}

}
