package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericAlertComposer;

@Command(
		executable = "cmd.syntax.alert",
		syntax = ":alert <user> <message>"
)
public class AlertCommand extends ICommand
{

	public AlertCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_alert"))
		{
			final Habbo habboTo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(parts[1]);

			if ((habboTo != null && !habboTo.isOnline()) || habboTo == null)
			{
				((RoomUser) this.invoker).getHabbo().getRoomUser().chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.alert.user.not.found", "User is not online or founded!"), ChatBubble.EXCLAMATION);
				return true;
			}

			String alert = Bootstrap.getEngine().getGame().getChatCommands().toString(parts, 2) + (char) 13;
			alert += (char) 13;
			alert += " - " + ((RoomUser) this.invoker).getHabbo().getUsername();

			habboTo.getConnection().send(new GenericAlertComposer(alert));

			return true;
		}
		return false;
	}

}
