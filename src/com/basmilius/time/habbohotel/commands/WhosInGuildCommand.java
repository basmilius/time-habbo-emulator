package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.guilds.Guild;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationUserListComposer;
import com.basmilius.time.util.ObjectUtils;

@Command(
		executable = "cmd.syntax.whosinguild",
		syntax = ":whosinguild [guildid]"
)
public class WhosInGuildCommand extends ICommand
{

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public WhosInGuildCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.isUser() && this.getInvoker(RoomUser.class).getHabbo().getPermissions().contains("acc_supporttool"))
		{
			final Guild guild;

			if (parts.length > 1 && ObjectUtils.isNumeric(parts[1]))
				guild = Bootstrap.getEngine().getGame().getGuildManager().getGuild(Integer.parseInt(parts[1]));
			else if (this.invoker.getRoom().getRoomData().getGuild() != null)
				guild = this.invoker.getRoom().getRoomData().getGuild();
			else
				return true;

			if (guild == null)
				return true;

			this.getInvoker(RoomUser.class).getConnection().send(new ModerationUserListComposer(guild.getMembersHabbos()));
			return true;
		}
		return false;
	}

}
