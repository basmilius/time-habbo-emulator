package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;

@Command(
		executable = "cmd.syntax.setpublic",
		syntax = ":setpublic <0/1>"
)
public class SetPublicCommand extends ICommand
{

	public SetPublicCommand(final RoomUnit invoker, final boolean shouted, final int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_setpublic"))
		{
			if (parts.length == 2)
			{
				final Room room = this.invoker.getRoom();

				room.getRoomData().getPermissions().setIsPublicRoom(parts[1].equals("1"));

				this.invoker.chatRoomMessage(ChatType.WHISPER, "Room status updatet!", ChatBubble.BOT);
			}
			return true;
		}
		return false;
	}

}
