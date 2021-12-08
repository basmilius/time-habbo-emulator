package com.basmilius.time.communication.messages.optcodes;

public class Outgoing
{

	// Ambassador
	public static final short AmbassadorMuted = 63;

	// Handshake
	public static final short PrimeAndGenerator = 3630;
	public static final short ServerPublicKey = 1716;
	public static final short MachineId = 3882;
	public static final short Start = 142;
	public static final short EnableTrading = 2733;
	public static final short EnableNotifications = 2206;

	// BuildersClub
	public static final short BuildersClubFurniCount = -1;
	public static final short BuildersClubStatus = 31;

	// Catalogue
	public static final short CatalogueOffer = 3259;
	public static final short CataloguePages = 1063;
	public static final short CataloguePage = 1370;
	public static final short CatalogueDiscounts = 1643;
	public static final short CatalogueCommission = 1862;
	public static final short CatalogueRares = -1;
	public static final short CatalogueGiftWrapping = 1008;
	public static final short CataloguePetNameCheckResult = 949;
	public static final short CataloguePetRaces = 917;
	public static final short CatalogueUpdated = 527;
	public static final short PurchaseOK = 3002;
	public static final short PurchaseFailed = 2935;
	public static final short PurchaseHabboNotFound = 2377;
	public static final short PurchaseNotAllowed = 579;
	public static final short ClubBenefits = 295;
	public static final short VoucherAcceptedFurni = 3204;
	public static final short VoucherError = 835;
	public static final short MarketCanOffer = -1;
	public static final short MarketPlaceResult = -1;
	public static final short LimitedEditionItemSoldOut = 3180;
	public static final short TargetedOfferData = 471;

	// Inventory
	public static final short AchievementScore = 1288;
	public static final short FloorAndWallItems = 694;
	public static final short FurniListAddOrUpdate = 1466;
	public static final short InventoryBadges = 1350;
	public static final short InventoryBots = 1847;
	public static final short InventoryPets = -1;
	public static final short AddItem = 3577;
	public static final short AddEffectItem = 3052;
	public static final short RemoveItem = 3595;
	public static final short RemoveEffectItem = 1335;
	public static final short RefreshInventory = 1657;
	public static final short EffectList = 2648;
	public static final short BadgeReceived = -1;

	// General
	public static final short ServerError = 932;
	public static final short GenericAlert = 1765;
	public static final short GenericModAlert = 2615;
	public static final short GenericAlertWithUrl = 1572;
	public static final short GenericModAlertWithUrl = 413;
	public static final short MessagesOfTheDay = 3935;
	public static final short ClubTimeToChooseBenefit = 1381;
	public static final short WelcomePresent = -1;
	public static final short HotelClosed = 1708;
	public static final short HotelClosing = 1745;
	public static final short HotelClosingNoDuration = 2550;
	public static final short SendHotelView = 3347;
	public static final short HotelViewBonusRare = 1752;
	public static final short HotelViewData = 2706;
	public static final short HotelViewDataOK = 2010;
	public static final short HotelViewDataParsed = 2546;
	public static final short HandshakeComplete = -1;
	public static final short ClubInfo = 1310;
	public static final short MinimailCount = 384;
	public static final short NewMinimail = 1018;
	public static final short RoomForward = 291;
	public static final short QuizData = -1;
	public static final short Citizenship = 3526;
	public static final short YoutubeDisplayPlaylists = 817;
	public static final short YoutubeDisplayPlayVideo = 1902;
	public static final short CloseClient = 4000;
	public static final short EpicPopupFrame = 3578;
	public static final short ShowHint = 2870;
	public static final short LinkEvent = 3627;
	public static final short LocalizedNotification = 2004;
	public static final short ToolbarRoomPanelState = -1;
	public static final short LatencyPong = 1363;

	// Landing
	public static final short HabboLandingAbstractTimerElement = 703;
	public static final short HabboLandingExpiringCatalogPage = 1150;
	public static final short HabboLandingNextLimitedRare = 227;
	public static final short HabboLandingTeamChosen = 2840;

	// Users
	public static final short UserInfo = 2021;
	public static final short PerkAllowances = 1529;
	public static final short Currencies = 2470;
	public static final short UpdateCurrency = 13;
	public static final short Credits = 3907;
	public static final short HabboClub = 838;
	public static final short UserProfile = 2425;
	public static final short UserProfileFriends = 3439;
	public static final short UserProfileBadges = 3761;
	public static final short Fuseright = 1179;
	public static final short UpdateLook = 1084;
	public static final short UpdateLookInRoom = 3214;
	public static final short HabboSettings = 1197;
	public static final short HomeRoom = 53;
	public static final short Wardrobe = 3646;
	public static final short AvatarEditorApplyEffect = 1153;
	public static final short AvatarEditorFigureSetIds = 2973;
	public static final short NotEnoughCurrency = 2318;

	// Friends
	public static final short FriendListInit = 904;
	public static final short FriendListUsers = 2273;
	public static final short FriendRequests = 1486;
	public static final short FriendRequest = 3273;
	public static final short ReceiveMessage = 317;
	public static final short FriendUpdate = 192;
	public static final short FriendError = 2463;
	public static final short FriendFollowError = 2892;
	public static final short FriendMessageError = 1206;
	public static final short InviteMessage = 991;
	public static final short UserSearchResults = 3695;
	public static final short FindNewFriendsResult = 1053;

	// Moderation
	public static final short ModerationActionResult = 1348;
	public static final short ModerationHelpCanRequest = 1582;
	public static final short ModerationHelpClosed = -1;
	public static final short ModerationHelpPendingIssues = 3215;
	public static final short ModerationHelpRequestSend = -1;
	public static final short ModerationIssueChatlogs = 747;
	public static final short ModerationIssueFailed = 2429;
	public static final short ModerationIssueWindowSettings = 2736;
	public static final short ModerationNewTicket = 3917;
	public static final short ModerationRemoveTicket = 3888;
	public static final short ModerationReply = -1;
	public static final short ModerationRoomChatlogs = 784;
	public static final short ModerationRoomInfo = 1307;
	public static final short ModerationRoomVisits = 3509;
	public static final short ModerationTicketClosedAlert = -1;
	public static final short ModerationTool = 283;
	public static final short ModerationUserChatlogs = 3915;
	public static final short ModerationUserInfo = 2559;
	public static final short ModerationUserList = 1990;

	// Name change
	public static final short NameChangeCheckResult = 594;
	public static final short NameChangeClaimResult = 3406;

	// HabboHelpers
	public static final short HabboHelpersTool = -1;
	public static final short HabboHelpersToolClose = -1;
	public static final short HabboHelpersGuideSessionAttached = -1;
	public static final short HabboHelpersGuideSessionDetached = -1;
	public static final short HabboHelpersGuideSessionStarted = -1;
	public static final short HabboHelpersGuideSessionEnded = -1;
	public static final short HabboHelpersGuideSessionError = -1;
	public static final short HabboHelpersGuideSessionMessage = -1;
	public static final short HabboHelpersGuideSessionRequesterRoom = -1;
	public static final short HabboHelpersGuideSessionInvitedToGuideRoom = -1;
	public static final short HabboHelpersGuideSessionPartnerIsTyping = -1;
	public static final short HabboHelpersGuardianFinalVoteResults = -1; // (I I I [I:I])
	public static final short HabboHelpersGuardianNewRequest = -1;
	public static final short HabboHelpersGuardianVoteResults = -1; // ([I:I])

	// Pets
	public static final short PetCantGoToNest = -1;

	// Navigator
	public static final short NavigatorOfficialList = 453;
	public static final short NavigatorFlatList = 918;
	public static final short NavigatorTags = 2271;
	public static final short RoomCategories = 180;
	public static final short CanCreateRoom = 625;
	public static final short RoomNameUnacceptable = 2147;
	public static final short CreateRoom = 647;
	public static final short MyRooms = 2231;
	public static final short NavigatorFavorites = 2941;
	public static final short NavigatorFavoriteChange = 2495;
	public static final short DoorbellKnock = 2133;
	public static final short DoorbellRemove = -1;
	public static final short DoorbellRemoveUser = -1;
	public static final short NavigatorCategories = 3678;
	public static final short NavigatorLiftedRooms = -1;
	public static final short NavigatorMetaData = 158;
	public static final short NavigatorSavedSearches = 1236;
	public static final short NavigatorSearchResultSet = 873;
	public static final short NavigatorSettings = -1;

	// Rooms
	public static final short StartRoom = 567;
	public static final short DisconnectRoom = 819;
	public static final short SendModel = 321;
	public static final short SendPaper = 2022;
	public static final short RoomRights = 469;
	public static final short RoomOwner = 2217;
	public static final short RoomScore = 668;
	public static final short FloorItems = 1416;
	public static final short WallItems = 3642;
	public static final short RoomThickness = 689;
	public static final short RoomSettings = 455;
	public static final short RoomSettingsEnforceNewCategory = 1091;
	public static final short RoomSettingsRights = 347;
	public static final short RoomSettingsRightsAdded = 1319;
	public static final short RoomSettingsRightsRemoved = 2824;
	public static final short RoomSettingsSaved = -1;
	public static final short Heightmap = 812;
	public static final short UpdateHeightmap = 2944;
	public static final short RelativeHeightmap = 1104;
	public static final short RoomUsers = 714;
	public static final short RoomStatuses = 3140;
	public static final short RoomPanel = 22;
	public static final short RoomData = 3685;
	public static final short RemoveRoomUser = 2203;
	public static final short Idle = 3063;
	public static final short Talk = 2310;
	public static final short Shout = 3078;
	public static final short Whisper = 1429;
	public static final short ChatFlooding = 3684;
	public static final short RoomUserAction = 2219;
	public static final short ApplyDance = 2844;
	public static final short ApplyEffect = 156;
	public static final short CarryItem = 3192;
	public static final short GotCarryItem = 1873;
	public static final short TypingChange = 1345;
	public static final short UserRespected = 3457;
	public static final short Ignores = -1;
	public static final short NewFloorItem = 1124;
	public static final short NewWallItem = 2077;
	public static final short RemoveFloorItem = 2121;
	public static final short RemoveWallItem = 2789;
	public static final short UpdateFloorItem = 193;
	public static final short UpdateFloorItemData = 2846;
	public static final short UpdateFloorItemsData = 2755;
	public static final short UpdateWallItem = 3686;
	public static final short RollerMove = 2595;
	public static final short WiredCondition = 2200;
	public static final short WiredEffect = 952;
	public static final short WiredTrigger = 1958;
	public static final short WiredSaved = 570;
	public static final short WiredReward = 3491;
	public static final short WiredUpdateFailed = 705;
	public static final short MoodlightPresets = 834;
	public static final short BotSettings = -1;
	public static final short RoomErrorFull = -1;
	public static final short RoomErrorKicked = -1;
	public static final short RoomErrorItem = -1;
	public static final short RoomErrorPet = 2114;
	public static final short RoomErrorBot = 1396;
	public static final short UpdateUnitName = 850;
	public static final short RoomUpdate = 1267;
	public static final short RoomChatSettings = 741;
	public static final short RoomFilterWords = -1;
	public static final short FloorPlanEditorDoor = 1651;
	public static final short FloorPlanEditorLockedTiles = 3651;
	public static final short StickyNoteData = 1627;
	public static final short RoomEvent = -1;
	public static final short RoomEventRemove = -1;
	public static final short RoomUpdateError = -1;
	public static final short SpectatorMode = 2278;

	// Rooms: Items
	public static final short GiftOpened = 354;
	public static final short LoveLockConfirm = 2338;
	public static final short LoveLockFriendConfirmed = 1026;
	public static final short LoveLockFriendDenied = 1220;

	// Rooms: Competitions
	public static final short CompetitionEntrySubmitResult = 1355;
	
	// Rooms: Pets
	public static final short PetsData = 3375;
	public static final short PetsExperienceReceived = 1566;
	public static final short PetsScratched = 1188;

	// Rooms: Trading
	public static final short TradingConfirmation = 3430;
	public static final short TradingOpenFailed = 3048;
	public static final short TradingCompleted = 602;
	public static final short TradingItems = 1769;
	public static final short TradingClose = 1735; // (I I) I1: 0 = no message, 1 = message :: I2: 1 = can't find some items.
	public static final short TradingPrevented = 3978; // you turned trading off.
	public static final short TradingPreventedOtherUser = 648; // other user turned trading off.
	public static final short TradingStart = 839;
	public static final short TradingAccept = 1972;

	// Quests
	public static final short GotQuest = -1; // out 2711 VIPTutorials 2 5 101 1 false S:EMPTY S:EMPTY 10 wear_white_shirt 23 25 5 set_kuurna MAIN_CHAIN false

	// Soundmachine
	public static final short SongId = 911;
	public static final short SongInfo = 1555;
	public static final short SoundMachineSongs = 91;
	public static final short SoundMachinePlayList = 387;
	public static final short SoundMachineNowPlaying = 2629;
	public static final short SoundMachinePlayListFull = 3363;

	// Achievements
	public static final short Achievements = 876;
	public static final short AchievementsLevels = 185;
	public static final short UpdateAchievementStatus = 1155;
	public static final short AchievementUnlocked = 37;
	public static final short TalentTrack = 425;
	public static final short TalentTrackLeveled = 2907;

	// Guilds
	public static final short GuildBadges = 3659;
	public static final short CreateGuildPanelRooms = 1353;
	public static final short CreateGuildPanelSymbols = 2256;
	public static final short GuildPurchaseOK = 1219;
	public static final short GuildInfo = 3302;
	public static final short ManageGuildInfo = 3963;
	public static final short MyGuilds = 2504;
	public static final short GuildItemInfo = -1;
	public static final short GuildMembers = 1644;
	public static final short UpdateGuildMember = 2157;
	public static final short AddGuildMember = 966;
	public static final short ConfirmRemoveGuildMember = 1907;
	public static final short RemoveGuildMember = 3847;
	public static final short ReloadGuild = 3450;
	public static final short GuildFail = 3121;
	public static final short GuildEditFail = 727;
	public static final short GuildModOperationFail = 3364;

	// Guilds Forum
	public static final short GuildForumGuildData = 1890;
	public static final short GuildForumMessageUpdate = 3961;
	public static final short GuildForumNewMessage = 2581;
	public static final short GuildForumNewThread = 235;
	public static final short GuildForumRootList = 3141;
	public static final short GuildForumThreadMessages = 3868;
	public static final short GuildForumThreads = 438;
	public static final short GuildForumThreadUpdate = 2328;
	public static final short GuildForumUnreadMessages = 885;

	// Room Games
	public static final short FreezeLifes = 2798;
	public static final short FreezeStatus = 2208;

	// Polls
	public static final short PollContents = 1321;
	public static final short PollError = 1479;
	public static final short PollSummary = 3468;

	// Verification
	public static final short PhoneNumberVerificationInit = -1;

	// Camera
	public static final short CameraPhotoPrice = 1278;

	// Nux
	public static final short NuxFreePresents = 816;
	public static final short NuxWantSomePresents = 280;

}
