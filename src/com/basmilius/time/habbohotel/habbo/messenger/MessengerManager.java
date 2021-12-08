package com.basmilius.time.habbohotel.habbo.messenger;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.enums.HabboValues;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.outgoing.friends.FriendsUpdateComposer;
import com.basmilius.time.communication.messages.outgoing.friends.ReceiveMessageComposer;
import com.basmilius.time.util.TimeUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class manages the Friends of a Habbo.
 * Friend requests and updates are also managed here.
 */
public class MessengerManager
{

	private final Habbo habbo;

	private final MessengerCache cache;
	private final List<Friend> friends;
	private final List<FriendRequest> friendRequests;
	private final List<Friend> friendsToUpdate;

	/**
	 * Constructor.
	 *
	 * @param habbo The Habbo to work with.
	 */
	public MessengerManager(final Habbo habbo)
	{
		this.habbo = habbo;

		this.cache = new MessengerCache(this);
		this.friends = new ArrayList<>();
		this.friendRequests = new ArrayList<>();
		this.friendsToUpdate = new ArrayList<>();

		this.loadFriends();
		this.loadFriendRequests();
	}

	/**
	 * Loads all the Habbo's friends.
	 */
	private void loadFriends()
	{
		this.friends.clear();

		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM messenger_friendships WHERE user_one_id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, habbo.getId());
					final ResultSet result = statement.executeQuery();

					while (result.next())
					{
						final Habbo habboFriend = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_two_id"));
						final Friend newFriend = new Friend(habbo, habboFriend, result.getInt("relation"));

						if (this.isFriendWith(habboFriend))
							continue;

						this.friends.add(newFriend);
					}
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	/**
	 * Loads all the friend requests for this Habbo.
	 */
	public void loadFriendRequests()
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM messenger_friendrequests WHERE user_to_id = ?");
			{
				if (statement != null)
				{
					statement.setInt(1, habbo.getId());
					final ResultSet result = statement.executeQuery();

					while (result.next())
					{
						this.friendRequests.add(new FriendRequest(this.habbo, Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("user_from_id"))));
					}
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	/**
	 * Gets the messenger cache. Offline messaging etc.
	 *
	 * @return MessengerCache
	 */
	public MessengerCache getCache()
	{
		return this.cache;
	}

	/**
	 * Gets the Habbos friends. We COPY the list to prevent compatibility issues.
	 *
	 * @return List
	 */
	public List<Friend> getFriends()
	{
		return new ArrayList<>(this.friends);
	}

	/**
	 * Gets the friend requests for this Habbo. We COPY the list to prevent compatibility issues.
	 *
	 * @return List
	 */
	public List<FriendRequest> getFriendRequests()
	{
		return new ArrayList<>(this.friendRequests);
	}

	/**
	 * Gets the Friends of this Habbo that need an update.
	 *
	 * @return List
	 */
	public List<Friend> getFriendsToUpdate()
	{
		return new ArrayList<>(this.friendsToUpdate);
	}

	/**
	 * Gets all the friends of this Habbo that have a special relation.
	 *
	 * @param relationId Relation ID.
	 * @return List
	 */
	public List<Friend> getFriendsWithRelation(final int relationId)
	{
		return this.getFriends().stream().filter(friend -> friend.getRelation() == relationId).collect(Collectors.toList());
	}

	/**
	 * Gets a Friend object from a Habbo object if the specified Habbo is a Friend of this Habbo.
	 *
	 * @param habbo The Habbo.
	 * @return Friend
	 */
	public Friend getFriend(final Habbo habbo)
	{
		return this.getFriend(habbo.getId());
	}

	/**
	 * Gets a Friend object from a Habbos ID if the specified Habbos ID is a Friend of this Habbo.
	 *
	 * @param habboId The Habbos ID.
	 * @return Friend
	 */
	public Friend getFriend(final int habboId)
	{
		for (final Friend friend : this.getFriends())
			if (friend.getHabbo().getId() == habboId)
				return friend;

		return null;
	}

	/**
	 * Gets the search results from a search query.
	 *
	 * @param query Query-string.
	 * @return Map
	 * @throws SQLException
	 */
	public Map<String, List<Habbo>> getSearchByQuery(final String query) throws SQLException
	{
		final Map<String, List<Habbo>> results = new HashMap<>();

		results.put("friends", new ArrayList<>());
		results.put("strangers", new ArrayList<>());

		final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("SELECT * FROM users WHERE username LIKE ? LIMIT 100");
		{
			if (statement != null)
			{
				statement.setString(1, "%" + query + "%");
				final ResultSet result = statement.executeQuery();

				while (result.next())
				{
					if (habbo.getMessenger().isFriendWith(result.getInt("id")))
					{
						results.get("friends").add(Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("id")));
					}
					else
					{
						results.get("strangers").add(Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(result.getInt("id")));
					}
				}
			}
		}

		return results;
	}

	/**
	 * Checks if the specified Habbo is a Friend of this Habbo.
	 *
	 * @param habbo The Habbo.
	 * @return boolean
	 */
	public boolean isFriendWith(final Habbo habbo)
	{
		return this.isFriendWith(habbo.getId());
	}

	/**
	 * Checks if the specified Habbos ID is a Friend of this Habbo.
	 *
	 * @param habboId The Habbos ID.
	 * @return boolean
	 */
	public boolean isFriendWith(final int habboId)
	{
		for (final Friend friend : this.getFriends())
			if (friend.getHabbo().getId() == habboId)
				return true;

		return false;
	}

	/**
	 * Checks if the specified user has requested this Habbo to be friends.
	 *
	 * @param habbo The Habbo.
	 * @return boolean
	 */
	public boolean isHabboRequested(final Habbo habbo)
	{
		for (final FriendRequest request : this.getFriendRequests())
			if (request.getRequesterHabbo().getId() == habbo.getId())
				return true;

		return false;
	}

	/**
	 * Adds a Friend Request into the Database.
	 *
	 * @param habboTo The Habbo to send it to.
	 * @return boolean
	 */
	public boolean addFriendRequest(final Habbo habboTo)
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO messenger_friendrequests (user_from_id, user_to_id) VALUES (?, ?)");
			{
				if (statement != null)
				{
					statement.setInt(1, this.habbo.getId());
					statement.setInt(2, habboTo.getId());
					statement.execute();

					return true;
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}

		return false;
	}

	/**
	 * Deletes a Friend Request from the Database.
	 *
	 * @param habbo The Habbo which request will be removed.
	 */
	public void deleteFriendRequest(final Habbo habbo)
	{
		final List<FriendRequest> requests = this.getFriendRequests();

		this.getFriendRequests().stream().filter(request -> request.getRequesterHabbo().getId() == habbo.getId()).forEach(requests::remove);

		this.friendRequests.clear();
		this.friendRequests.addAll(requests);
	}

	/**
	 * Deletes a Friendship with the specified Habbo.
	 *
	 * @param habbo The Habbo to delete as a Friend.
	 * @return boolean
	 */
	public boolean deleteFriendship(final Habbo habbo)
	{
		try
		{
			final PreparedStatement statement = Bootstrap.getEngine().getDatabase().prepare("DELETE FROM messenger_friendships WHERE (user_one_id = ? AND user_two_id = ?) OR (user_two_id = ? AND user_one_id = ?)");
			{
				if (statement != null)
				{
					statement.setInt(1, this.habbo.getId());
					statement.setInt(2, habbo.getId());
					statement.setInt(3, this.habbo.getId());
					statement.setInt(4, habbo.getId());
					statement.execute();

					return true;
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}

		return false;
	}

	/**
	 * Disposes this Manager.
	 */
	public void dispose()
	{
		this.friends.clear();
		this.friendRequests.clear();
		this.friendsToUpdate.clear();
	}

	/**
	 * If there are offline messages, this function sends it all.
	 */
	public void handleOfflineMessages()
	{
		for (final Map<Habbo, Map<String, Integer>> message : this.cache.getOfflineMessages())
		{
			final Habbo habboFrom = (Habbo) message.keySet().toArray()[0];
			final String msg = (String) message.get(habboFrom).keySet().toArray()[0];
			final int time = (TimeUtils.getUnixTimestamp() - message.get(habboFrom).get(msg));

			this.habbo.getConnection().send(new ReceiveMessageComposer(habboFrom.getId(), msg, time));
		}
		this.cache.clearOfflineMessages();
	}

	/**
	 * Handles a friend request.
	 *
	 * @param habboFromId The Habbos ID who sended this request.
	 * @param accepted    Is the request accepted?
	 */
	public void handleRequest(final int habboFromId, final boolean accepted)
	{
		final Habbo habbo = Bootstrap.getEngine().getGame().getHabboManager().getHabboFromId(habboFromId);

		if (habbo != null)
		{
			try
			{
				final PreparedStatement deleteRequest = Bootstrap.getEngine().getDatabase().prepare("DELETE FROM messenger_friendrequests WHERE user_from_id = ? AND user_to_id = ?");
				{
					if (deleteRequest != null)
					{
						deleteRequest.setInt(1, habbo.getId());
						deleteRequest.setInt(2, this.habbo.getId());
						deleteRequest.execute();
					}
				}

				this.deleteFriendRequest(habbo);

				if (accepted)
				{
					final PreparedStatement createFriendship1 = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO messenger_friendships (user_one_id, user_two_id, friends_since) VALUES (?, ?, UNIX_TIMESTAMP())");
					{
						if (createFriendship1 != null)
						{
							createFriendship1.setInt(1, habbo.getId());
							createFriendship1.setInt(2, this.habbo.getId());
							createFriendship1.execute();
						}
					}
					final PreparedStatement createFriendship2 = Bootstrap.getEngine().getDatabase().prepare("INSERT INTO messenger_friendships (user_one_id, user_two_id, friends_since) VALUES (?, ?, UNIX_TIMESTAMP())");
					{
						if (createFriendship2 != null)
						{
							createFriendship2.setInt(1, this.habbo.getId());
							createFriendship2.setInt(2, habbo.getId());
							createFriendship2.execute();
						}
					}

					final List<Habbo> habbosToAdd1 = new ArrayList<>();
					final List<Habbo> habbosToAdd2 = new ArrayList<>();

					habbosToAdd1.add(habbo);
					habbosToAdd2.add(this.habbo);

					Friend friend1 = new Friend(this.habbo, habbo, 0);
					this.friends.add(friend1);
					this.habbo.getConnection().send(new FriendsUpdateComposer(this.habbo, habbosToAdd1, habbosToAdd1, new ArrayList<>()));

					if (habbo.isOnline())
					{
						Friend friend2 = new Friend(habbo, this.habbo, 0);
						habbo.getMessenger().friends.add(friend2);
						habbo.getConnection().send(new FriendsUpdateComposer(habbo, habbosToAdd2, habbosToAdd2, new ArrayList<>()));
					}
				}
			}
			catch (SQLException e)
			{
				Bootstrap.getEngine().getLogging().handleSQLException(e);
			}
		}
	}

	/**
	 * Simply removes all requests.
	 */
	public final void removeAllRequests()
	{
		try
		{
			final PreparedStatement deleteRequest = Bootstrap.getEngine().getDatabase().prepare("DELETE FROM messenger_friendrequests WHERE user_to_id = ?");
			{
				if (deleteRequest != null)
				{
					deleteRequest.setInt(1, this.habbo.getId());
					deleteRequest.execute();
				}
			}
		}
		catch (SQLException e)
		{
			Bootstrap.getEngine().getLogging().handleSQLException(e);
		}
	}

	/**
	 * Sends an update to every friend. Like if this Habbo enters any room.
	 */
	public void sendUpdate()
	{
		for (final Friend f : this.getFriends())
		{
			if (f.getHabbo().isOnline() && f.getHabbo().getValues().containsKey(HabboValues.CLIENT_MESSENGER_INITIALIZED))
			{
				final Habbo friendHabbo = f.getHabbo();
				friendHabbo.loadMessenger();

				final List<Habbo> habbosToUpdate = new ArrayList<>();
				habbosToUpdate.add(this.habbo);

				friendHabbo.getConnection().send(new FriendsUpdateComposer(friendHabbo, habbosToUpdate, new ArrayList<>(), new ArrayList<>()));
			}
			else if (f.getHabbo().isOnline())
			{
				if (f.getHabbo() == null || f.getHabbo().getMessenger() == null || f.getHabbo().getMessenger().getFriendsToUpdate() == null)
					continue;

				final Friend friend = f.getHabbo().getMessenger().getFriend(this.habbo);

				if (f.getHabbo().getMessenger().getFriendsToUpdate().contains(friend))
					continue;

				f.getHabbo().getMessenger().getFriendsToUpdate().add(friend);
			}
		}
	}

}
