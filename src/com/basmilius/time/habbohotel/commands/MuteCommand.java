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
		executable = "cmd.syntax.mute",
		syntax = ":mute <user> <seconds>"
)
public class MuteCommand extends ICommand
{

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public MuteCommand(final RoomUnit invoker, final boolean shouted, final int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(final String[] parts) throws Exception
	{
		if (this.isUser() && this.isServerUnitOrHasPermission("cmd_mute"))
		{
			if (parts.length >= 3 && ObjectUtils.isNumeric(parts[2]))
			{
				final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(parts[1]);
				final int seconds = Integer.parseInt(parts[2]);

				if (habbo != null && seconds > 0)
				{
					habbo.mute(seconds, ((RoomUser) this.invoker).getHabbo());
					this.invoker.chatRoomMessage(ChatType.WHISPER, "cmd.mute.succeeded", ChatBubble.BOT);
				}
				else
				{
					this.invoker.chatRoomMessage(ChatType.WHISPER, "cmd.mute.invalid.habbo", ChatBubble.BOT);
				}
			}
			else
			{
				this.invoker.chatRoomMessage(ChatType.WHISPER, "cmd.mute.invalid.syntax", ChatBubble.BOT);
			}

			return true;
		}
		return false;
	}

}
