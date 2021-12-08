package com.basmilius.time.communication.messages.incoming.catalog;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.TargetedOffer;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.items.Item;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.inventory.AddItemComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.FurniListAddOrUpdateComposer;

@Event(id = Incoming.PurchaseTargetedOffer)
public class PurchaseTargetedOfferEvent extends MessageEvent
{

	@Override
	public final void handle() throws Exception
	{
		final int offerId = packet.readInt();
		final TargetedOffer targetedOffer = Bootstrap.getEngine().getGame().getCatalogueManager().getTargetedOffer();

		if (targetedOffer.getId() == offerId)
		{
			if (!targetedOffer.getBadge().isEmpty())
			{
				Bootstrap.getEngine().getGame().getBadgeManager().addBadge(connection.getHabbo(), targetedOffer.getBadge(), 0, true);
			}

			if (targetedOffer.getItems().size() > 0)
			{
				final QueuedComposers composers = new QueuedComposers();
				
				for (final Item item : targetedOffer.getItems())
				{
					final UserItem newItem  = Bootstrap.getEngine().getGame().getItemsManager().createItem(connection.getHabbo(), null, item, Bootstrap.getEngine().getGame().getCatalogueManager().getItemByBase(item), connection.getHabbo(), BoughtType.NORMAL, 0);
					composers.appendComposer(new AddItemComposer(newItem));
					composers.appendComposer(new FurniListAddOrUpdateComposer(newItem));
				}

				connection.send(composers);
			}

			if (targetedOffer.getSubscriptionTime() > 0 && !targetedOffer.getSubscriptionType().isEmpty())
			{
				connection.getHabbo().getSubscriptions().createOrExtendSubscription(targetedOffer.getSubscriptionType(), targetedOffer.getSubscriptionTime(), false);
			}

			connection.getHabbo().getSettings().updateCredits(-targetedOffer.getCost());
			connection.getHabbo().getSettings().getCurrencies().updateBalance(targetedOffer.getCurrencyId(), -targetedOffer.getCost());
		}
	}

}
