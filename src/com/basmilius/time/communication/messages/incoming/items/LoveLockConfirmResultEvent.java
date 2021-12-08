package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.LoveLockUserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.items.LoveLockFriendConfirmedComposer;
import com.basmilius.time.communication.messages.outgoing.items.LoveLockFriendDeniedComposer;
import com.basmilius.time.util.TimeUtils;

@Event(id = Incoming.LoveLockConfirmResult)
public class LoveLockConfirmResultEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();
		final boolean accepted = packet.readBoolean();

		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);

		if (item == null || !LoveLockUserItem.class.isInstance(item))
			return;

		final LoveLockUserItem loveLock = (LoveLockUserItem) item;

		if (loveLock.getUserOne().isInRoom() && loveLock.getUserTwo().isInRoom() && loveLock.getUserOne().getCurrentRoom().getRoomData().getId() == item.getRoom().getRoomData().getId() && loveLock.getUserTwo().getCurrentRoom().getRoomData().getId() == item.getRoom().getRoomData().getId())
		{
			if (loveLock.getUserOne().getId() == connection.getHabbo().getId())
			{
				loveLock.getServerData().getJSONObject("love_lock").put("user_one_confirmed", accepted ? 1 : 0);
				loveLock.saveServerData();

				if (loveLock.getUserTwoConfirmed() == -1 && accepted)
				{
					loveLock.getUserTwo().getConnection().send(new LoveLockFriendConfirmedComposer(loveLock.getId()));
					return;
				}
				else if (loveLock.getUserTwoConfirmed() == -1)
				{
					loveLock.getUserOne().getConnection().send(new LoveLockFriendDeniedComposer(loveLock.getId()));
					loveLock.getUserTwo().getConnection().send(new LoveLockFriendDeniedComposer(loveLock.getId()));
					this.setLock(loveLock, false);
					return;
				}
				else if (loveLock.getUserTwoConfirmed() == 0)
				{
					loveLock.getUserOne().getConnection().send(new LoveLockFriendDeniedComposer(loveLock.getId()));
					this.setLock(loveLock, false);
					return;
				}
			}
			else if (loveLock.getUserTwo().getId() == connection.getHabbo().getId())
			{
				loveLock.getServerData().getJSONObject("love_lock").put("user_two_confirmed", accepted ? 1 : 0);
				loveLock.saveServerData();

				if (loveLock.getUserOneConfirmed() == -1 && accepted)
				{
					loveLock.getUserOne().getConnection().send(new LoveLockFriendConfirmedComposer(loveLock.getId()));
					return;
				}
				else if (loveLock.getUserOneConfirmed() == -1)
				{
					loveLock.getUserOne().getConnection().send(new LoveLockFriendDeniedComposer(loveLock.getId()));
					loveLock.getUserTwo().getConnection().send(new LoveLockFriendDeniedComposer(loveLock.getId()));
					this.setLock(loveLock, false);
					return;
				}
				else if (loveLock.getUserOneConfirmed() == 0)
				{
					loveLock.getUserOne().getConnection().send(new LoveLockFriendDeniedComposer(loveLock.getId()));
					this.setLock(loveLock, false);
					return;
				}
			}

			this.setLock(loveLock, true);
		}
		else
		{
			loveLock.getUserOne().getConnection().send(new LoveLockFriendDeniedComposer(loveLock.getId()));
			loveLock.getUserTwo().getConnection().send(new LoveLockFriendDeniedComposer(loveLock.getId()));
		}
	}

	private void setLock(final LoveLockUserItem loveLock, boolean lock)
	{
		if (lock)
		{
			loveLock.getServerData().getJSONObject("love_lock").put("locked_on", TimeUtils.getUnixTimestamp());

			loveLock.setExtraData("1");
			loveLock.updateStateInRoom();

			if (loveLock.getUserOne().getRoomUser() == null || loveLock.getUserTwo().getRoomUser() == null)
				return;
			
			loveLock.getUserOne().getRoomUser().setCanWalk(true);
			loveLock.getUserTwo().getRoomUser().setCanWalk(true);

			/**
			 * This also closes the dialog.
			 */
			loveLock.getUserOne().getConnection().send(new LoveLockFriendDeniedComposer(loveLock.getId()));
			loveLock.getUserTwo().getConnection().send(new LoveLockFriendDeniedComposer(loveLock.getId()));
		}
		else if (loveLock.getServerData().has("love_lock"))
		{
			loveLock.getServerData().remove("love_lock");
			loveLock.saveServerData();
		}
	}

}
