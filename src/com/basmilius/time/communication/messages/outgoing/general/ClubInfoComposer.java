package com.basmilius.time.communication.messages.outgoing.general;

import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ClubInfoComposer extends MessageComposer
{

	private final Habbo habbo;
	private final int packageId;
	private final List<CatalogueItem> items;

	public ClubInfoComposer(Habbo habbo, int packageId, List<CatalogueItem> items)
	{
		this.habbo = habbo;
		this.packageId = packageId;
		this.items = items;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.ClubInfo);
		response.appendInt(this.items.size());
		for (CatalogueItem item : this.items)
		{
			response.appendInt(item.getId());
			response.appendString(item.getName());
			response.appendBoolean(false);
			response.appendInt(item.getCostsCredits());
			response.appendInt(0);
			response.appendInt(0);
			response.appendBoolean(true);

			Calendar cal = Calendar.getInstance();

			if (this.habbo.getSubscriptions().hasSubscription(item.getSubscriptionType()))
			{
				Map<String, Integer> subData = this.habbo.getSubscriptions().getSubscription(item.getSubscriptionType());
				cal.add(Calendar.SECOND, (subData.get("expiration") - subData.get("activated")));
			}

			int days = item.getSubscriptionDays();
			int months = (int) (Math.floor(days / 31));

			cal.add(Calendar.DAY_OF_YEAR, days);

			int endYear = cal.get(Calendar.YEAR);
			int endMonth = (cal.get(Calendar.MONTH) + 1);
			int endDay = cal.get(Calendar.DAY_OF_MONTH);

			response.appendInt(months);
			response.appendInt(days);
			response.appendBoolean(true);
			response.appendInt(days * 11);
			response.appendInt(endYear);
			response.appendInt(endMonth);
			response.appendInt(endDay);
		}
		response.appendInt(this.packageId);
		return response;
	}

}
