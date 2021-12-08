package com.basmilius.time.habbohotel.habbohelpers;

import com.basmilius.time.habbohotel.abstracts.IManager;
import com.basmilius.time.habbohotel.habbo.Habbo;
import com.basmilius.time.communication.messages.outgoing.MessageComposer;
import com.basmilius.time.communication.messages.outgoing.habbohelpers.HabboHelpersGuideSessionAttachedComposer;
import com.basmilius.time.communication.messages.outgoing.habbohelpers.HabboHelpersToolCloseComposer;
import com.basmilius.time.util.ObjectUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class HabboHelpersManager extends IManager
{

	private List<HabboHelper> helpers;
	private List<HabboHelpersTicket> tickets;

	public HabboHelpersManager()
	{
		this.helpers = new ArrayList<>();
	}

	@Override
	public void initialize() throws Exception
	{
		this.tickets = new LinkedList<>();
	}

	public void cancelHelp(Habbo habbo)
	{
		HabboHelpersTicket ticket = this.getTicketForHabbo(habbo);

		if (ticket == null)
			return;

		ticket.cancel();
		this.removeTickedPickWindow(ticket);

		this.tickets.remove(ticket);
	}

	public void createHelpRequest(Habbo habbo, int type, String issue)
	{
		this.tickets.add(new HabboHelpersTicket(habbo, type, issue));

		this.peek();
	}

	public void peek()
	{
		this.helpers.stream().filter(helper -> !helper.isHandlingTicket()).forEach(this::peek);
	}

	public void peek(HabboHelper helper)
	{
		this.peek(helper, 1);
	}

	public void peek(HabboHelper helper, int attempt)
	{
		HabboHelpersTicket ticket = ObjectUtils.getRandomObject(this.tickets);

		if (ticket == null || attempt == 3)
			return;

		if (ticket.hasDenyHelper(helper))
		{
			this.peek(helper);
			return;
		}

		helper.setPendingTicket(ticket);

		helper.getHabbo().getConnection().send(new HabboHelpersGuideSessionAttachedComposer(helper.isOnDuty(), ticket.getType(), ticket.getIssue(), 60));
	}

	public void pick(HabboHelper pickedHelper, boolean pick)
	{
		HabboHelpersTicket ticket = pickedHelper.getPendingTicket();

		if (pick)
		{
			pickedHelper.setPendingTicket(null);

			ticket.pick(pickedHelper);
			final Stream<HabboHelper> helpers = this.helpers.stream().filter(helper -> helper.getPendingTicket() != null && helper.getPendingTicket().getHabbo().getId() == ticket.getHabbo().getId());
			if (helpers != null)
			{
				helpers.forEach(helper -> helper.getHabbo().getConnection().send(new HabboHelpersToolCloseComposer()));
			}
		}
		else
		{
			ticket.addDenyHelper(pickedHelper);
			ticket.reject();

			this.peek(pickedHelper);
		}
	}

	public HabboHelper createOrGetHelper(Habbo habbo)
	{
		HabboHelper helper = this.getHelper(habbo);

		if (helper == null)
		{
			this.helpers.add(helper = new HabboHelper(habbo));
		}

		this.peek(helper);

		return helper;
	}

	public HabboHelper getHelper(Habbo habbo)
	{
		for (HabboHelper helper : this.helpers)
			if (helper.getHabbo().getId() == habbo.getId())
				return helper;

		return null;
	}

	public HabboHelpersTicket getTicketForHabbo(Habbo habbo)
	{
		for (HabboHelpersTicket ticket : this.tickets)
			if (ticket.getHabbo().getId() == habbo.getId())
				return ticket;

		return null;
	}

	public final HabboHelpersTicket getTicketForSession(final Habbo habbo)
	{
		for (final HabboHelpersTicket ticket : this.tickets)
		{
			if (ticket.getHabbo().getId() == habbo.getId() || (ticket.getHelper() != null && ticket.getHelper().getHabbo().getId() == habbo.getId()))
			{
				return ticket;
			}
		}

		return null;
	}

	public void removeTickedPickWindow(HabboHelpersTicket ticket)
	{
		this.helpers.stream().filter(helper -> helper.getPendingTicket() != null && helper.getPendingTicket().equals(ticket)).forEach(helper -> helper.getHabbo().getConnection().send(new HabboHelpersToolCloseComposer()));
	}

	public int getCountGuides()
	{
		int counter = 0;

		for (HabboHelper helper : this.helpers)
			if (helper.isOnDuty() && helper.isHandleTourRequests())
				counter++;

		return counter;
	}

	public int getCountHelpers()
	{
		int counter = 0;

		for (HabboHelper helper : this.helpers)
			if (helper.isOnDuty() && helper.isHandleHelpRequests())
				counter++;

		return counter;
	}

	public int getCountGuardians()
	{
		int counter = 0;

		for (HabboHelper helper : this.helpers)
			if (helper.isOnDuty() && helper.isHandleChatReviews())
				counter++;

		return counter;
	}

	public void send(MessageComposer composer)
	{
		for (HabboHelper helper : this.helpers)
		{
			if (!helper.getHabbo().isOnline() || !helper.isOnDuty())
				continue;

			helper.getHabbo().getConnection().send(composer);
		}
	}

	@Override
	public void dispose()
	{

	}

}
