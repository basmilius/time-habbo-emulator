package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.rooms.RoomUser;

@Command(
		executable = "cmd.syntax.teleport",
		syntax = ":teleport"
)
public class TeleportCommand extends ICommand
{

	public TeleportCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_teleport"))
		{
			boolean isTeleporting = this.invoker.getIsTeleporting();
			String msg = Bootstrap.getEngine().getConfig().getString("cmd.teleport.on", "Teleport mode on.");
			if (isTeleporting)
			{
				msg = Bootstrap.getEngine().getConfig().getString("cmd.teleport.off", "Teleport mode off.");
			}

			this.invoker.setIsTeleporting(!isTeleporting);
			this.invoker.chatRoomMessage(ChatType.WHISPER, msg, ChatBubble.BOT);
			return true;
		}
		return false;
	}

}
