package com.basmilius.time.habbohotel.habbohelpers;

import com.basmilius.time.Bootstrap;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.communication.messages.outgoing.habbohelpers.HabboHelpersGuideSessionErrorComposer;
import com.basmilius.time.communication.messages.outgoing.habbohelpers.HabboHelpersGuideSessionMessageComposer;
import com.basmilius.time.communication.messages.outgoing.habbohelpers.HabboHelpersGuideSessionStartedComposer;

import java.util.ArrayList;
import java.util.List;

public class HabboHelpersTicket
{

	private Habbo habbo;
	private HabboHelper helper;

	private int type;
	private String issue;

	private int rejectedCount;

	private List<HabboHelper> denyHelpers;

	public HabboHelpersTicket(Habbo habbo, int type, String issue)
	{
		this.habbo = habbo;
		this.helper = null;

		this.type = type;
		this.issue = issue;

		this.rejectedCount = 0;

		this.denyHelpers = new ArrayList<>();
	}

	public void addDenyHelper(HabboHelper helper)
	{
		this.denyHelpers.add(helper);
	}

	public boolean hasDenyHelper(HabboHelper helper)
	{
		return this.denyHelpers.contains(helper);
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public HabboHelper getHelper()
	{
		return this.helper;
	}

	public String getIssue()
	{
		return this.issue;
	}

	public int getType()
	{
		return this.type;
	}

	public void pick(HabboHelper helper)
	{
		this.helper = helper;
		this.send(new HabboHelpersGuideSessionStartedComposer(this.habbo, this.helper.getHabbo()));
	}

	public void reject()
	{
		this.rejectedCount++;

		if (this.rejectedCount == 3)
		{
			Bootstrap.getEngine().getGame().getHabboHelpersManager().cancelHelp(this.habbo);
			this.habbo.getConnection().send(new HabboHelpersGuideSessionErrorComposer(4));
		}
	}

	public void cancel()
	{

	}

	@Override
	public boolean equals(Object obj)
	{
		return (obj.getClass().isInstance(this) && ((HabboHelpersTicket) obj).getHabbo().getId() == this.getHabbo().getId());
	}

	public final void sendMessage(final Habbo habbo, final String message)
	{
		this.send(new HabboHelpersGuideSessionMessageComposer(habbo.getId(), message));
	}

	public final void send(final MessageComposer composer)
	{
		this.habbo.getConnection().send(composer);
		this.helper.getHabbo().getConnection().send(composer);
	}

	public final void send(final MessageComposer composer, final Habbo habbo)
	{
		if (this.habbo.getId() != habbo.getId())
		{
			this.habbo.getConnection().send(composer);
		}

		if (this.helper.getHabbo().getId() != habbo.getId())
		{
			this.helper.getHabbo().getConnection().send(composer);
		}
	}

}
