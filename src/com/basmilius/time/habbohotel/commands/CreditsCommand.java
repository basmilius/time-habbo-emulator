package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.enums.GenericAlertType;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.util.ObjectUtils;

@Command(
		executable = "cmd.syntax.credits",
		syntax = ":credits [<user>] <credits>"
)
public class CreditsCommand extends ICommand
{

	public CreditsCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("cmd_credits"))
		{
			int credits = 0;
			Habbo toHabbo = null;

			if (parts.length >= 2)
			{
				if (parts.length == 3 && ObjectUtils.isNumeric(parts[2]))
				{
					toHabbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(parts[1]);
					credits = Integer.parseInt(parts[2]);
				}
				else if (ObjectUtils.isNumeric(parts[1]))
				{
					credits = Integer.parseInt(parts[1]);
				}

				if (credits != 0)
				{
					if (toHabbo == null)
					{
						for (Habbo habbo : Bootstrap.getEngine().getGame().getHabboManager().getOnlineHabbos())
						{
							habbo.getSettings().updateCredits(credits);
							if (Bootstrap.getEngine().getConfig().getBoolean("cmd.credits.alert.enabled", true))
							{
								habbo.getConnection().sendNotif(GenericAlertType.TEXT, Bootstrap.getEngine().getConfig().getString("cmd.credits.everyone.alert", "You've just received some credits from hotel staff!"));
							}
						}
					}
					else if (toHabbo.isOnline())
					{
						toHabbo.getSettings().updateCredits(credits);
					}
					else
					{
						this.invoker.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.credits.user.offline", "User is offline!"), ChatBubble.BOT);
					}
				}
				else
				{
					this.invoker.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.credits.invalid.syntax", "Invalid command syntax, :credits user amount / :credits amount"), ChatBubble.BOT);
				}
			}
			else
			{
				this.invoker.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.credits.invalid.syntax", "Invalid command syntax, :credits user amount / :credits amount"), ChatBubble.BOT);
			}

			return true;
		}
		return false;
	}

}
