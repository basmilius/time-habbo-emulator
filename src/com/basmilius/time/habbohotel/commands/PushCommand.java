package com.basmilius.time.habbohotel.commands;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.ICommand;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.annotations.Command;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.ChatType;
import com.basmilius.time.habbohotel.rooms.Room;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;

@Command(
		executable = "cmd.syntax.push",
		syntax = ":push [<user>]"
)
public class PushCommand extends ICommand
{

	public PushCommand(RoomUnit invoker, boolean shouted, int bubbleId)
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
				final Room room = this.invoker.getRoom();
				final RoomUser roomUser = ((RoomUser) this.invoker);
				final Node nodeInFront = roomUser.getNodeInFront(1);
				final Node nodeToPush = roomUser.getNodeInFront(2);

				if (room.getRoomData().getRoomModel().isValidNode(nodeInFront))
				{
					RoomUnit roomUnit = room.getRoomUnitsHandler().getUnitAt(nodeInFront);

					if (roomUnit != null)
					{
						if ((parts.length > 1 && roomUnit.getName().equalsIgnoreCase(parts[1])) || ((RoomUser) this.invoker).getHabbo().getPermissions().contains("is_staff"))
						{
							if (room.getRoomData().getRoomModel().isValidNode(nodeToPush))
							{
								roomUnit.setGoal(nodeToPush);
								roomUser.chatRoomMessage(ChatType.TALK, String.format(Bootstrap.getEngine().getConfig().getString("cmd.push.pushes", "* Pushes %s *"), roomUnit.getName()), ChatBubble.EXCLAMATION);
							}
							else
							{
								roomUser.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.push.invalid.destination.node", "Invalid destination node!"), ChatBubble.EXCLAMATION);
							}
						}
						else
						{
							roomUser.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.push.nobody.in.front", "There is no unit in front of you!"), ChatBubble.EXCLAMATION);
						}
					}
					else
					{
						roomUser.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.push.nobody.in.front", "There is no unit in front of you!"), ChatBubble.EXCLAMATION);
					}
				}
				else
				{
					roomUser.chatRoomMessage(ChatType.WHISPER, Bootstrap.getEngine().getConfig().getString("cmd.push.invalid.node", "You haven't a valid node in front of you!"), ChatBubble.EXCLAMATION);
				}
			}
			return true;
		}
		this.sendUserNeedsVipMessage("cmd.syntax.push");

		return true;
	}

}
