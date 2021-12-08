package com.basmilius.time.communication.messages.incoming.notifications;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.notifications.MessagesOfTheDayComposer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Event(id = Incoming.GetMessageOfTheDay)
public class GetMessageOffTheDayEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (Bootstrap.getEngine().getConfig().getBoolean("hotel.motd.enabled", false))
		{
			final String fileName = Bootstrap.getEngine().getConfig().getString("hotel.motd.file", "");

			if (!fileName.isEmpty())
			{
				List<String> motd = Files.readAllLines(Paths.get((new File(fileName).getAbsolutePath())));
				connection.send(new MessagesOfTheDayComposer(motd));
			}
		}

		/**
		 * TODO: Handle these the proper way.
		 * client.send(new LocalizedNotificationComposer.BuildersClubMembershipExpiredLocalizedNotificationComposer());
		 * client.send(new LocalizedNotificationComposer.BuildersClubMembershipExpiresLocalizedNotificationComposer());
		 * client.send(new LocalizedNotificationComposer.BuildersClubMembershipExtendedLocalizedNotificationComposer(1000));
		 * client.send(new LocalizedNotificationComposer.BuildersClubMembershipMadeLocalizedNotificationComposer(1000));
		 * client.send(new LocalizedNotificationComposer.BuildersClubMembershipRenewedLocalizedNotificationComposer(1000));
		 * client.send(new LocalizedNotificationComposer.BuildersClubRoomLockedLocalizedNotificationComposer());
		 * client.send(new LocalizedNotificationComposer.BuildersClubVisitDeniedForOwnerLocalizedNotificationComposer("Bas"));
		 * client.send(new LocalizedNotificationComposer.BuildersClubVisitDeniedForVisitorLocalizedNotificationComposer());
		 * client.send(new LocalizedNotificationComposer.ForumErrorAccessDeniedComposer());
		 * client.send(new LocalizedNotificationComposer.ForumSettingsUpdatedComposer());
		 * client.send(new LocalizedNotificationComposer.ForumMessageHiddenComposer());
		 * client.send(new LocalizedNotificationComposer.ForumMessageRestoredComposer());
		 * client.send(new LocalizedNotificationComposer.ForumThreadHiddenComposer());
		 * client.send(new LocalizedNotificationComposer.ForumThreadLockedComposer());
		 * client.send(new LocalizedNotificationComposer.ForumThreadPinnedComposer());
		 * client.send(new LocalizedNotificationComposer.ForumThreadRestoredComposer());
		 * client.send(new LocalizedNotificationComposer.ForumThreadUnLockedComposer());
		 * client.send(new LocalizedNotificationComposer.ForumThreadUnPinnedComposer());
		 * client.send(new LocalizedNotificationComposer.RoomKickCannonballLocalizedNotificationComposer());
		 * client.send(new LocalizedNotificationComposer.PurchasingRoomLocalizedNotificationComposer(1, "Ontvangstruimte"));
		 */

		//client.send(new LocalizedNotificationComposer.TimeWelcomeLocalizedNotificationComposer());
	}

}
