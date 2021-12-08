package com.basmilius.time.communication.messages.incoming.users;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.catalogue.CatalogueItem;
import com.basmilius.time.habbohotel.items.InteractionType;
import com.basmilius.time.habbohotel.items.Item;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.utils.avatar.AvatarEditorFigureString;
import com.basmilius.time.habbohotel.utils.avatar.AvatarEditorPart;
import com.basmilius.time.communication.messages.Event;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.optcodes.Incoming;
import com.basmilius.time.communication.messages.outgoing.users.AvatarEditorFigureSetIdsComposer;

import java.util.List;

@Event(id = Incoming.AvatarEditorUseClothes)
public class AvatarEditorUseClothesEvent extends MessageEvent
{

	@Override
	public void handle() throws Exception
	{
		final int itemId = packet.readInt();
		final UserItem item = Bootstrap.getEngine().getGame().getItemsManager().getUserItem(itemId);
		
		if (item == null)
			return;
		
		final CatalogueItem catalogueItem = item.getCatalogItem();
		final Item baseItem = item.getBaseItem();

		if (!baseItem.getInteractionType().equalsIgnoreCase(InteractionType.SellableClothes) || catalogueItem.getExtraData().isEmpty())
			return;

		String clothingData = catalogueItem.getExtraData();
		if (clothingData.contains("m:") && clothingData.contains("f:"))
		{
			final String[] clothingDataGender = clothingData.split("&");
			if (connection.getHabbo().getGender().equalsIgnoreCase("M"))
			{
				clothingData = ((clothingDataGender[0].startsWith("m:")) ? clothingDataGender[0].substring(2) : clothingDataGender[1].substring(2));
			}
			else
			{
				clothingData = ((clothingDataGender[0].startsWith("f:")) ? clothingDataGender[0].substring(2) : clothingDataGender[1].substring(2));
			}
		}

		final List<AvatarEditorPart> parts = AvatarEditorFigureString.getParts(clothingData);
		connection.getHabbo().getInventory().addClothes(parts);

		connection.send(new AvatarEditorFigureSetIdsComposer(connection.getHabbo().getInventory().getClothesPartIds(), connection.getHabbo().getInventory().getClothesPartTypes()));

		Thread t = new Thread(() -> {
			try
			{
				item.setExtraData("1");
				item.updateStateInRoom();

				Thread.sleep(1000);

				item.delete();
			}
			catch (InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		});
		t.start();
	}

}
