package com.basmilius.time.communication.messages.outgoing.notifications;

import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

import java.util.ArrayList;
import java.util.List;

public abstract class LocalizedNotificationComposer extends MessageComposer
{

	private final String type;
	private final List<NotificationParameter> parameters;

	public LocalizedNotificationComposer(final String type, List<NotificationParameter> parameters)
	{
		this.type = type;
		this.parameters = parameters;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.LocalizedNotification);
		response.appendString(this.type);
		response.appendInt(this.parameters.size());
		for (final NotificationParameter parameter : this.parameters)
		{
			response.appendString(parameter.getKey());
			response.appendString(parameter.getValue());
		}
		return response;
	}

	public static final class TimeWelcomeLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public TimeWelcomeLocalizedNotificationComposer()
		{
			super("time.welcome", new ArrayList<>());
		}

	}

	public static final class BuildersClubMembershipExpiredLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public BuildersClubMembershipExpiredLocalizedNotificationComposer()
		{
			super("builders_club.membership_expired", new ArrayList<>());
		}

	}

	public static final class BuildersClubMembershipExpiresLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public BuildersClubMembershipExpiresLocalizedNotificationComposer()
		{
			super("builders_club.membership_expires", new ArrayList<>());
		}

	}

	public static final class BuildersClubMembershipExtendedLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public BuildersClubMembershipExtendedLocalizedNotificationComposer(final int furniLimit)
		{
			super("builders_club.membership_extended", getParameters(furniLimit));
		}

		private static List<NotificationParameter> getParameters(final int furniLimit)
		{
			List<NotificationParameter> parameters = new ArrayList<>();
			parameters.add(new NotificationParameter("FURNI_LIMIT", Integer.toString(furniLimit)));
			return parameters;
		}

	}

	public static final class BuildersClubMembershipMadeLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public BuildersClubMembershipMadeLocalizedNotificationComposer(final int furniLimit)
		{
			super("builders_club.membership_made", getParameters(furniLimit));
		}

		private static List<NotificationParameter> getParameters(final int furniLimit)
		{
			List<NotificationParameter> parameters = new ArrayList<>();
			parameters.add(new NotificationParameter("FURNI_LIMIT", Integer.toString(furniLimit)));
			return parameters;
		}

	}

	public static final class BuildersClubMembershipRenewedLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public BuildersClubMembershipRenewedLocalizedNotificationComposer(final int furniLimit)
		{
			super("builders_club.membership_renewed", getParameters(furniLimit));
		}

		private static List<NotificationParameter> getParameters(final int furniLimit)
		{
			List<NotificationParameter> parameters = new ArrayList<>();
			parameters.add(new NotificationParameter("FURNI_LIMIT", Integer.toString(furniLimit)));
			return parameters;
		}

	}

	public static final class BuildersClubRoomLockedLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public BuildersClubRoomLockedLocalizedNotificationComposer()
		{
			super("builders_club.room_locked", new ArrayList<>());
		}

	}

	public static final class BuildersClubVisitDeniedForOwnerLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public BuildersClubVisitDeniedForOwnerLocalizedNotificationComposer(final String username)
		{
			super("builders_club.visit_denied_for_owner", getParameters(username));
		}

		private static List<NotificationParameter> getParameters(final String username)
		{
			List<NotificationParameter> parameters = new ArrayList<>();
			parameters.add(new NotificationParameter("USERNAME", username));
			return parameters;
		}

	}

	public static final class BuildersClubVisitDeniedForVisitorLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public BuildersClubVisitDeniedForVisitorLocalizedNotificationComposer()
		{
			super("builders_club.visit_denied_for_visitor", new ArrayList<>());
		}

	}

	public static final class ForumsDeliveredLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public ForumsDeliveredLocalizedNotificationComposer(final int groupId, final String groupName)
		{
			super("forums.delivered", getParameters(groupId, groupName));
		}

		private static List<NotificationParameter> getParameters(final int groupId, final String groupName)
		{
			List<NotificationParameter> parameters = new ArrayList<>();
			parameters.add(new NotificationParameter("GROUPID", Integer.toString(groupId)));
			parameters.add(new NotificationParameter("GROUPNAME", groupName));
			return parameters;
		}

	}

	public static final class ForumErrorAccessDeniedComposer extends LocalizedNotificationComposer
	{

		public ForumErrorAccessDeniedComposer()
		{
			super("forums.error.access_denied", new ArrayList<>());
		}

	}

	public static final class ForumSettingsUpdatedComposer extends LocalizedNotificationComposer
	{

		public ForumSettingsUpdatedComposer()
		{
			super("forums.forum_settings_updated", new ArrayList<>());
		}

	}

	public static final class ForumMessageHiddenComposer extends LocalizedNotificationComposer
	{

		public ForumMessageHiddenComposer()
		{
			super("forums.message.hidden", new ArrayList<>());
		}

	}

	public static final class ForumMessageRestoredComposer extends LocalizedNotificationComposer
	{

		public ForumMessageRestoredComposer()
		{
			super("forums.message.restored", new ArrayList<>());
		}

	}

	public static final class ForumThreadHiddenComposer extends LocalizedNotificationComposer
	{

		public ForumThreadHiddenComposer()
		{
			super("forums.thread.hidden", new ArrayList<>());
		}

	}

	public static final class ForumThreadLockedComposer extends LocalizedNotificationComposer
	{

		public ForumThreadLockedComposer()
		{
			super("forums.thread.locked", new ArrayList<>());
		}

	}

	public static final class ForumThreadPinnedComposer extends LocalizedNotificationComposer
	{

		public ForumThreadPinnedComposer()
		{
			super("forums.thread.pinned", new ArrayList<>());
		}

	}

	public static final class ForumThreadRestoredComposer extends LocalizedNotificationComposer
	{

		public ForumThreadRestoredComposer()
		{
			super("forums.thread.restored", new ArrayList<>());
		}

	}

	public static final class ForumThreadUnLockedComposer extends LocalizedNotificationComposer
	{

		public ForumThreadUnLockedComposer()
		{
			super("forums.thread.unlocked", new ArrayList<>());
		}

	}

	public static final class ForumThreadUnPinnedComposer extends LocalizedNotificationComposer
	{

		public ForumThreadUnPinnedComposer()
		{
			super("forums.thread.unpinned", new ArrayList<>());
		}

	}

	public static final class PurchasingRoomLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public PurchasingRoomLocalizedNotificationComposer(final int roomId, final String roomName)
		{
			super("purchasing.room", getParameters(roomId, roomName));
		}

		private static List<NotificationParameter> getParameters(final int roomId, final String roomName)
		{
			List<NotificationParameter> parameters = new ArrayList<>();
			parameters.add(new NotificationParameter("ROOMID", Integer.toString(roomId)));
			parameters.add(new NotificationParameter("ROOMNAME", roomName));
			return parameters;
		}

	}

	public static final class RoomKickCannonballLocalizedNotificationComposer extends LocalizedNotificationComposer
	{

		public RoomKickCannonballLocalizedNotificationComposer()
		{
			super("room.kick.cannonball", new ArrayList<>());
		}

	}

	public static final class NotificationParameter
	{

		private final String key;
		private final String value;

		public NotificationParameter(final String key, final String value)
		{
			this.key = key;
			this.value = value;
		}

		public String getKey()
		{
			return this.key;
		}

		public String getValue()
		{
			return this.value;
		}
	}

}
