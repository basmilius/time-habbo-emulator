package com.basmilius.time.communication.messages.incoming.handshake;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.Currencies;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.catalogue.TargetedOfferDataComposer;
import com.basmilius.time.communication.messages.outgoing.handshake.EnableNotificationsComposer;
import com.basmilius.time.communication.messages.outgoing.handshake.EnableTradingComposer;
import com.basmilius.time.communication.messages.outgoing.handshake.MachineIdComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.AchievementScoreComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.GenericAlertComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.MinimailCountComposer;
import com.basmilius.time.communication.messages.outgoing.users.*;

@Event(id = Incoming.RequestUserData)
public class RequestUserDataEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		if (!connection.hasHabbo())
			return;

		final QueuedComposers composers = new QueuedComposers();

		composers.appendComposer(new MachineIdComposer(connection.getHabbo().getIdentifier()));
		composers.appendComposer(new MinimailCountComposer(0));
		composers.appendComposer(new EnableNotificationsComposer());
		composers.appendComposer(new EnableTradingComposer());
		composers.appendComposer(new CurrencyComposer(connection.getHabbo()));
		composers.appendComposer(new FuserightComposer(connection.getHabbo()));
		composers.appendComposer(new UserInfoComposer(connection));
		composers.appendComposer(new PerkAllowancesComposer(connection));
		composers.appendComposer(new AchievementScoreComposer(connection.getHabbo().getSettings().getAchievementScore()));
		composers.appendComposer(new AvatarEditorFigureSetIdsComposer(connection.getHabbo().getInventory().getClothesPartIds(), connection.getHabbo().getInventory().getClothesPartTypes()));

		try
		{
			if (Bootstrap.getEngine().getConfig().getBoolean("hotel.welcome.alert.enabled"))
			{
				composers.appendComposer(new GenericAlertComposer(Bootstrap.getEngine().getConfig().getString("hotel.welcome.alert.message", "").replace("%USER%", connection.getHabbo().getUsername())));
			}
		}
		catch (Exception e)
		{
			Bootstrap.getEngine().getLogging().handleException(e);
		}

		if (Bootstrap.getEngine().getGame().getCatalogueManager().getTargetedOffer() != null)
		{
			composers.appendComposer(new TargetedOfferDataComposer(Bootstrap.getEngine().getGame().getCatalogueManager().getTargetedOffer()));
		}

		connection.send(composers);

		Thread t = new Thread(() -> {
			try
			{
				Thread.sleep(3000);
			}
			catch (InterruptedException ignored)
			{

			}

			if (!connection.getSocket().isOpen())
				return;

			connection.getHabbo().getSettings().getCurrencies().updateBalance(Currencies.DUCKETS, 10);
		});
		t.start();
	}

}
