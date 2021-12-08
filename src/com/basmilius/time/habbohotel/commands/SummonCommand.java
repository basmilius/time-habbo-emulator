package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.enums.GenericAlertType;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.RoomBot;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.habbohotel.utils.misc.UncleWiki;
import com.basmilius.time.communication.messages.outgoing.general.RoomForwardComposer;

import java.util.List;
import java.util.stream.Collectors;

@Command(
		executable = "cmd.syntax.summon",
		syntax = ":summon <type> [<param>]"
)
public class SummonCommand extends ICommand
{

	/**
	 * Constructor.
	 *
	 * @param invoker  Which unit did execute this command.
	 * @param shouted  Is this command shouted.
	 * @param bubbleId Which bubble was used.
	 */
	public SummonCommand(final RoomUnit invoker, final boolean shouted, final int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public final boolean handleCommand(final String[] parts) throws Exception
	{
		if (this.isUser() && this.getInvoker(RoomUser.class).getHabbo().getPermissions().contains("cmd_summon"))
		{
			if (parts.length >= 2)
			{
				switch (parts[1])
				{
					case "all":
						final List<Habbo> habbos = Bootstrap.getEngine().getGame().getHabboManager().getOnlineHabbos().stream().filter(h -> h.getCurrentRoom() != this.invoker.getRoom()).filter(h -> h != this.getInvoker(RoomUser.class).getHabbo()).collect(Collectors.toList());
						for (final Habbo habbo : habbos)
						{
							habbo.getConnection().send(new RoomForwardComposer(this.invoker.getRoom().getRoomData().getId()));
						}
						this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.summon.adm.all-have-been-summoned", "All users have been summoned!"), ChatBubble.EXCLAMATION);
						break;
					case "permission":
						break;
					case "rand":
						break;
					case "uncle":
						final Node randomNode = this.invoker.getRoom().getRoomData().getRoomModel().getRandomNode();
						final UncleWiki uncle = new UncleWiki(-1, this.invoker.getRoom());
						final RoomBot roomUncle = new RoomBot(this.invoker.getRoom(), uncle);

						roomUncle.setPosition(randomNode);
						roomUncle.setHeight(randomNode.getZ());
						roomUncle.setRotation(4);

						this.invoker.getRoom().getRoomUnitsHandler().addRoomUnit(roomUncle, true);
						break;
					case "user":
					{
						if (parts.length >= 3)
						{
							final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(parts[2]);
							if (habbo != null && habbo.isOnline() && habbo != this.getInvoker(RoomUser.class).getHabbo() && habbo.getCurrentRoom() != this.invoker.getRoom())
							{
								habbo.getConnection().sendNotif(GenericAlertType.TEXT, Bootstrap.getEngine().getConfig().getString("cmd.summon.user.summoned", "You've been summoned by an Administrator."));
								habbo.getConnection().send(new RoomForwardComposer(this.invoker.getRoom().getRoomData().getId()));
							}
							else
							{
								this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.summon.adm.user-not-found", "The specified user doesn't exist!"), ChatBubble.EXCLAMATION);
							}
						}
						break;
					}
					default:
						this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.summon.adm.type-not-found", "The specified summon-type doesn't exist!"), ChatBubble.EXCLAMATION);
						break;
				}
			}
			else
			{
				this.getInvoker(RoomUser.class).chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.summon.adm.type-not-specified", "Please specify a summon-type!"), ChatBubble.EXCLAMATION);
			}
			return true;
		}
		return false;
	}
}
