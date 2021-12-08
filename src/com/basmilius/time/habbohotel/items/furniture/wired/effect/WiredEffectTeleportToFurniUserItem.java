package com.basmilius.time.habbohotel.items.furniture.wired.effect;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.enums.SpecialEffects;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredEffectUserItem;
import com.basmilius.time.habbohotel.rooms.RoomUser;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class WiredEffectTeleportToFurniUserItem extends WiredEffectUserItem
{

	private static SecureRandom randomizer;

	static
	{
		WiredEffectTeleportToFurniUserItem.randomizer = new SecureRandom();
	}

	public WiredEffectTeleportToFurniUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredEffectTeleportToFurniUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public boolean handle(final RoomUnit unit, final UserItem item, final String text)
	{
		this.toggleState();

		if (!RoomUser.class.isInstance(unit))
			return false;

		final UserItem teleportItem = (UserItem) this.getLinkedItems().stream().filter(UserItem::getIsWalkable).toArray()[(WiredEffectTeleportToFurniUserItem.randomizer.nextInt(this.getLinkedItems().size()))];
		final RoomUser user = (RoomUser) unit;
		final int previousSpecialEffect = user.getEffectId();

		final Thread t = new Thread(() -> {
			try
			{
				user.setCanWalk(false);
				user.applyEffect(SpecialEffects.SPARKLING);

				Thread.sleep((int) ((440 * 1.5) + ((WiredEffectTeleportToFurniUserItem.this.getValue("delay", 0) / 2) * 1000)));

				user.setPosition(teleportItem.getNode());
				user.setHeight(teleportItem.getTotalHeight());
				user.updateStatus();

				Thread.sleep(440);
				user.applyEffect(previousSpecialEffect);
				user.setCanWalk(true);
			}
			catch (InterruptedException e)
			{
				Bootstrap.getEngine().getLogging().handleException(e);
			}
		});
		t.start();

		return true;
	}

	@Override
	public void save(final ClientMessage packet)
	{
		packet.readInt();
		packet.readString();
		this.saveLinkedItems(packet.readIntArray());
		this.setValue("delay", packet.readInt());
	}

	@Override
	public void serializeWiredData(final ServerMessage response)
	{
		response.appendBoolean(false);
		response.appendInt(5);
		response.appendInt(this.getLinkedItems().size());
		for (final UserItem linkedItem : this.getLinkedItems())
		{
			response.appendInt(linkedItem.getId());
		}
		response.appendInt(this.getBaseItem().getSpriteId());
		response.appendInt(this.getId());
		response.appendString("");
		response.appendInt(0);
		response.appendInt(8);
		response.appendInt(0);
		response.appendInt(this.getValue("delay", 0));
		response.appendInt(0);
		response.appendInt(0);
	}

	@Override
	public boolean onUnitSaysSomething(final RoomUnit unit, final String text, final ChatBubble chatBubble, final boolean isShouted)
	{
		return false;
	}

	@Override
	public void onUnitWalksOnItem(final RoomUnit unit)
	{

	}

	@Override
	public void onUnitWalksOffItem(final RoomUnit unit)
	{

	}
}
