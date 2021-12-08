package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.enums.CloseClientReason;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.handshake.DisconnectReasonMessageComposer;

@Command(
		executable = "cmd.syntax.disconnect",
		syntax = ":disconnect <user>"
)
public class DisconnectCommand extends ICommand
{

	public DisconnectCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_disconnect") && parts.length >= 2)
		{
			final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(parts[1]);

			if (habbo.isInRoom() && habbo.isOnline())
			{
				if (!habbo.getPermissions().contains("cmd_shutdown"))
				{
					habbo.getConnection().send(new DisconnectReasonMessageComposer(CloseClientReason.KICKED_BY_STAFF));
				}
				else
				{
					this.invoker.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.disconnect.permissions.denied", "Whoops, you're not allowed to use this command on this Pixel!"), ChatBubble.BOT);
				}
			}
			else
			{
				this.invoker.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.disconnect.invalid.user", "Whoops, this Pixel doesn't exists, isn't in this room or is offline!"), ChatBubble.BOT);
			}
			return true;
		}
		return false;
	}

}
