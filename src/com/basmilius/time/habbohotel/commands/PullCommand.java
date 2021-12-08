package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;

@Command(
		executable = "cmd.syntax.pull",
		syntax = ":pull [<user>]"
)
public class PullCommand extends ICommand
{

	public PullCommand(RoomUnit invoker, boolean shouted, int bubbleId)
	{
		super(invoker, shouted, bubbleId);
	}

	@Override
	public boolean handleCommand(String[] parts) throws Exception
	{
		if (this.isServerUnitOrHasSubscription("pixel_vip"))
		{
			if (((RoomUser) this.invoker).getHabbo().isInRoom())
			{
				Room room = this.invoker.getRoom();
				RoomUser roomUser = ((RoomUser) this.invoker);

				Node nodeInFront = roomUser.getNodeInFront(1);
				Node nodeToPull = roomUser.getNodeInFront(2);

				if (room.getRoomData().getRoomModel().isValidNode(nodeToPull))
				{
					RoomUnit roomUnit = room.getRoomUnitsHandler().getUnitAt(nodeToPull);

					if (((RoomUser) this.invoker).getHabbo().getPermissions().contains("is_staff") && parts.length > 1)
					{
						Habbo habboToSuperPull = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromUsername(parts[1]);

						if (habboToSuperPull != null && habboToSuperPull.isInRoom() && habboToSuperPull.getCurrentRoom().getRoomData().getId() == room.getRoomData().getId())
						{
							roomUnit = habboToSuperPull.getRoomUser();
						}
					}

					if (roomUnit != null)
					{
						if ((parts.length > 1 && roomUnit.getName().equalsIgnoreCase(parts[1])) || ((RoomUser) this.invoker).getHabbo().getPermissions().contains("is_staff"))
						{
							if (room.getRoomData().getRoomModel().isValidNode(nodeInFront))
							{
								roomUnit.setGoal(nodeInFront);
								roomUser.chatRoomMessage(ChatType.TALK, String.format(Bootstrap.getEngine().getConfig().getString("cmd.pull.pulled", "* Pushes %s *"), roomUnit.getName()), ChatBubble.EXCLAMATION);
							}
							else
							{
								roomUser.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.pull.invalid.destination.node", "Invalid destination node!"), ChatBubble.EXCLAMATION);
							}
						}
						else
						{
							roomUser.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.pull.nobody.in.front", "There is no unit in front of you!"), ChatBubble.EXCLAMATION);
						}
					}
					else
					{
						roomUser.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.pull.nobody.in.front", "There is no unit in front of you!"), ChatBubble.EXCLAMATION);
					}
				}
				else
				{
					roomUser.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.pull.invalid.node", "You haven't a valid node in front of you!"), ChatBubble.EXCLAMATION);
				}
			}
			return true;
		}
		this.sendUserNeedsVipMessage("cmd.syntax.pull");

		return true;
	}

}
