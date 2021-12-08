package com.basmilius.time.communication.messages;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.communication.messages.incoming.MessageEvent;
import com.basmilius.time.communication.messages.incoming.achievements.CitizenshipEvent;
import com.basmilius.time.communication.messages.incoming.achievements.RequestAchievementsEvent;
import com.basmilius.time.communication.messages.incoming.achievements.RequestAchievementsLevelsEvent;
import com.basmilius.time.communication.messages.incoming.achievements.TalentTrackEvent;
import com.basmilius.time.communication.messages.incoming.preferences.*;
import com.basmilius.time.communication.messages.incoming.room.avatar.*;
import com.basmilius.time.communication.messages.incoming.room.chat.ShoutMessageEvent;
import com.basmilius.time.communication.messages.incoming.room.chat.ChatMessageEvent;
import com.basmilius.time.communication.messages.incoming.room.chat.WhisperMessageEvent;
import com.basmilius.time.communication.messages.incoming.room.engine.*;
import com.basmilius.time.communication.messages.incoming.room.furniture.SetMannequinFigureMessageEvent;
import com.basmilius.time.communication.messages.incoming.room.furniture.SetMannequinNameMessageEvent;
import com.basmilius.time.communication.messages.incoming.room.session.OpenFlatConnectionMessageEvent;
import com.basmilius.time.communication.messages.incoming.room.session.GoToFlatMessageEvent;
import com.basmilius.time.communication.messages.incoming.roomsettings.GetRoomSettingsMessageEvent;
import com.basmilius.time.communication.messages.incoming.roomsettings.DeleteRoomMessageEvent;
import com.basmilius.time.communication.messages.incoming.roomsettings.GetFlatControllersMessageEvent;
import com.basmilius.time.communication.messages.incoming.roomsettings.SaveRoomSettingsMessageEvent;
import com.basmilius.time.communication.messages.incoming.tracking.LatencyPingRequestMessageEvent;
import com.basmilius.time.communication.messages.incoming.availability.LatencyTrackerEvent;
import com.basmilius.time.communication.messages.incoming.buildersclub.RequestBuildersClubEvent;
import com.basmilius.time.communication.messages.incoming.camera.CameraRequestPriceEvent;
import com.basmilius.time.communication.messages.incoming.camera.RenderRoomThumbnailMessageEvent;
import com.basmilius.time.communication.messages.incoming.camera.RenderRoomMessageEvent;
import com.basmilius.time.communication.messages.incoming.catalog.*;
import com.basmilius.time.communication.messages.incoming.friends.*;
import com.basmilius.time.communication.messages.incoming.general.*;
import com.basmilius.time.communication.messages.incoming.guilds.*;
import com.basmilius.time.communication.messages.incoming.guilds.forum.*;
import com.basmilius.time.communication.messages.incoming.habbohelpers.*;
import com.basmilius.time.communication.messages.incoming.handshake.*;
import com.basmilius.time.communication.messages.incoming.inventory.RequestInventoryBadgesEvent;
import com.basmilius.time.communication.messages.incoming.inventory.RequestInventoryBotsEvent;
import com.basmilius.time.communication.messages.incoming.inventory.RequestInventoryEvent;
import com.basmilius.time.communication.messages.incoming.items.*;
import com.basmilius.time.communication.messages.incoming.landing.*;
import com.basmilius.time.communication.messages.incoming.moderation.*;
import com.basmilius.time.communication.messages.incoming.namechange.NameChangeCheckNameEvent;
import com.basmilius.time.communication.messages.incoming.namechange.NameChangeClaimNameEvent;
import com.basmilius.time.communication.messages.incoming.navigator.*;
import com.basmilius.time.communication.messages.incoming.notifications.GetMessageOffTheDayEvent;
import com.basmilius.time.communication.messages.incoming.poll.PollAcceptEvent;
import com.basmilius.time.communication.messages.incoming.poll.PollAnswerEvent;
import com.basmilius.time.communication.messages.incoming.poll.PollCancelEvent;
import com.basmilius.time.communication.messages.incoming.rooms.*;
import com.basmilius.time.communication.messages.incoming.rooms.competition.CompetitionEntrySubmitEvent;
import com.basmilius.time.communication.messages.incoming.rooms.competition.RequestRoomCompetitionEvent;
import com.basmilius.time.communication.messages.incoming.rooms.pets.PetsRequestDataEvent;
import com.basmilius.time.communication.messages.incoming.rooms.pets.PetsScratchEvent;
import com.basmilius.time.communication.messages.incoming.rooms.trading.*;
import com.basmilius.time.communication.messages.incoming.rooms.users.*;
import com.basmilius.time.communication.messages.incoming.soundmachine.*;
import com.basmilius.time.communication.messages.incoming.tracking.EventLogMessageEvent;
import com.basmilius.time.communication.messages.incoming.users.*;
import com.basmilius.time.communication.messages.incoming.verification.PhoneNumberVerificationInitResultEvent;
import com.basmilius.time.communication.messages.incoming.wired.SaveWiredConditionEvent;
import com.basmilius.time.communication.messages.incoming.wired.SaveWiredEffectEvent;
import com.basmilius.time.communication.messages.incoming.wired.SaveWiredTriggerEvent;

import java.util.HashMap;

public class HabboMessages
{

	private final HashMap<Short, Class<? extends MessageEvent>> events;

	public HabboMessages ()
	{
		this.events = new HashMap<>();

		/** ACHIEVEMENTS **/
		this.addConnectionEvent(CitizenshipEvent.class);
		this.addConnectionEvent(RequestAchievementsEvent.class);
		this.addConnectionEvent(RequestAchievementsLevelsEvent.class);
		this.addConnectionEvent(TalentTrackEvent.class);

		/** AVAILABILITY **/
		this.addConnectionEvent(LatencyPingRequestMessageEvent.class);
		this.addConnectionEvent(LatencyTrackerEvent.class);

		/** BUILDERS CLUB **/
		this.addConnectionEvent(RequestBuildersClubEvent.class);

		/** CAMERA **/
		this.addConnectionEvent(CameraRequestPriceEvent.class);
		this.addConnectionEvent(RenderRoomThumbnailMessageEvent.class);
		this.addConnectionEvent(RenderRoomMessageEvent.class);

		/** CATALOG **/
		this.addConnectionEvent(CatalogInitializedEvent.class);
		this.addConnectionEvent(MarketplaceCanOfferEvent.class);
		this.addConnectionEvent(PurchaseItemEvent.class);
		this.addConnectionEvent(PurchaseItemPresentEvent.class);
		this.addConnectionEvent(PurchaseRoomEventEvent.class);
		this.addConnectionEvent(PurchaseTargetedOfferEvent.class);
		this.addConnectionEvent(RequestCatalogueCommissionEvent.class);
		this.addConnectionEvent(RequestCatalogueDiscountsEvent.class);
		this.addConnectionEvent(RequestCatalogueEvent.class);
		this.addConnectionEvent(RequestCatalogueGiftWrappingEvent.class);
		this.addConnectionEvent(RequestCatalogueOfferEvent.class);
		this.addConnectionEvent(RequestCataloguePageEvent.class);
		this.addConnectionEvent(RequestCataloguePetNameCheckEvent.class);
		this.addConnectionEvent(RequestCataloguePetRacesEvent.class);
		this.addConnectionEvent(RequestCatalogueRaresEvent.class);
		this.addConnectionEvent(RequestClubBenefitsEvent.class);
		this.addConnectionEvent(VoucherRedeemEvent.class);

		/** FRIENDS **/
		this.addConnectionEvent(AcceptFriendshipEvent.class);
		this.addConnectionEvent(DeleteFriendshipEvent.class);
		this.addConnectionEvent(FindNewFriendsEvent.class);
		this.addConnectionEvent(FollowFriendEvent.class);
		this.addConnectionEvent(FriendChangeRelationEvent.class);
		this.addConnectionEvent(FriendRequestEvent.class);
		this.addConnectionEvent(InviteToRoomEvent.class);
		this.addConnectionEvent(RejectFriendshipEvent.class);
		this.addConnectionEvent(RequestFriendRequestsEvent.class);
		this.addConnectionEvent(RequestFriendsEvent.class);
		this.addConnectionEvent(RequestFriendsUpdateEvent.class);
		this.addConnectionEvent(SearchUsersEvent.class);
		this.addConnectionEvent(SendMessageEvent.class);

		/** GENERAL **/
		this.addConnectionEvent(EventLogMessageEvent.class);
		this.addConnectionEvent(ClientUserInfoEvent.class);
		this.addConnectionEvent(HabboClubInformationEvent.class);
		this.addConnectionEvent(HandshakeCompleteEvent.class);
		this.addConnectionEvent(SetHomeRoomEvent.class);
		this.addConnectionEvent(SetRoomCameraPreferencesMessageEvent.class);
		this.addConnectionEvent(SetIgnoreRoomInvitesMessageEvent.class);
		this.addConnectionEvent(SetChatPreferencesMessageEvent.class);
		this.addConnectionEvent(StartQuizEvent.class);
		this.addConnectionEvent(SwfDataEvent.class);
		this.addConnectionEvent(SetUIFlagsMessageEvent.class);

		/** GUILDS **/
		this.addConnectionEvent(AcceptGuildMemberEvent.class);
		this.addConnectionEvent(CanRemoveGuildMemberEvent.class);
		this.addConnectionEvent(GiveGuildRightsEvent.class);
		this.addConnectionEvent(LoadCreateGuildPanelRoomsEvent.class);
		this.addConnectionEvent(LoadCreateGuildPanelSymbolsEvent.class);
		this.addConnectionEvent(RemoveFavoriteGuildEvent.class);
		this.addConnectionEvent(RemoveGuildMemberEvent.class);
		this.addConnectionEvent(RemoveGuildRightsEvent.class);
		this.addConnectionEvent(RequestCreateGuildEvent.class);
		this.addConnectionEvent(RequestGuildInfoEvent.class);
		this.addConnectionEvent(RequestGuildItemInfoEvent.class);
		this.addConnectionEvent(RequestGuildMembersEvent.class);
		this.addConnectionEvent(RequestGuildsEvent.class);
		this.addConnectionEvent(RequestManageGuildEvent.class);
		this.addConnectionEvent(RequestMembershipEvent.class);
		this.addConnectionEvent(SaveGuildBadgeEvent.class);
		this.addConnectionEvent(SaveGuildColorsEvent.class);
		this.addConnectionEvent(SaveGuildDetailsEvent.class);
		this.addConnectionEvent(SaveGuildSettingsEvent.class);
		this.addConnectionEvent(SetFavoriteGuildEvent.class);

		/** GUILDS FORUM **/
		this.addConnectionEvent(GuildForumCreatePostEvent.class);
		this.addConnectionEvent(GuildForumDeleteMessageToggleEvent.class);
		this.addConnectionEvent(GuildForumDeleteThreadToggleEvent.class);
		this.addConnectionEvent(GuildForumGetForumsEvent.class);
		this.addConnectionEvent(GuildForumGetGuildDataEvent.class);
		this.addConnectionEvent(GuildForumGetThreadsEvent.class);
		this.addConnectionEvent(GuildForumOpenThreadEvent.class);
		this.addConnectionEvent(GuildForumSaveSettingsEvent.class);
		this.addConnectionEvent(GuildForumUpdateThreadEvent.class);

		/** HABBO HELPERS **/
		this.addConnectionEvent(HabboHelpersAcceptRequestEvent.class);
		this.addConnectionEvent(HabboHelpersCancelHelpEvent.class);
		this.addConnectionEvent(HabboHelpersRequestHelpEvent.class);
		this.addConnectionEvent(HabboHelpersSendMessageEvent.class);
		this.addConnectionEvent(HabboHelpersTypingEvent.class);
		this.addConnectionEvent(HabboHelpersToolEvent.class);

		/** HANDSHAKE **/
		this.addConnectionEvent(GenerateSecretKeyEvent.class);
		this.addConnectionEvent(InitCryptoEvent.class);
		this.addConnectionEvent(UniqueIDMessageEvent.class);
		this.addConnectionEvent(VersionCheckMessageEvent.class);
		this.addConnectionEvent(RequestUserDataEvent.class);
		this.addConnectionEvent(SSOTicketMessageEvent.class);

		/** INVENTORY **/
		this.addConnectionEvent(RequestInventoryBadgesEvent.class);
		this.addConnectionEvent(RequestInventoryBotsEvent.class);
		this.addConnectionEvent(RequestInventoryEvent.class);

		/** ITEMS **/
		this.addConnectionEvent(LoveLockConfirmResultEvent.class);
		this.addConnectionEvent(SetMannequinFigureMessageEvent.class);
		this.addConnectionEvent(SetMannequinNameMessageEvent.class);
		this.addConnectionEvent(RedeemCreditsEvent.class);
		this.addConnectionEvent(RemoveStickyNoteEvent.class);
		this.addConnectionEvent(RequestMoodLightPresetsEvent.class);
		this.addConnectionEvent(RequestOpenGiftEvent.class);
		this.addConnectionEvent(RequestStickyNoteEvent.class);
		this.addConnectionEvent(RequestYouTubePlaylistEvent.class);
		this.addConnectionEvent(RoomBgChangeEvent.class);
		this.addConnectionEvent(SaveBrandingEvent.class);
		this.addConnectionEvent(SaveMoodLightPresetEvent.class);
		this.addConnectionEvent(SaveStickyNoteEvent.class);
		this.addConnectionEvent(SetYouTubeVideoEvent.class);
		this.addConnectionEvent(SetYouTubeVideoNextEvent.class);
		this.addConnectionEvent(StackingHelperEvent.class);
		this.addConnectionEvent(ToggleMoodLightStateEvent.class);

		/** LANDING **/
		this.addConnectionEvent(HabboLandingAbstractTimerElementEvent.class);
		this.addConnectionEvent(HabboLandingCheckTeamEvent.class);
		this.addConnectionEvent(HabboLandingChooseTeamEvent.class);
		this.addConnectionEvent(HabboLandingExpiringCatalogPageEvent.class);
		this.addConnectionEvent(HabboLandingNextLimitedRareEvent.class);
		this.addConnectionEvent(HotelViewBonusRareEvent.class);
		this.addConnectionEvent(HotelViewDataCheckerEvent.class);
		this.addConnectionEvent(HotelViewDataEvent.class);
		this.addConnectionEvent(HotelViewDataParseEvent.class);
		this.addConnectionEvent(HotelViewEvent.class);

		/** MODERATION **/
		this.addConnectionEvent(ModerationHelpCancelIssueEvent.class);
		this.addConnectionEvent(ModerationHelpCanRequestEvent.class);
		this.addConnectionEvent(ModerationIssueChatlogsEvent.class);
		this.addConnectionEvent(ModerationIssueHandlingWindowSaveEvent.class);
		this.addConnectionEvent(ModerationPickTicketEvent.class);
		this.addConnectionEvent(ModerationReportGuildForumMessageEvent.class);
		this.addConnectionEvent(ModerationReportGuildForumThreadEvent.class);
		this.addConnectionEvent(ModerationReportUserEvent.class);
		this.addConnectionEvent(ModerationRoomChatlogsEvent.class);
		this.addConnectionEvent(ModerationRoomInfoEvent.class);
		this.addConnectionEvent(ModerationRoomVisitsEvent.class);
		this.addConnectionEvent(ModerationSendMessageEvent.class);
		this.addConnectionEvent(ModerationSendRoomMessageEvent.class);
		this.addConnectionEvent(ModerationUserChatlogsEvent.class);
		this.addConnectionEvent(ModerationUserInfoEvent.class);

		/** NAME CHANGE **/
		this.addConnectionEvent(NameChangeCheckNameEvent.class);
		this.addConnectionEvent(NameChangeClaimNameEvent.class);

		/** NAVIGATOR **/
		this.addConnectionEvent(CanCreateRoomEvent.class);
		this.addConnectionEvent(CreateRoomEvent.class);
		this.addConnectionEvent(DoorbellAnswerEvent.class);
		this.addConnectionEvent(GetMyRoomsEvent.class);
		this.addConnectionEvent(NavigatorEvent.class);
		this.addConnectionEvent(NavigatorAddFavoriteEvent.class);
		this.addConnectionEvent(NavigatorFavoriteRoomsEvent.class);
		this.addConnectionEvent(NavigatorGoActionEvent.class);
		this.addConnectionEvent(NavigatorMyGuildsRoomsEvent.class);
		this.addConnectionEvent(NavigatorMyRoomsEvent.class);
		this.addConnectionEvent(NavigatorNewSearchEvent.class);
		this.addConnectionEvent(NavigatorOfficialRoomsEvent.class);
		this.addConnectionEvent(NavigatorPopularGuildsEvent.class);
		this.addConnectionEvent(NavigatorPopularRoomsEvent.class);
		this.addConnectionEvent(NavigatorRemoveFavoriteEvent.class);
		this.addConnectionEvent(NavigatorRequestFavoritesEvent.class);
		this.addConnectionEvent(NavigatorRequestTagsEvent.class);
		this.addConnectionEvent(NavigatorRoomsOfMyFriendsEvent.class);
		this.addConnectionEvent(NavigatorRoomsWhereMyFriendsAreEvent.class);
		this.addConnectionEvent(NavigatorRoomsWithHighestScoresEvent.class);
		this.addConnectionEvent(NavigatorSearchEvent.class);
		this.addConnectionEvent(GoToFlatMessageEvent.class);
		this.addConnectionEvent(RequestRoomCategoriesEvent.class);

		/** NOTIFICATIONS **/
		this.addConnectionEvent(GetMessageOffTheDayEvent.class);

		/** POLL **/
		this.addConnectionEvent(PollAcceptEvent.class);
		this.addConnectionEvent(PollAnswerEvent.class);
		this.addConnectionEvent(PollCancelEvent.class);

		/** ROOMS **/
		this.addConnectionEvent(BotChatSettingsEvent.class);
		this.addConnectionEvent(ChangeRoomEventEvent.class);
		this.addConnectionEvent(UseFurnitureMessageEvent.class);
		this.addConnectionEvent(UseWallItemMessageEvent.class);
		this.addConnectionEvent(FloorPlanEditorRequestDoorEvent.class);
		this.addConnectionEvent(FloorPlanEditorRequestLockedTilesEvent.class);
		this.addConnectionEvent(OpenFlatConnectionMessageEvent.class);
		this.addConnectionEvent(LoadHeightmapEvent.class);
		this.addConnectionEvent(MoveObjectMessageEvent.class);
		this.addConnectionEvent(MoveWallItemMessageEvent.class);
		this.addConnectionEvent(RemoveBotFromFlatMessageEvent.class);
		this.addConnectionEvent(PickUpObjectMessageEvent.class);
		this.addConnectionEvent(PlaceBotMessageEvent.class);
		this.addConnectionEvent(PlaceObjectMessageEvent.class);
		this.addConnectionEvent(PlacePaperEvent.class);
		this.addConnectionEvent(RoomLoadedEvent.class);
		this.addConnectionEvent(GetRoomSettingsMessageEvent.class);
		this.addConnectionEvent(DeleteRoomMessageEvent.class);
		this.addConnectionEvent(RoomSettingsRightsAddEvent.class);
		this.addConnectionEvent(GetFlatControllersMessageEvent.class);
		this.addConnectionEvent(RoomSettingsRightsRemoveEvent.class);
		this.addConnectionEvent(RoomSettingsRightsRemoveAllEvent.class);
		this.addConnectionEvent(SaveBotSettingEvent.class);
		this.addConnectionEvent(SaveRoomModelEvent.class);
		this.addConnectionEvent(SaveRoomSettingsMessageEvent.class);
		this.addConnectionEvent(VoteRoomEvent.class);

		/** ROOMS COMPETITION **/
		this.addConnectionEvent(CompetitionEntrySubmitEvent.class);
		this.addConnectionEvent(RequestRoomCompetitionEvent.class);
		
		/** ROOMS PETS **/
		this.addConnectionEvent(PetsRequestDataEvent.class);
		this.addConnectionEvent(PetsScratchEvent.class);

		/** ROOMS TRADING **/
		this.addConnectionEvent(TradingAcceptEvent.class);
		this.addConnectionEvent(TradingCancelEvent.class);
		this.addConnectionEvent(TradingCancelConfirmEvent.class);
		this.addConnectionEvent(TradingConfirmEvent.class);
		this.addConnectionEvent(TradingOfferItemEvent.class);
		this.addConnectionEvent(TradingRequestEvent.class);
		this.addConnectionEvent(TradingTakeBackItemEvent.class);

		/** ROOMS USERS **/
		this.addConnectionEvent(ActionSitEvent.class);
		this.addConnectionEvent(DanceMessageEvent.class);
		this.addConnectionEvent(SignMessageEvent.class);
		this.addConnectionEvent(PassCarryItemMessageEvent.class);
		this.addConnectionEvent(GiveRespectEvent.class);
		this.addConnectionEvent(IgnoreHabboEvent.class);
		this.addConnectionEvent(KickHabboEvent.class);
		this.addConnectionEvent(ListenToHabboEvent.class);
		this.addConnectionEvent(LookAtHabboEvent.class);
		this.addConnectionEvent(LookToMessageEvent.class);
		this.addConnectionEvent(PutCarryItemDownEvent.class);
		this.addConnectionEvent(AvatarExpressionMessageEvent.class);
		this.addConnectionEvent(ShoutMessageEvent.class);
		this.addConnectionEvent(StartTypingEvent.class);
		this.addConnectionEvent(StopTypingEvent.class);
		this.addConnectionEvent(ChatMessageEvent.class);
		this.addConnectionEvent(MoveAvatarMessageEvent.class);
		this.addConnectionEvent(WhisperMessageEvent.class);

		/** SOUND MACHINE **/
		this.addConnectionEvent(AddSongToPlaylistEvent.class);
		this.addConnectionEvent(RemoveSongFromPlayListEvent.class);
		this.addConnectionEvent(RequestNowPlayingEvent.class);
		this.addConnectionEvent(RequestSongIdEvent.class);
		this.addConnectionEvent(RequestSongInfoEvent.class);
		this.addConnectionEvent(RequestSoundMachinePlayListEvent.class);
		this.addConnectionEvent(RequestSoundMachineSongsEvent.class);

		/** USERS **/
		this.addConnectionEvent(ActivateEffectEvent.class);
		this.addConnectionEvent(ApplyEffectEvent.class);
		this.addConnectionEvent(AvatarEditorUseClothesEvent.class);
		this.addConnectionEvent(RequestCreditsEvent.class);
		this.addConnectionEvent(RequestHabboClubEvent.class);
		this.addConnectionEvent(RequestHabboSettingsEvent.class);
		this.addConnectionEvent(SetSoundSettingsMessageEvent.class);
		this.addConnectionEvent(SaveWardrobeEvent.class);
		this.addConnectionEvent(SetBadgesEvent.class);
		this.addConnectionEvent(SetChatStylePreferenceMessageEvent.class);
		this.addConnectionEvent(UpdateLookEvent.class);
		this.addConnectionEvent(ChangeMottoMessageEvent.class);
		this.addConnectionEvent(UserProfileBadgesEvent.class);
		this.addConnectionEvent(UserProfileEvent.class);
		this.addConnectionEvent(UserProfileFriendsEvent.class);
		this.addConnectionEvent(WardrobeEvent.class);

		/** VERIFICATION **/
		this.addConnectionEvent(PhoneNumberVerificationInitResultEvent.class);

		/** WIRED **/
		this.addConnectionEvent(SaveWiredConditionEvent.class);
		this.addConnectionEvent(SaveWiredEffectEvent.class);
		this.addConnectionEvent(SaveWiredTriggerEvent.class);
	}

	public void addConnectionEvent(Class<? extends MessageEvent> handler)
	{
		Event event = handler.getAnnotation(Event.class);
		if (event != null && event.id() > 0)
		{
			this.events.put((short) event.id(), handler);
		}
		else if (event != null)
		{
			Bootstrap.getEngine().getLogging().logErrorLine("MessageEvent '" + handler.getCanonicalName() + "' has an header id that is less than 0.");
		}
		else
		{
			Bootstrap.getEngine().getLogging().logErrorLine("MessageEvent '" + handler.getCanonicalName() + "' does not have the new Event Annotation.");
		}

		if (event != null && event.id() > 0 && event.secondId() > 0)
		{
			this.events.put((short) event.secondId(), handler);
		}
	}

	public Class<? extends MessageEvent> getMessageHandler(final short messageId)
	{
		if (this.events.containsKey(messageId))
			return this.events.get(messageId);
		return null;
	}

	public boolean isRegistred(short messageId)
	{
		return this.events.containsKey(messageId);
	}

}