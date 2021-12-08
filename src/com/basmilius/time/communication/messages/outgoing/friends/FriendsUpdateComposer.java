package com.basmilius.time.communication.messages.outgoing.friends;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.habbo.messenger.Friend;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.List;

public class FriendsUpdateComposer extends MessageComposer
{

	private final Habbo habbo;
	private final List<Habbo> habbos;
	private final List<Habbo> addedHabbos;
	private final List<Habbo> deleteHabbos;

	public FriendsUpdateComposer(Habbo habbo, List<Habbo> habbos, List<Habbo> addedHabbos, List<Habbo> deleteHabbos)
	{
		this.habbo = habbo;
		this.habbos = habbos;
		this.addedHabbos = addedHabbos;
		this.deleteHabbos = deleteHabbos;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.FriendUpdate);
		if (Bootstrap.getEngine().getConfig().getString("hotel.messenger.friends.category.staff", "").isEmpty())
		{
			response.appendInt(0);
		}
		else
		{
			response.appendInt(1);
			response.appendInt(1);
			response.appendString(Bootstrap.getEngine().getConfig().getString("hotel.messenger.friends.category.staff", ""));
		}
		response.appendInt(this.habbos.size());
		for (final Habbo habbo : this.habbos)
		{
			if (this.deleteHabbos.contains(habbo))
			{
				response.appendInt(-1);
				response.appendInt(habbo.getId());
			}
			else
			{
				Friend friend = this.habbo.getMessenger().getFriend(habbo);
				response.appendInt((this.addedHabbos.contains(habbo)) ? 1 : 0);
				response.appendInt(habbo.getId());
				response.appendString(habbo.getUsername());
				response.appendInt((habbo.getGender().equals("M")) ? 0 : 1);
				response.appendBoolean(habbo.isOnline());
				response.appendBoolean(habbo.isInRoom());
				response.appendString(habbo.getLook());
				response.appendInt((habbo.isOnline() && habbo.getPermissions() != null && habbo.getPermissions().contains("is_staff")) ? 1 : 0);
				response.appendString(habbo.getMotto());
				response.appendString(""); // facebook name
				response.appendString(habbo.getRealName());
				response.appendBoolean(Bootstrap.getEngine().getConfig().getBoolean("hotel.messenger.offline.messaging.enabled", false));
				response.appendBoolean(false);
				response.appendBoolean(false);
				response.appendShort((friend != null) ? friend.getRelation() : 0);
			}
		}
		return response;
	}

}
