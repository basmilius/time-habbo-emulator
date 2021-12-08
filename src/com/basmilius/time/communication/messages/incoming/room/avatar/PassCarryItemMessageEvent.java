package com.basmilius.time.communication.messages.incoming.room.avatar;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.Dance;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.rooms.users.GotCarryItemComposer;

@Event(id = Incoming.PassCarryItem)
public class PassCarryItemMessageEvent extends MessageEvent implements RoomUnit.WalkToCallback
{

	private RoomUser senderRoomUser;
	private RoomUser receiverRoomUser;

	@Override
	public void handle() throws Exception
	{
		final int userId = packet.readInt();
		final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(userId);

		if (habbo == null)
			return;

		if (!connection.getHabbo().isInRoom() || (!habbo.isInRoom() && habbo.getCurrentRoom().getRoomData().getId() == connection.getHabbo().getCurrentRoom().getRoomData().getId()))
			return;

		this.senderRoomUser = connection.getHabbo().getRoomUser();
		this.receiverRoomUser = habbo.getRoomUser();
		
		if (this.senderRoomUser == null || this.receiverRoomUser == null)
			return;

		this.senderRoomUser.setIsGivingCarryItem(true);
		this.senderRoomUser.walkTo(this.receiverRoomUser.getNodeInFront(), -1, this);
	}

	@Override
	public boolean onStep(final RoomUnit unit, final Node node)
	{
		return true;
	}

	@Override
	public void onWalkToSuccess(final RoomUnit unit)
	{
		this.senderRoomUser.setIsGivingCarryItem(false);
		this.senderRoomUser.lookAtUnit(this.receiverRoomUser);
		this.receiverRoomUser.lookAtUnit(this.senderRoomUser);
		this.receiverRoomUser.dance(Dance.NONE);
		this.receiverRoomUser.carryItem(this.senderRoomUser.getCarryItem());
		this.receiverRoomUser.getConnection().send(new GotCarryItemComposer(this.senderRoomUser.getUnitId(), this.senderRoomUser.getCarryItem()));
		this.senderRoomUser.carryItem(0);
	}

	@Override
	public void onWalkToFailed(final RoomUnit unit)
	{

	}
}
