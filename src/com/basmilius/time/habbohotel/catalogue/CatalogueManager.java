package com.basmilius.time.habbohotel.catalogue;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.connection.SocketConnection;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.catalogue.layouts.*;
import com.basmilius.time.habbohotel.collections.CataloguePageItemsComparator;
import com.basmilius.time.habbohotel.collections.UserItemsList;
import com.basmilius.time.habbohotel.enums.CatalogueMode;
import com.basmilius.time.habbohotel.enums.HabboValues;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.InteractionType;
import com.basmilius.time.habbohotel.items.Item;
import com.basmilius.time.habbohotel.items.UserItem;
import com.basmilius.time.communication.messages.QueuedComposers;
import com.basmilius.time.communication.messages.outgoing.catalogue.LimitedEditionItemSoldOutComposer;
import com.basmilius.time.communication.messages.outgoing.catalogue.PurchaseNotAllowedComposer;
import com.basmilius.time.communication.messages.outgoing.catalogue.PurchaseOKComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.AddItemComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.FurniListAddOrUpdateComposer;
import com.basmilius.time.communication.messages.outgoing.inventory.RefreshInventoryComposer;
import com.basmilius.time.communication.messages.outgoing.users.NotEnoughCurrencyComposer;
import com.basmilius.time.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CatalogueManager extends IManager
{

	private final List<CataloguePage> pages;
	private TargetedOffer targetedOffer;

	public CatalogueManager()
	{
		this.pages = new ArrayList<>();
	}

	public final void reloadCatalogue()
	{
		this.pages.clear();

		Bootstrap.getEngine().getGame().getHabboManager().setValue(HabboValues.CLIENT_CATALOGUE_OPENED, false);

		Bootstrap.getEngine().getLogging().logNoNewLine(CatalogueManager.class, "Loading catalogue .. ");
		try
		{
			this.loadCataloguePages();
			this.loadCatalogueItems();
			this.loadTargetedOffer();

			Bootstrap.getEngine().getLogging().logOK();
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().logFailed();
			Bootstrap.getEngine().getLogging().handleSQLException(e);
			Bootstrap.getEngine().onLaunchFail();
		}
	}

	private void loadCataloguePages() throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM catalog_pages ORDER BY id");
		{
			if (statement != null)
			{
				final ResultSet result = statement.executeQuery();

				while (result.next())
				{
					switch (result.getString("page_layout"))
					{
						case "badge_display":
							this.pages.add(new BadgeDisplay(result));
							break;
						case "builders_club_addons":
							this.pages.add(new BuildersClubAddons(result));
							break;
						case "builders_club_frontpage":
							this.pages.add(new BuildersClubFrontPage(result));
							break;
						case "bots":
							this.pages.add(new Bots(result));
							break;
						case "club_buy":
							this.pages.add(new ClubBuy(result));
							break;
						case "club_gift":
							this.pages.add(new ClubGifts(result));
							break;
						case "frontpage":
							this.pages.add(new FrontPage(result));
							break;
						case "guild_furni":
							this.pages.add(new GuildFurni(result));
							break;
						case "guild_forum":
							this.pages.add(new GuildForum(result));
							break;
						case "guilds":
							this.pages.add(new Guilds(result));
							break;
						case "info_duckets":
							this.pages.add(new InfoDuckets(result));
							break;
						case "info_pets":
							this.pages.add(new InfoPets(result));
							break;
						case "info_rentables":
							this.pages.add(new InfoRentables(result));
							break;
						case "pets":
							this.pages.add(new Pets(result));
							break;
						case "roomads":
							this.pages.add(new RoomAds(result));
							break;
						case "single_bundle":
							this.pages.add(new SingleBundle(result));
							break;
						case "sold_ltd_items":
							this.pages.add(new SoldLtdItems(result));
							break;
						case "soundmachine":
							this.pages.add(new SoundMachine(result));
							break;
						case "spaces_new":
							this.pages.add(new Spaces(result));
							break;
						case "trophies":
							this.pages.add(new Trophies(result));
							break;
						case "default_3x3":
							this.pages.add(new Default3x3(result));
							break;
					}
				}
			}
		}
	}

	private void loadCatalogueItems() throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM catalog_items ORDER BY id");
		{
			if (statement != null)
			{
				final ResultSet result = statement.executeQuery();

				while (result.next())
				{
					final CatalogueItem item = new CatalogueItem(result);

					if (item.getBaseItems().size() == 0 && item.getSubscriptionType().isEmpty())
					{
						Bootstrap.getEngine().getLogging().logErrorLine(String.format("Warning: CatalogueItem #%d does not have any base items.", item.getId()));
						continue;
					}

					final CataloguePage page = this.getPage(item.getPageId());

					if (page == null)
						continue;

					page.getItems().add(item);
				}

				for (final CataloguePage page : this.pages)
				{
					Collections.sort(page.getItems(), new CataloguePageItemsComparator());
				}
			}
		}
	}

	private void loadTargetedOffer() throws SQLException
	{
		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM `catalog_targetedoffers` WHERE enabled = '1' AND offer_expire > UNIX_TIMESTAMP() LIMIT 1");
		{
			if (statement != null)
			{
				final ResultSet result = statement.executeQuery();

				while(result.next())
				{
					this.targetedOffer = new TargetedOffer(result);
				}
			}
		}
	}

	public final CataloguePage getPage(final int id)
	{
		for (final CataloguePage page : this.pages)
			if (page.getId() == id)
				return page;

		return null;
	}

	public final CataloguePage getPage(final String type)
	{
		for (final CataloguePage page : this.pages)
		{
			if (page.getPageLayout().equals(type))
				return page;
		}
		return null;
	}

	private List<CatalogueItem> getItems()
	{
		List<CatalogueItem> items = new ArrayList<>();

		for (final CataloguePage page : this.pages)
			items.addAll(page.getItems().stream().collect(Collectors.toList()));

		return items;
	}

	public final CatalogueItem getItem(final int id)
	{
		for (final CatalogueItem item : this.getItems())
			if (item.getId() == id)
				return item;

		return null;
	}

	public final CatalogueItem getItemByOffer(final int offerId)
	{
		for (final CatalogueItem item : this.getItems())
			if (item.getBaseItems().size() > 0 && item.getBaseItems().get(0).getOfferId() == offerId)
				return item;

		return null;
	}

	public final CatalogueItem getItemByBase(final Item base)
	{
		return this.getItemByBaseId(base.getId());
	}

	public final CatalogueItem getItemByBaseId(final int id)
	{
		for (final CatalogueItem item : this.getItems())
			if (item.getBaseItems().size() > 0 && item.getBaseItems().get(0).getId() == id)
				return item;

		return null;
	}

	public final List<CataloguePage> getPages(final int parentId, final Habbo habbo, final CatalogueMode mode)
	{
		final List<CataloguePage> pages = new ArrayList<>();

		for (final CataloguePage page : this.pages)
		{
			if (page.getParentId() != parentId)
				continue;

			if (habbo != null && !page.getCanSeePage(habbo))
				continue;

			if (mode != page.getMode() && page.getMode() != CatalogueMode.BUILDER_NORMAL)
				continue;

			pages.add(page);
		}

		Collections.sort(pages);
		return pages;
	}

	public final TargetedOffer getTargetedOffer()
	{
		return this.targetedOffer;
	}

	public final void purchaseItem(final SocketConnection connection, final CataloguePage page, final CatalogueItem item, final String extraData, final int purchaseCount, final GiftWrappingParams giftWrappingParams) throws PurchaseException
	{
		if (item.getPageId() != page.getId())
			return;

		if (item.isSoldOut())
		{
			connection.send(new LimitedEditionItemSoldOutComposer());
			return;
		}

		if (item.getIsClubOnly() && !connection.getHabbo().getSubscriptions().hasSubscription("habbo_club"))
		{
			connection.send(new PurchaseNotAllowedComposer(PurchaseNotAllowedComposer.HC_NEEDED));
			return;
		}

		int totalCredits = 0, totalCurrency = 0;
		final Map<Integer, UserItemsList> itemsBought = new HashMap<>();

		for (int p = 0; p < purchaseCount; p++)
		{
			if (item.getCostsCredits() <= connection.getHabbo().getSettings().getCredits() && item.getCostsCurrency() <= connection.getHabbo().getSettings().getCurrencies().getBalance(item.getCurrencyId()))
			{
				if (item.getCostsCredits() > 0 && !((p + 1) % 6 == 0))
				{
					totalCredits += item.getCostsCredits();
				}
				if (item.getCostsCurrency() > 0 && item.getCurrencyId() == 0 && !((p + 1) % 6 == 0))
				{
					totalCurrency += item.getCostsCurrency();
				}

				if (!item.getSubscriptionType().isEmpty())
				{
					try
					{
						connection.getHabbo().getSubscriptions().createOrExtendSubscription(item.getSubscriptionType(), item.getSubscriptionDays());
					}
					catch (Exception e)
					{
						throw new PurchaseException(e);
					}
				}

				for (int i = 0; i < item.getAmount(); i++)
				{
					for (Item baseItem : item.getBaseItems())
					{
						for (int b = 0; b < item.getBundleAmount().get(baseItem.getId()); b++)
						{
							/*
							 * TODO Buy (permanent) effects.
							 * TODO Buy pets.
							 */
							if (item.getName().startsWith("a0 pet"))
							{
								final String[] data = extraData.split("\n");
								if (data.length == 3)
								{
									final String name = data[0];
									final int race = Integer.parseInt(data[1]);
									final String color = data[2];
									
									if (!StringUtils.isAlphaNum(name))
										return;
									
									if (color.length() != 6)
										return;

									connection.getHabbo().getAchievements().unlockAchievement("ACH_PetLover", 1);
								}
							}
							else if (baseItem.getInteractionType().equals(InteractionType.Teleport))
							{
								final UserItem newItem1 = Bootstrap.getEngine().getGame().getItemsManager().createItem(connection.getHabbo(), null, baseItem, item, connection.getHabbo(), item.getBoughtType(), item.getItemExpire());
								final UserItem newItem2 = Bootstrap.getEngine().getGame().getItemsManager().createItem(connection.getHabbo(), null, baseItem, item, connection.getHabbo(), item.getBoughtType(), item.getItemExpire());

								if (newItem1 == null || newItem2 == null)
									return;
								
								newItem1.setItemLinkedWith(newItem2);
								newItem2.setItemLinkedWith(newItem1);

								this.addOrUpdate(itemsBought, 1, newItem1);
								this.addOrUpdate(itemsBought, 1, newItem2);
							}
							else
							{
								final UserItem newItem = Bootstrap.getEngine().getGame().getItemsManager().createItem(connection.getHabbo(), null, baseItem, item, connection.getHabbo(), item.getBoughtType(), item.getItemExpire());

								if (newItem == null)
									return;
								
								if (!extraData.isEmpty())
								{
									newItem.setOverrideExtraData(extraData);
								}

								this.addOrUpdate(itemsBought, 1, newItem);
							}
						}
					}
				}
			}
			else
			{
				if (item.getCostsCredits() <= connection.getHabbo().getSettings().getCredits())
				{
					connection.send(new NotEnoughCurrencyComposer(true, false));
				}
				else
				{
					connection.send(new NotEnoughCurrencyComposer(true, true, item.getCurrencyId()));
				}
				break;
			}
		}

		if (totalCredits > 0)
		{
			connection.getHabbo().getSettings().updateCredits(-totalCredits);
		}
		if (totalCurrency > 0)
		{
			connection.getHabbo().getSettings().getCurrencies().updateBalance(item.getCurrencyId(), -totalCurrency);
		}

		for (final List<UserItem> items : itemsBought.values())
		{
			for (final UserItem newItem : items)
			{
				if (newItem.getBaseItem().getInteractionType().equalsIgnoreCase(InteractionType.GuildFurni))
				{
					newItem.getServerData().put("guild", newItem.getExtraData());
					newItem.setExtraData("0");
				}

				newItem.saveServerData();
			}
		}

		if (giftWrappingParams != null)
		{
			for (final List<UserItem> items : itemsBought.values())
			{
				for (final UserItem newItem : items)
				{
					newItem.setHabbo(giftWrappingParams.getHabboTo());
					newItem.getServerData().put("present", giftWrappingParams.getJsonData());
					newItem.saveServerData();
				}
			}

			if (giftWrappingParams.getHabboTo().isOnline())
			{
				giftWrappingParams.getHabboTo().getConnection().send(new AddItemComposer(itemsBought));
				giftWrappingParams.getHabboTo().getConnection().send(new RefreshInventoryComposer());
			}
		}
		else if (itemsBought.size() > 0)
		{
			connection.send(new AddItemComposer(itemsBought));

			final QueuedComposers composers = new QueuedComposers();
			for (final int key : itemsBought.keySet())
			{
				for (final UserItem newItem : itemsBought.get(key))
				{
					composers.appendComposer(new FurniListAddOrUpdateComposer(newItem));
				}
			}
			connection.send(composers);
		}

		item.limitedBought();

		connection.send(new PurchaseOKComposer(item));
	}

	private void addOrUpdate(final Map<Integer, UserItemsList> map, final int tab, final UserItem item)
	{
		if (map.containsKey(tab))
		{
			map.get(tab).add(item);
		}
		else
		{
			map.put(tab, new UserItemsList(new ArrayList<>()));
			map.get(tab).add(item);
		}
	}

	@Override
	public final void initialize()
	{
		this.reloadCatalogue();
	}

	@Override
	public final void dispose()
	{
		this.pages.clear();
	}

}
