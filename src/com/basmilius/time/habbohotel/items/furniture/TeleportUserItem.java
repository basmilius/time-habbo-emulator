package com.basmilius.time.habbohotel.items.furniture;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.abstracts.IPixelException;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUnitType;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.outgoing.general.RoomForwardComposer;
import com.basmilius.time.communication.messages.outgoing.handshake.GenericErrorMessageComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class TeleportUserItem extends UserItem implements RoomUnit.WalkToCallback
{

	private String teleportState = "0";

	public TeleportUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public TeleportUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public void onWiredCollision(final RoomUnit unit)
	{

	}

	@Override
	public void onWiredInteractsWithItem(final WiredUserItem wiredUserItem)
	{

	}

	@Override
	public void onUnitInteractsWithItem(final RoomUnit unit, final int param)
	{
		if (this.getItemLinkedWith() != null)
		{
			try
			{
				unit.walkTo(this.getNodeInFront(), -1, this);
			}
			catch (IPixelException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);

				if (unit.getUnitType() == RoomUnitType.USER)
				{
					((RoomUser) unit).getConnection().send(new GenericErrorMessageComposer(e));
				}
			}
		}
	}

	@Override
	public boolean onUnitSaysSomething(final RoomUnit unit, final String text, final ChatBubble chatBubble, final boolean isShouted)
	{
		return false;
	}

	@Override
	public void onUnitWalksOnItem(final RoomUnit unit)
	{

	}

	@Override
	public void onUnitWalksOffItem(final RoomUnit unit)
	{

	}

	@Override
	public void serializeData(final ServerMessage response, final boolean isInventory, final boolean isDataOnly)
	{
		if (!isDataOnly)
			response.appendInt(0);

		response.appendInt(0);
		response.appendString(this.teleportState);
	}

	@Override
	public boolean onStep(final RoomUnit unit, final Node node)
	{
		return true;
	}

	@Override
	public void onWalkToSuccess(final RoomUnit unit)
	{
		(new Thread(() -> {
			try
			{
				if (!unit.getNode().equals(this.getNode()))
				{
					if (!unit.getNode().equals(this.getNodeInFront()))
						return;

					this.teleportState = "1";
					this.updateStateInRoom();

					unit.setGoal(this.getNode(), true);
					unit.setIsWalking(true);
					unit.setCanWalk(false);

					Thread.sleep(480);

					if (!unit.getNode().equals(this.getNode()))
						return;
				}
				else
				{
					this.teleportState = "1";
					this.updateStateInRoom();
				}

				Thread.sleep(240);

				this.teleportState = "2";
				this.updateAllDataInRoom();

				Thread.sleep(240);

				if (this.getItemLinkedWith().getRoom() == null)
				{
					unit.setGoal(this.getNodeInFront(), true);
					unit.setCanWalk(true);
				}
				else if (this.getRoom().getRoomData().getId() == this.getItemLinkedWith().getRoom().getRoomData().getId())
				{
					((TeleportUserItem) this.getItemLinkedWith()).teleportState = "2";
					this.getItemLinkedWith().updateStateInRoom();

					Thread.sleep(240);

					this.teleportState = "0";
					this.updateStateInRoom();

					Thread.sleep(240);

					((TeleportUserItem) this.getItemLinkedWith()).teleportState = "1";
					this.getItemLinkedWith().updateStateInRoom();

					unit.setPosition(this.getItemLinkedWith().getNode());
					unit.setHeight(this.getItemLinkedWith().getTotalHeight());
					unit.setRotation(this.getItemLinkedWith().getRot());
					unit.updateStatus();
					unit.setGoal(this.getItemLinkedWith().getNodeInFront());
					unit.setCanWalk(true);

					Thread.sleep(720);

					((TeleportUserItem) this.getItemLinkedWith()).teleportState = "0";
					this.getItemLinkedWith().updateAllDataInRoom();
				}
				else if (unit.getUnitType() == RoomUnitType.USER)
				{
					final Habbo user = ((RoomUser) unit).getHabbo();
					final UserItem item = this.getItemLinkedWith();
					user.setUserEntersRoomCallback(() -> {
						((TeleportUserItem) item).teleportState = "2";
						item.updateStateInRoom();

						if (!(user.isInRoom() && user.getCurrentRoom().getRoomData().getId() == item.getRoom().getRoomData().getId()))
							return;
						
						if (user.getRoomUser() == null)
							return;

						try
						{
							Thread.sleep(240);
						}
						catch (InterruptedException ignored)
						{

						}

						this.teleportState = "0";
						this.updateStateInRoom();

						try
						{
							Thread.sleep(240);
						}
						catch (InterruptedException ignored)
						{

						}

						((TeleportUserItem) item).teleportState = "1";
						item.updateStateInRoom();

						user.getRoomUser().setPosition(item.getNode());
						user.getRoomUser().setHeight(item.getTotalHeight());
						user.getRoomUser().setRotation(item.getRot());
						user.getRoomUser().updateStatus();
						user.getRoomUser().setGoal(item.getNodeInFront());
						unit.setCanWalk(true);

						try
						{
							Thread.sleep(720);
						}
						catch (InterruptedException ignored)
						{

						}

						((TeleportUserItem) item).teleportState = "0";
						item.updateAllDataInRoom();
					});
					user.getConnection().send(new RoomForwardComposer(item.getRoom().getRoomData().getId()));
				}
			}
			catch (InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		})).start();
	}

	@Override
	public void onWalkToFailed(RoomUnit unit)
	{

	}

}
