package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.util.ObjectUtils;

@Command(
		executable = "cmd.syntax.ban",
		syntax = ":ban <user> <hours> <message>"
)
public class BanCommand extends ICommand
{

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public BanCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.isUser() && this.getInvoker(RoomUser.class).getHabbo().getPermissions().contains("cmd_ban"))
		{
			if (parts.length < 3)
			{
				this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ban.invalid.syntax", "Invalid ban command syntax."), ChatBubble.EXCLAMATION);
				return true;
			}

			Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(parts[1]);

			if (habbo == null)
			{
				this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ban.user.not.found", "The specified user cannot be founded."), ChatBubble.EXCLAMATION);
				return true;
			}

			if (!ObjectUtils.isNumeric(parts[2]))
			{
				this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ban.invalid.hours", "Hours must be a number."), ChatBubble.EXCLAMATION);
				return true;
			}

			int hours = Integer.parseInt(parts[2]);
			String reason = Bootstrap.getEngine().getGame().getChatCommands().toString(parts, 3);

			try
			{
				Bootstrap.getEngine().getGame().getBanManager().createBan(habbo, hours, reason, this.getInvoker(RoomUser.class).getHabbo());
				this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ban.user.banned", "The specified user is now banned."), ChatBubble.EXCLAMATION);
			}
			catch (Exception e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
				this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ban.user.not.banned", "Something went wrong saving the new ban. The user isn't banned."), ChatBubble.EXCLAMATION);
			}
			return true;
		}
		return false;
	}

}
