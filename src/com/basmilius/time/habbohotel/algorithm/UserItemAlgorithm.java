package com.basmilius.time.habbohotel.algorithm;

import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.rooms.pathfinding.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Algorithm for calculating stuff that has something to do with Items.
 * I did not know better words for this ;').
 */
public class UserItemAlgorithm extends IAlgorithm<UserItem>
{

	/**
	 * Constructor.
	 *
	 * @param object The base item.
	 */
	public UserItemAlgorithm(final UserItem object)
	{
		this.setObject(object);
	}

	/**
	 * Gets the items around an item.
	 *
	 * @param skip Skip this item.
	 * @return List
	 */
	public List<UserItem> getItemsAround(final UserItem skip)
	{
		final List<UserItem> skipList = new ArrayList<>();
		skipList.add(skip);
		return this.getItemsAround(skipList);
	}

	/**
	 * Gets the items around an item.
	 *
	 * @param skipList Skip this items.
	 * @return List
	 */
	public List<UserItem> getItemsAround(final List<UserItem> skipList)
	{
		final List<UserItem> itemsAround = new ArrayList<>();

		final int x = this.getObject().getX();
		final int y = this.getObject().getY();

		Node[] nodes = new Node[8];
		nodes[0] = new Node(x - 1, y - 1);
		nodes[1] = new Node(x - 1, y + 1);
		nodes[2] = new Node(x + 1, y - 1);
		nodes[3] = new Node(x + 1, y + 1);
		nodes[4] = new Node(x - 1, y);
		nodes[5] = new Node(x, y - 1);
		nodes[6] = new Node(x + 1, y);
		nodes[7] = new Node(x, y + 1);

		for (final Node node : nodes)
		{
			final List<UserItem> itemsAtNode = this.getObject().getRoom().getRoomObjectsHandler().getFloorItemsAt(node);

			for (final UserItem itemAtNode : itemsAtNode)
			{
				if (skipList.contains(itemAtNode))
					continue;

				itemsAround.add(itemAtNode);
			}
		}

		return itemsAround;
	}

	/**
	 * Removes all the items that do not have a specified interaction type.
	 *
	 * @param items            List with items to filter.
	 * @param interactionTypes List with interaction types to accept.
	 * @return List
	 */
	public List<UserItem> filterItems(final List<UserItem> items, final List<String> interactionTypes)
	{
		final List<UserItem> newItems = new ArrayList<>();

		for (final UserItem item : items)
		{
			if (!interactionTypes.contains(item.getBaseItem().getInteractionType()))
				continue;

			newItems.add(item);
		}

		return newItems;
	}

	/**
	 * Removes all the items that do not have the specified extra data's.
	 *
	 * @param items     List with items to filter.
	 * @param extraData List with extra data's to accept.
	 * @return List
	 */
	public List<UserItem> filterItemsExtraData(final List<UserItem> items, final List<String> extraData)
	{
		final List<UserItem> newItems = new ArrayList<>();

		for (final UserItem item : items)
		{
			if (!extraData.contains(item.getExtraData()))
				continue;

			newItems.add(item);
		}

		return newItems;
	}

}
