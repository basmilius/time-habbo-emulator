package com.basmilius.time.communication.messages.incoming.handshake;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.CloseClientReason;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.handshake.DisconnectReasonMessageComposer;
import com.basmilius.time.communication.messages.outgoing.handshake.StartComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.EffectListComposer;
import com.basmilius.time.communication.messages.outgoing.moderation.ModerationToolComposer;
import com.basmilius.time.communication.messages.outgoing.notifications.MinimailCountComposer;
import com.basmilius.time.communication.messages.outgoing.users.FuserightComposer;
import com.basmilius.time.communication.messages.outgoing.users.HomeRoomComposer;

@Event(id = Incoming.SSOTicket)
public class SSOTicketMessageEvent extends MessageEvent
{

	@Override
	public void handle()
	{
		String ssoTicket = packet.readString();

		Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromSessionTicket(ssoTicket);

		if (habbo != null && !habbo.isOnline() && !Bootstrap.getEngine().getGame().getBanManager().isBanned(habbo))
		{
			habbo.setConnection(connection);
			connection.setHabbo(habbo);
			habbo.loadSettings();
			habbo.loadAchievements();
			habbo.loadMessenger();
			habbo.loadSubscriptions();
			habbo.loadInventory();

			Bootstrap.getEngine().getGame().getGuildManager().loadGuildsForHabbo(habbo);

			final QueuedComposers composers = new QueuedComposers();

			composers.appendComposer(new StartComposer());
			composers.appendComposer(new HomeRoomComposer(connection.getHabbo().getSettings().getHomeRoom(), Bootstrap.getEngine().getConfig().getInt("hotel.home.room", 0)));
			composers.appendComposer(new FuserightComposer(connection.getHabbo()));
			composers.appendComposer(new EffectListComposer(habbo.getInventory().getEffectsInventory().getEffects()));
			composers.appendComposer(new MinimailCountComposer(0));

			if (connection.getHabbo().getPermissions().contains("acc_supporttool"))
			{
				composers.appendComposer(new ModerationToolComposer(true, true, true, true, true, true, true));
			}

			connection.send(composers);

			Bootstrap.getEngine().getGame().getRoomManager().preloadRoomsForUser(connection.getHabbo());
		}
		else if (habbo != null && habbo.isOnline())
		{
			connection.send(new StartComposer());
			connection.send(new DisconnectReasonMessageComposer(CloseClientReason.HABBO_ONLINE));
		}
		else if (habbo != null)
		{
			connection.send(new StartComposer());
			connection.send(new DisconnectReasonMessageComposer(CloseClientReason.BANNED));
		}
		else
		{
			connection.send(new StartComposer());
			connection.send(new DisconnectReasonMessageComposer(CloseClientReason.INVALID_SSO));
		}
	}

}
