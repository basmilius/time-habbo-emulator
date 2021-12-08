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
		executable = "cmd.syntax.badge.give",
		syntax = ":badge.give [<user>] <badgeCode>"
)
public class BadgeGiveCommand extends ICommand
{

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public BadgeGiveCommand(final RoomUnit invoker, final boolean shouted, final int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(final String[] parts) throws Exception
	{
		if (this.isUser() && this.getInvoker(RoomUser.class).getHabbo().getPermissions().contains("cmd_badge"))
		{
			if (parts.length == 3)
			{
				final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(parts[1]);

				if (habbo == null || !habbo.isOnline())
				{
					this.invoker.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.badge.user.not.found", "The specified Habbo does not exists or isn't online!"), ChatBubble.BOT);
				}
				else
				{
					String badgeCode = parts[2];

					if (!Bootstrap.getEngine().getGame().getBadgeManager().hasBadge(habbo, badgeCode))
					{
						Bootstrap.getEngine().getGame().getBadgeManager().addBadge(habbo, badgeCode, 0, false);
						this.invoker.chatRoomMessage(ChatType.WHISPER, String.format(Bootstrap.getEngine().getConfig().getString("cmd.badge.gave.badge", "You gave %s to %s!"), badgeCode, habbo.getUsername()), ChatBubble.BOT);
					}
					else
					{
						this.invoker.chatRoomMessage(ChatType.WHISPER, String.format(Bootstrap.getEngine().getConfig().getString("cmd.badge.user.has.badge", "The Habbo already has the badge %s!"), badgeCode), ChatBubble.BOT);
					}
				}
			}
			else
			{
				final String badgeCode = parts[1];

				Bootstrap.getEngine().getGame().getHabboManager().getOnlineHabbos().stream().filter(habbo -> !(habbo == null || !habbo.isOnline())).filter(habbo -> !Bootstrap.getEngine().getGame().getBadgeManager().hasBadge(habbo, badgeCode)).forEach(habbo -> Bootstrap.getEngine().getGame().getBadgeManager().addBadge(habbo, badgeCode, 0, false));
				this.invoker.chatRoomMessage(ChatType.WHISPER, String.format(Bootstrap.getEngine().getConfig().getString("cmd.badge.gave.all.badge", "You gave %s to all the online Habbos!"), badgeCode), ChatBubble.BOT);
			}

			return true;
		}

		return false;
	}
}
