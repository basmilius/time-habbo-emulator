package com.basmilius.time.habbohotel.habbohelpers;

import com.basmilius.time.habbohotel.habbo.Habbo;

public class HabboHelper
{

	private Habbo habbo;

	private boolean onDuty;
	private boolean handleChatReviews;
	private boolean handleHelpRequests;
	private boolean handleTourRequests;

	private HabboHelpersTicket ticket;
	private HabboHelpersTicket pendingTicket;

	public HabboHelper(Habbo habbo)
	{
		this.habbo = habbo;

		this.onDuty = false;
		this.handleChatReviews = false;
		this.handleHelpRequests = false;
		this.handleTourRequests = false;

		this.ticket = null;
	}

	public Habbo getHabbo()
	{
		return this.habbo;
	}

	public HabboHelpersTicket getPendingTicket()
	{
		return this.pendingTicket;
	}

	public void setPendingTicket(HabboHelpersTicket ticket)
	{
		this.pendingTicket = ticket;
	}

	public boolean isOnDuty()
	{
		return this.onDuty;
	}

	public boolean isHandleChatReviews()
	{
		return this.handleChatReviews;
	}

	public boolean isHandleHelpRequests()
	{
		return this.handleHelpRequests;
	}

	public boolean isHandleTourRequests()
	{
		return this.handleTourRequests;
	}

	public boolean isHandlingTicket()
	{
		return (this.getTicket() != null);
	}

	public HabboHelpersTicket getTicket()
	{
		return this.ticket;
	}

	public void setHelperStatus(boolean onDuty, boolean handleChatReviews, boolean handleHelpRequests, boolean handleTourRequests)
	{
		this.onDuty = onDuty;
		this.handleChatReviews = handleChatReviews;
		this.handleHelpRequests = handleHelpRequests;
		this.handleTourRequests = handleTourRequests;
	}

}
