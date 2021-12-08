package com.basmilius.time.communication.messages.incoming.items;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.GiftWrappingParams;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.inventory.AddItemComposer;
import com.basmilius.time.communication.messages.outgoing.items.GiftOpenedComposer;

@Event(id = Incoming.RequestOpenGift)
public class RequestOpenGiftEvent extends MessageEvent implements Runnable
{

	private UserItem item;

	@Override
	public void handle() throws Exception
	{
		final int giftId = packet.readInt();

		this.item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(giftId);

		if (this.item == null)
			return;

		if (!this.item.isGift())
			return;

		final GiftWrappingParams giftWrappingParams = this.item.getGift();
		
		if (giftWrappingParams == null)
			return;
		
		giftWrappingParams.setOpened(true);

		this.item.getServerData().put("present", giftWrappingParams.getJsonData());
		this.item.updateAllDataInRoom();

		final Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(2500);

			this.item.getRoom().getRoomObjectsHandler().pickUp(this.item);
			this.item.getServerData().remove("present");
			this.item.saveServerData();

			connection.send(new GiftOpenedComposer(this.item));
			connection.send(new AddItemComposer(this.item));
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
