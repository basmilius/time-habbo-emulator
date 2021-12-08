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
		executable = "cmd.syntax.ambassador",
		syntax = ":ambassador <user> <status/toggle>"
)
public class AmbassadorCommand extends ICommand
{

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public AmbassadorCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.isUser() && this.getInvoker(RoomUser.class).getHabbo().getPermissions().contains("cmd_ambassador"))
		{
			if (parts.length >= 3)
			{
				final String habboName = parts[1];
				final String commandName = parts[2];
				final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(habboName);

				if (habbo != null)
				{
					habbo.loadSettings();

					switch (commandName)
					{
						case "status":
							if (habbo.getSettings().getIsAmbassador())
							{
								this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ambassador.user.is.ambassador", "The user is an ambassador!"), ChatBubble.EXCLAMATION);
							}
							else
							{
								this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ambassador.user.not.ambassador", "The user is not an ambassador."), ChatBubble.EXCLAMATION);
							}
							break;
						case "toggle":
							habbo.getSettings().setIsAmbassador(!habbo.getSettings().getIsAmbassador());
							this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ambassador.toggled", "Ambassador status toggled!"), ChatBubble.EXCLAMATION);
							break;
						default:
							this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ambassador.command.not.found", "The specified command does not exists!"), ChatBubble.EXCLAMATION);
							break;
					}
				}
				else
				{
					this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ambassador.user.not.found", "The specified user does not exists!"), ChatBubble.EXCLAMATION);
				}
			}
			else
			{
				this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.ambassador.invalid.syntax", "Invalid command syntax! See ASE for more information about commands."), ChatBubble.EXCLAMATION);
			}

			return true;
		}
		return false;
	}

}
