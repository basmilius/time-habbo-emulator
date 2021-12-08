package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.RoomUser;

@Command(
		executable = "cmd.syntax.talk",
		syntax = ":talk <user> <message>"
)
public class TalkCommand extends ICommand
{

	public TalkCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_talk") && parts.length > 2)
		{
			final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(parts[1]);
			if (habbo != null && habbo.isInRoom() && habbo.isOnline())
			{
				if (this.isSuperUser(this.invoker) || !habbo.getPermissions().contains("cmd_shutdown"))
				{
					habbo.getRoomUser().chatRoomMessage((this.shouted) ? ChatType.SHOUT : ChatType.TALK, Bootstrap.getEngine().getGame().getChatCommands().toString(parts, 2), ChatBubble.fromInt(this.bubbleId));
				}
				else
				{
					this.invoker.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.talk.permissions.denied", "Whoops, you're not allowed to use this command on this Pixel!"), ChatBubble.BOT);
				}
			}
			else
			{
				this.invoker.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.talk.invalid.user", "Whoops, this Pixel doesn't exists, isn't in this room or is offline!"), ChatBubble.BOT);
			}
			return true;
		}
		return false;
	}

}
