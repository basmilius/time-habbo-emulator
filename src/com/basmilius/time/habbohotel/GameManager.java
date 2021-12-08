package com.basmilius.time.habbohotel;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.collections.ManagersList;
import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.achievements.AchievementsManager;
import com.basmilius.time.habbohotel.badges.BadgeManager;
import com.basmilius.time.habbohotel.bots.BotManager;
import com.basmilius.time.habbohotel.catalogue.CatalogueManager;
import com.basmilius.time.habbohotel.commands.ChatCommandsManager;
import com.basmilius.time.habbohotel.events.CommunityEventManager;
import com.basmilius.time.habbohotel.guilds.GuildManager;
import com.basmilius.time.habbohotel.habbo.HabboManager;
import com.basmilius.time.habbohotel.habbohelpers.HabboHelpersManager;
import com.basmilius.time.habbohotel.items.ItemsManager;
import com.basmilius.time.habbohotel.items.soundmachine.SoundMachineManager;
import com.basmilius.time.habbohotel.moderation.ModerationManager;
import com.basmilius.time.habbohotel.navigator.NavigatorManager;
import com.basmilius.time.habbohotel.pets.PetManager;
import com.basmilius.time.habbohotel.poll.PollManager;
import com.basmilius.time.habbohotel.quests.QuestsManager;
import com.basmilius.time.habbohotel.reception.ReceptionManager;
import com.basmilius.time.habbohotel.roles.BanManager;
import com.basmilius.time.habbohotel.roles.PermissionsManager;
import com.basmilius.time.habbohotel.rooms.RoomManager;
import com.basmilius.time.habbohotel.rooms.competition.RoomCompetitionManager;
import com.basmilius.time.habbohotel.utils.worker.LowLevelWorkerManager;
import com.basmilius.time.habbohotel.wordfilter.WordFilterManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameManager extends IManager
{

	private final ManagersList managers;

	public GameManager() throws Exception
	{
		this.managers = new ManagersList(new ArrayList<>());

		this.addManager(BadgeManager.class);
		this.addManager(BanManager.class);
		this.addManager(BotManager.class);
		this.addManager(ItemsManager.class);
		this.addManager(CatalogueManager.class);
		this.addManager(CommunityEventManager.class);
		this.addManager(GuildManager.class);
		this.addManager(HabboManager.class);
		this.addManager(HabboHelpersManager.class);
		this.addManager(NavigatorManager.class);
		this.addManager(PermissionsManager.class);
		this.addManager(ReceptionManager.class);
		this.addManager(RoomManager.class);
		this.addManager(SoundMachineManager.class);
		this.addManager(ModerationManager.class);
		this.addManager(WordFilterManager.class);
		this.addManager(AchievementsManager.class);
		this.addManager(PollManager.class);
		this.addManager(QuestsManager.class);
		this.addManager(RoomCompetitionManager.class);
		this.addManager(ChatCommandsManager.class);
		this.addManager(PetManager.class);
		this.addManager(LowLevelWorkerManager.class);
	}

	public void addManager(Class<? extends IManager> manager) throws IllegalAccessException, InstantiationException
	{
		this.managers.add(manager.newInstance());
	}

	@Override
	public void initialize() throws Exception
	{
		for (IManager manager : this.managers)
		{
			manager.initialize();
		}

		try
		{
			final PreparedStatement ouStatement = Bootstrap.getEngine().getDatabase().prepare("UPDATE users SET online = '0'");
			{
				if (ouStatement != null)
				{
					ouStatement.executeUpdate();
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends IManager> T getManager(Class<T> manager)
	{
		for (IManager managerInstance : this.managers)
			if (manager.isInstance(managerInstance))
			{
				return (T) managerInstance;
			}
		return null;
	}

	public final AchievementsManager getAchievementsManager()
	{
		return this.getManager(AchievementsManager.class);
	}

	public final BadgeManager getBadgeManager()
	{
		return this.getManager(BadgeManager.class);
	}

	public final BanManager getBanManager()
	{
		return this.getManager(BanManager.class);
	}

	public final BotManager getBotManager()
	{
		return this.getManager(BotManager.class);
	}

	public final CatalogueManager getCatalogueManager()
	{
		return this.getManager(CatalogueManager.class);
	}

	public final ChatCommandsManager getChatCommands()
	{
		return this.getManager(ChatCommandsManager.class);
	}

	public final CommunityEventManager getCommunityEventManager()
	{
		return this.getManager(CommunityEventManager.class);
	}

	public final GuildManager getGuildManager()
	{
		return this.getManager(GuildManager.class);
	}

	public final HabboManager getHabboManager()
	{
		return this.getManager(HabboManager.class);
	}

	public final HabboHelpersManager getHabboHelpersManager()
	{
		return this.getManager(HabboHelpersManager.class);
	}

	public final ItemsManager getItemsManager()
	{
		return this.getManager(ItemsManager.class);
	}

	public final ModerationManager getModerationManager()
	{
		return this.getManager(ModerationManager.class);
	}

	public final NavigatorManager getNavigatorManager()
	{
		return this.getManager(NavigatorManager.class);
	}

	public final PermissionsManager getPermissionsManager()
	{
		return this.getManager(PermissionsManager.class);
	}

	public final PetManager getPetManager()
	{
		return this.getManager(PetManager.class);
	}

	public final PollManager getPollManager()
	{
		return this.getManager(PollManager.class);
	}
	
	public final QuestsManager getQuestsManager()
	{
		return this.getManager(QuestsManager.class);
	}

	public final ReceptionManager getReceptionManager()
	{
		return this.getManager(ReceptionManager.class);
	}

	public final RoomCompetitionManager getRoomCompetitionManager()
	{
		return this.getManager(RoomCompetitionManager.class);
	}

	public final RoomManager getRoomManager()
	{
		return this.getManager(RoomManager.class);
	}

	public final SoundMachineManager getSoundMachineManager()
	{
		return this.getManager(SoundMachineManager.class);
	}

	public final WordFilterManager getWordFilterManager()
	{
		return this.getManager(WordFilterManager.class);
	}

	@Override
	public void dispose()
	{
		for (final IManager manager : this.managers)
		{
			Bootstrap.getEngine().getLogging().log(GameManager.class, String.format("Disposing %s ..", manager.getClass().getName()));
			manager.dispose();
			Bootstrap.getEngine().getLogging().log(GameManager.class, String.format("Disposed %s.", manager.getClass().getName()));
		}
		this.managers.clear();

		Bootstrap.getEngine().getLogging().log(GameManager.class, "Game Manager disposed!");
	}

}
