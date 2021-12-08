package com.basmilius.time.habbohotel.habbo.inventory;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.collections.UserItemsList;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.habbohotel.utils.avatar.AvatarEditorPart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryManager
{

	private final Habbo habbo;
	private final EffectsInventory effects;
	private final Map<String, List<Integer>> clothes;

	private boolean _userItemsLoaded;

	public InventoryManager(Habbo habbo)
	{
		this.habbo = habbo;
		this.clothes = new HashMap<>();
		this.effects = new EffectsInventory(habbo);

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT `part_id`, `part_type` FROM `users_clothing` WHERE `user_id` = ?");
			{
				statement.setInt(1, this.habbo.getId());
				final ResultSet result = statement.executeQuery();
				while (result.next())
				{
					if (!this.clothes.containsKey(result.getString("part_type")))
					{
						this.clothes.put(result.getString("part_type"), new ArrayList<>());
					}
					this.clothes.get(result.getString("part_type")).add(result.getInt("part_id"));
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public final Habbo getHabbo()
	{
		return this.habbo;
	}

	public final void addClothes(List<AvatarEditorPart> parts)
	{
		String queryBuilder = "INSERT IGNORE INTO `users_clothing` (user_id, part_id, part_type) VALUES";

		for (final AvatarEditorPart part : parts)
		{
			if (!this.clothes.containsKey(part.getPartType()))
			{
				this.clothes.put(part.getPartType(), new ArrayList<>());
			}
			this.clothes.get(part.getPartType()).add(part.getPartId());
			queryBuilder += String.format(" (%d, %d, '%s'),", this.habbo.getId(), part.getPartId(), part.getPartType());
		}
		queryBuilder = queryBuilder.substring(0, queryBuilder.length() - 1);

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare(queryBuilder);
			statement.execute();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	public final Map<String, List<Integer>> getClothes()
	{
		return this.clothes;
	}

	public final List<Integer> getClothesPartIds()
	{
		final List<Integer> partIds = new ArrayList<>();

		for (final List<Integer> mapList : this.getClothes().values())
		{
			partIds.addAll(mapList.stream().collect(Collectors.toList()));
		}

		return partIds;
	}

	public final List<String> getClothesPartTypes()
	{
		return this.getClothes().keySet().stream().collect(Collectors.toList());
	}

	public final EffectsInventory getEffectsInventory()
	{
		return this.effects;
	}

	private UserItemsList getInventoryItems()
	{
		return Bootstrap.getEngine().getGame().getItemsManager().getItemsForUser(this.habbo);
	}

	public final UserItem getItem(final int itemId)
	{
		for (UserItem item : getInventoryItems())
		{
			if (item.getId() == itemId)
				return item;
		}
		return null;
	}

	public final UserItemsList getUserItems()
	{
		return this.getInventoryItems();
	}

	public final void dispose()
	{
		this.effects.dispose();
	}

	public final void loadUserItems()
	{
		if(this._userItemsLoaded)
			return;

		this._userItemsLoaded = true;
		Bootstrap.getEngine().getGame().getItemsManager().loadUserItemsForHabbo(habbo);
	}

}
