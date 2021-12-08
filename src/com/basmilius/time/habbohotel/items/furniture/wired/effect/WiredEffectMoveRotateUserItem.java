package com.basmilius.time.habbohotel.items.furniture.wired.effect;

import com.basmilius.time.habbohotel.rooms.RoomUnit;
import com.basmilius.time.habbohotel.enums.BoughtType;
import com.basmilius.time.habbohotel.enums.ChatBubble;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.items.furniture.wired.WiredEffectUserItem;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;
import com.basmilius.time.communication.messages.ClientMessage;
import com.basmilius.time.communication.messages.ServerMessage;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class WiredEffectMoveRotateUserItem extends WiredEffectUserItem
{

	private static final int NO_MOVE = 0;
	private static final int NO_ROTATE = 0;
	private static final int ROTATE_CLOCKWISE = 1;
	private static final int ROTATE_COUNTERCLOCKWISE = 2;
	private static final int ROTATE_RANDOM = 3;
	private static final int MOVE_BOTTOM_LEFT = 6;
	private static final int MOVE_BOTTOM_RIGHT = 5;
	private static final int MOVE_TOP_LEFT = 7;
	private static final int MOVE_TOP_RIGHT = 4;
	private static final int MOVE_TL_BR = 2;
	private static final int MOVE_TR_BL = 3;
	private static final int MOVE_RANDOM = 1;

	public WiredEffectMoveRotateUserItem(final ResultSet result) throws SQLException
	{
		super(result);
	}

	public WiredEffectMoveRotateUserItem(final int habboId, final int roomId, final int itemId, final int catalogueItemId, final int itemBoughtBy, final BoughtType itemBoughtType, final int itemExpire)
	{
		super(habboId, roomId, itemId, catalogueItemId, itemBoughtBy, itemBoughtType, itemExpire);
	}

	@Override
	public boolean handle(final RoomUnit unit, final UserItem item, final String text)
	{
		this.toggleState();

		Thread t = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					if (WiredEffectMoveRotateUserItem.this.getValue("delay", 0) > 0)
					{
						Thread.sleep(((WiredEffectMoveRotateUserItem.this.getValue("delay", 1) / 2) * 1000));
					}

					final int move = WiredEffectMoveRotateUserItem.this.getSetting("random_movement", 0);
					final int rotate = WiredEffectMoveRotateUserItem.this.getSetting("random_rotation", 0);

					for (UserItem linkedItem : WiredEffectMoveRotateUserItem.this.getLinkedItems())
					{
						SecureRandom rnd = new SecureRandom();

						this.move(move, rnd, linkedItem);
						this.rotate(rotate, rnd, linkedItem);
					}
				}
				catch (StackOverflowError | InterruptedException e)
				{
					e.printStackTrace();
				}
			}

			public void move(final int move, final SecureRandom rnd, final UserItem linkedItem)
			{
				if (move == NO_MOVE)
					return;

				Node node = new Node(linkedItem.getX(), linkedItem.getY());

				if (move == MOVE_BOTTOM_LEFT)
					node = linkedItem.getNearNode(new Node(0, 1));
				else if (move == MOVE_BOTTOM_RIGHT)
					node = linkedItem.getNearNode(new Node(1, 0));
				else if (move == MOVE_TOP_LEFT)
					node = linkedItem.getNearNode(new Node(-1, 0));
				else if (move == MOVE_TOP_RIGHT)
					node = linkedItem.getNearNode(new Node(0, -1));
				else if (move == MOVE_TL_BR)
				{
					if (rnd.nextInt(10) >= 5)
						node = linkedItem.getNearNode(new Node(-1, 0));
					else
						node = linkedItem.getNearNode(new Node(1, 0));
				}
				else if (move == MOVE_TR_BL)
				{
					if (rnd.nextInt(10) >= 5)
						node = linkedItem.getNearNode(new Node(0, -1));
					else
						node = linkedItem.getNearNode(new Node(0, 1));
				}
				else if (move == MOVE_RANDOM)
				{
					int r = rnd.nextInt(7);

					if (r == 0)
						node = linkedItem.getNearNode(new Node(0, 1));
					else if (r == 1)
						node = linkedItem.getNearNode(new Node(1, 0));
					else if (r == 2)
						node = linkedItem.getNearNode(new Node(-1, 0));
					else if (r == 3)
						node = linkedItem.getNearNode(new Node(0, -1));
					else if (r == 4)
						node = linkedItem.getNearNode(new Node(1, 1));
					else if (r == 5)
						node = linkedItem.getNearNode(new Node(-1, -1));
					else if (r == 6)
						node = linkedItem.getNearNode(new Node(1, -1));
					else if (r == 7)
						node = linkedItem.getNearNode(new Node(-1, 1));
				}

				/*
		         * Set the last false to true for furni in furni bug fix
				 */
				if (WiredEffectMoveRotateUserItem.this.getRoom().getRoomData().getRoomModel().isValidNode(node) && !WiredEffectMoveRotateUserItem.this.getRoom().getRoomObjectsHandler().isSomethingOnNode(node))
				{
					linkedItem.rollTo(node, WiredEffectMoveRotateUserItem.this.getRoom().getRoomObjectsHandler().getStackHeight(node, linkedItem), null);
					WiredEffectMoveRotateUserItem.this.getRoom().getRoomObjectsHandler().updateGameMap();
				}
				else
				{
					this.move(move, rnd, linkedItem);
				}
			}

			public void rotate(final int rotate, final SecureRandom rnd, final UserItem linkedItem)
			{
				//if (rotate == NO_ROTATE)
				//	return;

				/*
				 * TODO: Rotate furniture.
				 */
			}

		});
		t.start();

		return true;
	}

	@Override
	public void save(final ClientMessage packet)
	{
		this.hasWiredServerData();

		final int[] settings = packet.readIntArray();
		this.setSetting("random_movement", settings.length > 0 ? settings[0] : 0);
		this.setSetting("random_rotation", settings.length > 1 ? settings[1] : 0);
		
		packet.readString();

		final int[] items = packet.readIntArray();
		this.saveLinkedItems(items);
		
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
		response.appendInt(2);
		response.appendInt(this.getSetting("random_movement", 0));
		response.appendInt(this.getSetting("random_rotation", 0));
		response.appendInt(0);
		response.appendInt(4);
		response.appendInt(this.getValue("delay", 0));
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
