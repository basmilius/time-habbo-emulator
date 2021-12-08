package com.basmilius.time.communication.messages.outgoing.moderation;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.moderation.ModerationPresetIssue;
import com.basmilius.time.habbohotel.moderation.ModerationPresetRoom;
import com.basmilius.time.habbohotel.moderation.ModerationPresetUser;
import com.basmilius.time.habbohotel.moderation.collections.ModerationIssueList;
import com.basmilius.time.habbohotel.moderation.collections.ModerationPresetIssueList;
import com.basmilius.time.habbohotel.moderation.collections.ModerationPresetRoomList;
import com.basmilius.time.habbohotel.moderation.collections.ModerationPresetUserList;
import com.basmilius.time.habbohotel.moderation.issue.ModerationIssue;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class ModerationToolComposer extends MessageComposer
{

	private ModerationIssueList issues;
	private ModerationPresetIssueList presetsIssue;
	private ModerationPresetRoomList presetsRoom;
	private ModerationPresetUserList presetsUser;
	private boolean canReadTickets;
	private boolean canReadChatlogs;
	private boolean canSendMessagesAndCautions;
	private boolean canKick;
	private boolean canBan;
	private boolean canUseBroadcastTools;
	private boolean canOther;

	public ModerationToolComposer(boolean canReadTickets, boolean canReadChatlogs, boolean canSendMessagesAndCautions, boolean canKick, boolean canBan, boolean canUseBroadcastTools, boolean canOther)
	{
		this.issues = Bootstrap.getEngine().getGame().getModerationManager().getIssues();
		this.presetsIssue = Bootstrap.getEngine().getGame().getModerationManager().getPresetsIssue();
		this.presetsRoom = Bootstrap.getEngine().getGame().getModerationManager().getPresetsRoom();
		this.presetsUser = Bootstrap.getEngine().getGame().getModerationManager().getPresetsUser();
		this.canReadTickets = canReadTickets;
		this.canReadChatlogs = canReadChatlogs;
		this.canSendMessagesAndCautions = canSendMessagesAndCautions;
		this.canKick = canKick;
		this.canBan = canBan;
		this.canUseBroadcastTools = canUseBroadcastTools;
		this.canOther = canOther;
	}

	@Override
	public ServerMessage compose() throws Exception
	{
		response.init(Outgoing.ModerationTool);
		response.appendInt(this.issues.size());
		for (final ModerationIssue issue : this.issues)
		{
			issue.serializeIssue(response, 0);
		}
		response.appendInt(this.presetsUser.size());
		for (final ModerationPresetUser presetUser : this.presetsUser)
		{
			response.appendString(presetUser.getPreset());
		}
		response.appendInt(this.presetsIssue.size());
		for (final ModerationPresetIssue preset : this.presetsIssue)
		{
			preset.serialize(response);
		}
		response.appendBoolean(this.canReadTickets);
		response.appendBoolean(this.canReadChatlogs);
		response.appendBoolean(this.canSendMessagesAndCautions);
		response.appendBoolean(this.canKick);
		response.appendBoolean(this.canBan);
		response.appendBoolean(this.canUseBroadcastTools);
		response.appendBoolean(this.canOther);
		response.appendInt(this.presetsRoom.size());
		for (final ModerationPresetRoom presetRoom : this.presetsRoom)
		{
			response.appendString(presetRoom.getPreset());
		}
		return response;
	}

}
