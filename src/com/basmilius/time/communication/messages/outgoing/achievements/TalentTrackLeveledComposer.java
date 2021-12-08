package com.basmilius.time.communication.messages.outgoing.achievements;

import com.basmilius.time.habbohotel.achievements.Talent;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.habbohotel.items.Item;
import com.basmilius.time.communication.messages.ServerMessage;
import com.basmilius.time.communication.messages.optcodes.Outgoing;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;

public class TalentTrackLeveledComposer extends MessageComposer
{

	private final Talent _talent;
	private final Habbo _habbo;

	public TalentTrackLeveledComposer(Habbo _habbo, Talent _talent)
	{
		this._habbo = _habbo;
		this._talent = _talent;
	}

	@Override
	public ServerMessage compose()
	{
		response.init(Outgoing.TalentTrackLeveled);
		response.appendString(this._talent.getType());
		response.appendInt(this._habbo.getSettings().getCitizenLevel());
		response.appendInt(this._talent.getRewardPerks().size());
		this._talent.getRewardPerks().forEach(response::appendString);
		response.appendInt(this._talent.getRewardItems().size());
		for (Item item : this._talent.getRewardItems())
		{
			response.appendString(item.getPublicName());
			response.appendInt(0); // HC days
		}
		return response;
	}

}
