package com.basmilius.time.habbohotel.rooms.trading;

import com.basmilius.time.habbohotel.collections.UserItemsList;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.AddItemComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.RefreshInventoryComposer;
import com.basmilius.time.communication.messages.outgoing.rooms.trading.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TradingSession
{

	private final TradingEngine tradingEngine;

	private final Habbo habboOne;
	private final Habbo habboTwo;

	private boolean habboOneAccepted;
	private boolean habboTwoAccepted;

	private boolean habboOneConfirmed;
	private boolean habboTwoConfirmed;

	private final Map<Habbo, UserItemsList> items;

	public TradingSession(final TradingEngine engine, final Habbo habboOne, final Habbo habboTwo)
	{
		this.tradingEngine = engine;

		this.habboOne = habboOne;
		this.habboTwo = habboTwo;

		this.habboOneAccepted = false;
		this.habboTwoAccepted = false;

		this.items = new HashMap<>();
		this.items.put(this.habboOne, new UserItemsList(new ArrayList<>()));
		this.items.put(this.habboTwo, new UserItemsList(new ArrayList<>()));

		this.setStatus(true);
	}

	public final void accept(final Habbo habbo)
	{
		if (this.getHabboOne() == habbo)
		{
			this.habboOneAccepted = true;
		}
		else if (this.getHabboTwo() == habbo)
		{
			this.habboTwoAccepted = true;
		}
		this.updateAcceptedStatus();

		if (this.habboOneAccepted && this.habboTwoAccepted)
		{
			this.habboOneConfirmed = this.habboTwoConfirmed = false;
			this.send(new TradingConfirmationComposer());
		}
	}

	public final void cancel(final Habbo habbo)
	{
		this.habboOne.getConnection().send(new TradingCloseComposer(habbo != null ? habbo.getId() : 0, false));
		this.habboTwo.getConnection().send(new TradingCloseComposer(habbo != null ? habbo.getId() : 0, false));
		this.setStatus(false);
	}

	public final void cancelConfirm()
	{
		this.tradingEngine.cancel(null, this);
	}

	public final void confirm(final Habbo habbo)
	{
		if (this.getHabboOne() == habbo)
		{
			this.habboOneConfirmed = true;
		}
		else if (this.getHabboTwo() == habbo)
		{
			this.habboTwoConfirmed = true;
		}

		if (this.habboOneConfirmed && this.habboTwoConfirmed)
		{
			this.deliver();
			this.send(new TradingCompletedComposer());
			this.tradingEngine.finalize(this);
		}
	}

	public final void deliver()
	{
		final UserItemsList itemsForHabboOne = this.items.get(this.habboTwo);
		final UserItemsList itemsForHabboTwo = this.items.get(this.habboOne);

		this.deliverItems(this.habboOne, itemsForHabboOne);
		this.deliverItems(this.habboTwo, itemsForHabboTwo);
	}

	public final void deliverItems(final Habbo habbo, UserItemsList items)
	{
		final Map<Integer, UserItemsList> addedItems = new HashMap<>();

		for (final UserItem item : items)
		{
			item.setHabbo(habbo);

			final int tab = (item.getItemExpire() == 0) ? 1 : 2;
			if (!addedItems.containsKey(tab))
			{
				addedItems.put(tab, new UserItemsList(new ArrayList<>()));
			}
			addedItems.get(tab).add(item);
		}

		habbo.getConnection().send(new AddItemComposer(addedItems));
		habbo.getConnection().send(new RefreshInventoryComposer());
	}

	public final Habbo getHabboOne()
	{
		return this.habboOne;
	}

	public final Habbo getHabboTwo()
	{
		return this.habboTwo;
	}

	public final void offerItem(final Habbo habbo, final UserItem item)
	{
		this.items.get(habbo).add(item);
		this.send(new TradingItemsComposer(this.items));

		this.habboOneAccepted = this.habboTwoAccepted = false;
		this.updateAcceptedStatus();
	}

	public final void send(MessageComposer composer)
	{
		this.habboOne.getConnection().send(composer);
		this.habboTwo.getConnection().send(composer);
	}

	public final void setStatus(final boolean isTrading)
	{
		if (!isTrading)
		{
			if (!this.habboOne.getRoomUser().getStatuses().containsKey("trd"))
			{
				this.habboOne.getRoomUser().getStatuses().put("trd", "");
			}

			if (!this.habboTwo.getRoomUser().getStatuses().containsKey("trd"))
			{
				this.habboTwo.getRoomUser().getStatuses().put("trd", "");
			}
		}
		else
		{
			if (this.habboOne.getRoomUser().getStatuses().containsKey("trd"))
			{
				this.habboOne.getRoomUser().getStatuses().remove("trd");
			}

			if (this.habboTwo.getRoomUser().getStatuses().containsKey("trd"))
			{
				this.habboTwo.getRoomUser().getStatuses().remove("trd");
			}
		}

		this.habboOne.getRoomUser().updateStatus();
		this.habboTwo.getRoomUser().updateStatus();
	}

	public final void takeBackItem(final Habbo habbo, final UserItem item)
	{
		if (this.items.get(habbo).contains(item))
		{
			this.items.get(habbo).remove(item);
			this.send(new TradingItemsComposer(this.items));

			this.habboOneAccepted = this.habboTwoAccepted = false;
			this.updateAcceptedStatus();
		}
	}

	public final void updateAcceptedStatus()
	{
		this.send(new TradingAcceptComposer(this.habboOne.getId(), this.habboOneAccepted));
		this.send(new TradingAcceptComposer(this.habboTwo.getId(), this.habboTwoAccepted));
	}

}
