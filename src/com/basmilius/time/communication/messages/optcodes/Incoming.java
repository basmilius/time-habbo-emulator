package com.basmilius.time.communication.messages.optcodes;

public class Incoming
{

	// Handshake
	public static final short VersionCheck = 4000;
	public static final short InitCrypto = 760;
	public static final short GenerateSecretKey = 31;
	public static final short SSOTicket = 187;
	public static final short UniqueID = 1735;
	public static final short RequestUserData = 3849;

	// Notifications
	public static final short GetMessageOfTheDay = 3496;

	// General
	public static final short EventLog = 1186;
	public static final short ClientUserInfo = 1896;
	public static final short SwfData = 2092;
	public static final short HotelView = 2797;
	public static final short HotelViewBonusRare = 1883;
	public static final short HotelViewData = 694;
	public static final short HotelViewDataChecker = 3723;
	public static final short HotelViewDataParse = 2632;
	public static final short HabboClubInformation = 2100;
	public static final short HandshakeComplete = 275;
	public static final short StartQuiz = 2937;
	public static final short CheckQuizAnswers = -1;
	public static final short Citizenship = 1629;
	public static final short RequestYouTubePlaylist = 181;
	public static final short SetYouTubeVideo = 3832;
	public static final short SetYouTubeVideoNext = 2279;
	public static final short SetHomeRoom = 1583;
	public static final short SetRoomCameraPreferences = 1728;
	public static final short SetIgnoreRoomInvites = 1148;
	public static final short SetChatPreferences = 3621;
	public static final short LatencyPingRequest = 1042;
	public static final short LatencyTracker = 47;
	public static final short SetUIFlags = 2137;

	// Landing
	public static final short HabboLandingAbstractTimerElement = 1697;
	public static final short HabboLandingCheckTeam = 1121;
	public static final short HabboLandingChooseTeam = 3010;
	public static final short HabboLandingExpiringCatalogPage = 2191;
	public static final short HabboLandingNextLimitedRare = 820;

	// User
	public static final short RequestUserProfile = 2223;
	public static final short RequestUserProfileFriends = 2910;
	public static final short RequestUserProfileBadges = 457;
	public static final short UpdateLook = 105;
	public static final short ChangeMotto = 1297;
	public static final short RequestCredits = 3044;
	public static final short RequestHabboClub = 2850;
	public static final short RequestHabboSettings = 3530;
	public static final short AvatarEditorUseClothes = 3339;
	public static final short Wardrobe = 1395;
	public static final short SaveWardrobe = 2922;
	public static final short SetSoundSettings = 3663;
	public static final short ActivateEffect = -1;
	public static final short ApplyEffect = -1;
	public static final short SetBadges = 3333;
	public static final short SetChatStylePreference = 3612;

	// Moderation
	public static final short ModerationReportGuideSession = -1; // (S)
	public static final short ModerationReportGuildForumMessage = -1;
	public static final short ModerationReportGuildForumThread = -1;
	public static final short ModerationReportUser = -1;
	public static final short ModerationHelpCancelIssue = -1;
	public static final short ModerationHelpCanRequest = 341;
	public static final short ModerationIssueChatlogs = -1;
	public static final short ModerationIssueHandlingWindowSave = -1;
	public static final short ModerationPickTicket = -1;
	public static final short ModerationRoomChatlogs = 679;
	public static final short ModerationRoomInfo = 3605;
	public static final short ModerationRoomVisits = 582;
	public static final short ModerationSendMessage = 2339;
	public static final short ModerationSendRoomMessage = 647;
	public static final short ModerationUserChatlogs = 1747;
	public static final short ModerationUserInfo = 871;

	// Change name
	public static final short NameChangeCheckName = 1367;
	public static final short NameChangeClaimName = 3251;

	// HabboHelpers
	public static final short HabboHelpersTool = 593;
	public static final short HabboHelpersAcceptRequest = -1;
	public static final short HabboHelpersCancelHelp = -1;
	public static final short HabboHelpersRequestHelp = 3154;
	public static final short HabboHelpersSendMessage = -1;
	public static final short HabboHelpersTicketResponse = -1;
	public static final short HabboHelpersTyping = -1;
	public static final short HabboHelpersGuardianAcceptRequest = -1;
	public static final short HabboHelpersGuardianVote = -1;

	// BuildersClub
	public static final short RequestBuildersClub = -1;

	// Catalogue
	public static final short CatalogInitialized = 3127;
	public static final short RequestCatalogue = 263;
	public static final short RequestCatalogueCommission = 3438;
	public static final short RequestCatalogueDiscounts = 1228;
	public static final short RequestCatalogueGiftWrapping = 489;
	public static final short RequestCatalogueOffer = 3561;
	public static final short RequestCataloguePage = 3049;
	public static final short RequestCataloguePetNameCheck = 3381;
	public static final short RequestCataloguePetRaces = 666;
	public static final short RequestCatalogueRares = 362;
	public static final short PurchaseItem = 2164;
	public static final short PurchaseItemPresent = 1071;
	public static final short PurchaseRoomEvent = -1;
	public static final short PurchaseTargetedOffer = 1471;
	public static final short RequestClubBenefits = 533;
	public static final short MarketplaceCanOffer = -1;
	public static final short MarketplaceRequestPrice = -1;
	public static final short VoucherRedeem = 2900;

	// Inventory
	public static final short RequestInventory = 46;
	public static final short RequestInventoryBadges = 2218;
	public static final short RequestInventoryBots = 3691;
	public static final short RequestInventoryPets = 2757;

	// Friends
	public static final short RequestFriends = 1926;
	public static final short RequestFriendRequests = 1011;
	public static final short RequestFriendsUpdate = 2158;
	public static final short SendMessage = 2297;
	public static final short FriendRequest = 1052;
	public static final short FriendRelationChange = 1803;
	public static final short AcceptFriendship = 3749;
	public static final short RejectFriendship = 2044;
	public static final short FollowFriend = 1436;
	public static final short InviteToRoom = 1740;
	public static final short SearchUsers = 3454;
	public static final short DeleteFriendship = 2215;
	public static final short FindNewFriends = 193;
	public static final short VisitSomeone = 350;

	// Navigator
	public static final short Navigator = 543;
	public static final short NavigatorOfficialRooms = 2584;
	public static final short NavigatorMyRooms = 2383;
	public static final short NavigatorRoomsOfMyFriends = 107;
	public static final short NavigatorRoomsWhereMyFriendsAre = 966;
	public static final short NavigatorPopularRooms = 2477;
	public static final short NavigatorPopularGuilds = 1892;
	public static final short NavigatorRoomsWithHighestScores = 3214;
	public static final short NavigatorRequestTags = 3194;
	public static final short NavigatorSearch = 123;
	public static final short NavigatorFavoriteRooms = 80;
	public static final short NavigatorLastVisitedRooms = 584;
	public static final short NavigatorMyGuildsRooms = 2060;
	public static final short NavigatorRoomsWithRights = 3291;
	public static final short NavigatorRequestFavorites = 3103;
	public static final short CanCreateRoom = 3767;
	public static final short CreateRoom = 3736;
	public static final short GoToFlat = 3222;
	public static final short RequestRoomCategories = 3105;
	public static final short GetMyRooms = -1;
	public static final short NavigatorAddFavorite = 2237;
	public static final short NavigatorRemoveFavorite = 1599;
	public static final short NavigatorGoAction = -1;
	public static final short DoorbellAnswer = 625;
	public static final short NavigatorNewSearch = 2233;

	// Rooms
	public static final short SaveRoomModel = 1637;
	public static final short OpenFlatConnection = 1889;
	public static final short LoadHeightmap = 2867;
	public static final short RoomLoaded = 2058;
	public static final short RoomLoadedSecond = 3895;
	public static final short GetRoomSettings = 3866;
	public static final short DeleteRoom = 1978;
	public static final short GetFlatControllers = 2877;
	public static final short RoomSettingsRightsAdd = 216;
	public static final short RoomSettingsRightsRemove = 2316;
	public static final short RoomSettingsRightsRemoveAll = 3575;
	public static final short SaveRoomSettings = 1413;
	public static final short MoveAvatar = 2842;
	public static final short Whisper = 3168;
	public static final short Chat = 1082;
	public static final short Shout = 1420;
	public static final short StartTyping = 3032;
	public static final short StopTyping = 1627;
	public static final short AvatarExpression = 1096;
	public static final short ActionSit = 717;
	public static final short Sign = 1448;
	public static final short Dance = 2098;
	public static final short PutCarryItemDown = 2707;
	public static final short PassCarryItem = 2921;
	public static final short LookAtHabbo = 425;
	public static final short LookTo = 1757;
	public static final short UseFurniture = 3043;
	public static final short UseWallItem = 3391;
	public static final short GiveRespect = 387;
	public static final short IgnoreHabbo = 2156;
	public static final short ListenToHabbo = -1;
	public static final short KickHabbo = 3696;
	public static final short PlaceBot = 3480;
	public static final short PlaceObject = 969;
	public static final short RemoveBotFromFlat = 142;
	public static final short PickUpObject = 3789;
	public static final short PlacePaper = 2450;
	public static final short MoveObject = 3430;
	public static final short MoveWallItem = 3357;
	public static final short SetMannequinName = 1386;
	public static final short SetMannequinFigure = 2621;
	public static final short StackingHelper = 1305;
	public static final short SaveBranding = 1004;
	public static final short SaveWiredCondition = 3173;
	public static final short SaveWiredEffect = 3052;
	public static final short SaveWiredTrigger = 2573;
	public static final short VoteRoom = 1584;
	public static final short RoomBgChange = 2753;
	public static final short RequestMoodLightPresets = 1601;
	public static final short SaveMoodLightPreset = -1;
	public static final short ToggleMoodLightState = -1;
	public static final short RedeemCredits = 3294;
	public static final short SaveBotSetting = 3836;
	public static final short BotChatSettings = 2501;
	public static final short RoomFilterRequestWords = 2925;
	public static final short RoomFilterManageWord = 1404;
	public static final short FloorPlanEditorRequestDoor = 333;
	public static final short FloorPlanEditorRequestLockedTiles = 2794;
	public static final short RequestStickyNote = 733;
	public static final short SaveStickyNote = -1;
	public static final short RemoveStickyNote = -1;
	public static final short ChangeRoomEvent = -1;

	// Items.
	public static final short LoveLockConfirmResult = 3212;
	public static final short RequestOpenGift = 2402;

	// Rooms: Competitions
	public static final short CompetitionEntrySubmit = -1;
	public static final short RequestRoomCompetition = 2666;
	
	// Rooms: Pets
	public static final short PetsRequestData = 1346;
	public static final short PetsScratch = 3549;

	// Rooms: Trading
	public static final short TradingCancel = 2577;
	public static final short TradingCancelConfirm = 3275;
	public static final short TradingConfirm = 2714;
	public static final short TradingOfferItem = 2902;
	public static final short TradingRequest = 2455;
	public static final short TradingTakeBackItem = 2210;
	public static final short TradingUserAccept = 2124;

	// Quests
	public static final short QuestsList = 1039;

	// Soundmachine
	public static final short AddSongToPlaylist = 3590;
	public static final short RemoveSongFromPlaylist = 441;
	public static final short RequestNowPlaying = 2478;
	public static final short RequestSongId = 634;
	public static final short RequestSongInfo = 1462;
	public static final short RequestSoundMachineSongs = 2554;
	public static final short RequestSoundMachinePlayList = 141;

	// Achievements
	public static final short RequestAchievements = 3340;
	public static final short RequestAchievementsLevels = 3242;
	public static final short TalentTrack = 1663;

	// Guilds
	public static final short LoadCreateGuildPanelRooms = 1792;
	public static final short LoadCreateGuildPanelSymbols = 3;
	public static final short RequestCreateGuild = 3055;
	public static final short RequestGuildInfo = 1542;
	public static final short RequestManageGuild = 1810;
	public static final short RequestGuilds = 475;
	public static final short RequestGuildItemInfo = 2835;
	public static final short RequestGuildMembers = 315;
	public static final short RequestMembership = 272;
	public static final short SaveGuildDetails = 2863;
	public static final short SaveGuildBadge = 463;
	public static final short SaveGuildColors = 334;
	public static final short SaveGuildSettings = 3965;
	public static final short GiveGuildRights = 498;
	public static final short RemoveGuildRights = 2814;
	public static final short AcceptGuildMember = 3361;
	public static final short CanRemoveGuildMember = 742;
	public static final short RemoveGuildMember = 2848;
	public static final short SetFavoriteGuild = 3581;
	public static final short RemoveFavoriteGuild = 3342;

	// Guilds Forum
	// guild forum request update: 1966
	public static final short GuildForumCreatePost = 1564;
	public static final short GuildForumDeleteMessageToggle = 520;
	public static final short GuildForumDeleteThreadToggle = 264;
	public static final short GuildForumGetGuildData = 885;
	public static final short GuildForumGetForums = 1613;
	public static final short GuildForumGetThreads = 3278;
	public static final short GuildForumOpenThread = 2232;
	public static final short GuildForumSaveSettings = 805;
	public static final short GuildForumUpdateThread = 166;

	// Polls
	public static final short PollAccept = 828;
	public static final short PollAnswer = -1;
	public static final short PollCancel = 778;

	// Verification
	public static final short PhoneNumberVerificationInitResult = -1;

	// Camera
	public static final short CameraRequestPrice = 3475;
	public static final short RenderRoomThumbnail = 1105;
	public static final short RenderRoom = 2756;

}
