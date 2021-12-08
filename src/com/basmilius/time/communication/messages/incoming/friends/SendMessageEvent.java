package com.basmilius.time.communication.messages.incoming.friends;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.friends.FriendMessageErrorComposer;
import com.basmilius.time.communication.messages.outgoing.friends.ReceiveMessageComposer;
import com.basmilius.time.util.TimeUtils;

@Event(id = Incoming.SendMessage)
public class SendMessageEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int friendId = packet.readInt();
		final String message = packet.readString();
		final Habbo friend = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(friendId);

		// TODO: Muted users. (3 = receiver, 4 = sender muted)
		// TODO: Users can turn off the chat. (8 = receiver, 9 = sender has no chat)
		// TODO: Setting for the user is busy. (7 = busy)

		if (message.replace(" ", "").isEmpty())
			return;

		if (!connection.getHabbo().getMessenger().isFriendWith(friend))
		{
			connection.send(new FriendMessageErrorComposer(friend.getId(), 6, message)); // messenger.error.notfriend
		}
		else if (!friend.isOnline() && Bootstrap.getEngine().getConfig().getBoolean("hotel.messenger.offline.messaging.enabled", false))
		{
			friend.loadMessenger();
			try
			{
				friend.getMessenger().getCache().addOfflineMessage(connection.getHabbo(), message, TimeUtils.getUnixTimestamp());
			}
			catch (Exception e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
				connection.send(new FriendMessageErrorComposer(friend.getId(), 10, message)); // messenger.error.offline_failed
			}
		}
		else if (!friend.isOnline())
		{
			connection.send(new FriendMessageErrorComposer(friend.getId(), 5, message)); // messenger.error.offline
		}
		else
		{
			friend.getConnection().send(new ReceiveMessageComposer(connection.getHabbo().getId(), message, 0));
		}
	}

}
